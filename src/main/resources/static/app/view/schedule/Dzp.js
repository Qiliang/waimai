Ext.define('Kits.view.schedule.Dzp', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dzp',
    //title: '调度--待指派',
    store: Ext.create('Kits.store.User'),
    layout: {
        type: 'hbox',
        align: 'stretch'
    },
    requires: ['Kits.view.BMap'],

    items: [
        {
            xtype: 'grid',
            itemId: 'shopOrderGrid',
            selType: 'checkboxmodel',
            store: Ext.create('Kits.store.Order', {
                proxy: {
                    type: 'ajax',
                    url: '/api/orders?status=4'
                }
            }),
            tools: [
                {
                    type: 'refresh',
                    tooltip: '刷新',
                    callback: function (panel, tool, event) {
                        panel.getStore().load();
                    }
                }
            ],
            // tbar: [
            //     {
            //         xtype: 'combo',
            //         emptyText: '输入商家名称筛选',
            //         store: Ext.create('Kits.store.Shop'),
            //         displayField: 'name',
            //         valueField: 'id',
            //         flex: 1
            //     }
            // ],
            columns: [
                {

                    text: '订单', dataIndex: 'description', flex: 1,
                    cellWrap: true,
                    variableRowHeight: true,
                    renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                        var recipientAddress = record.get('recipientAddress').split('@#')[0];
                        return '<div style="padding: 5px;">#'+record.get('daySeq') +'</div>'+
                        '<div style="padding: 5px;"><span style="color:#4c82b1" class="fa fa-arrow-circle-left"></span>' + record.get('shopName') + '</div>'
                            + '<div style="padding: 5px;"><span style="color:saddlebrown" class="fa fa-arrow-circle-right"></span><span>' + recipientAddress + '</span></div>'
                    }
                }
            ],
            width: 240,
            listeners: {
                rowclick: function (grid, record, element, rowIndex, e, eOpts) {
                    var me = this.up('dzp');
                    me.queryById('riderGird').setSelection(null);
                },
                selectionchange: function (grid, selected, eOpts) {
                    var bMap = this.up('panel').down('bmap');
                    bMap.markerOrderClear();
                    bMap.markerOrder(selected);
                },
                afterrender:function (grid,eOpts) {
                    grid.getSelectionModel().setSelectionMode('MULTI');
                }
            }
        },
        {
            xtype: 'bmap',
            asignToRider: function (riderId, displayName) {
                var me = this.up('dzp');
                me.asignToRider(riderId, displayName);
            },
            reasignToRider: function (riderId, displayName, orderId, callback) {
                var me = this.up('dzp');
                me.reasignToRider(riderId, displayName, orderId, function () {
                    me.queryById('riderGird').getStore().reload();
                    me.queryById('shopOrderGrid').getStore().reload();
                    callback();
                });
            },
            flex: 1
        },
        {
            xtype: 'grid',
            itemId: 'riderGird',
            store: Ext.create('Kits.store.Rider', {sorters: {property: 'status', direction: 'DESC'}}),
            tools: [
                {
                    type: 'refresh',
                    tooltip: '刷新',
                    callback: function (panel, tool, event) {
                        panel.getStore().load();
                    }
                }
            ],
            columns: [
                {
                    text: '骑手', dataIndex: 'displayName', width: 180,
                    renderer: function (value, metaData, record) {
                        var v = value + '[' + record.get('dqcCount') + '][' + record.get('pszCount') + ']';
                        if (record.get('status') === 1) {
                            return '<span style="color: #cf4c35">' + v + '</span>'
                        } else {
                            return '<span style="color: darkgrey">' + v + '</span>'
                        }
                    }
                },

            ],
            listeners: {
                rowcontextmenu: function (grid, record, tr, rowIndex, e, eOpts) {
                    e.preventDefault();
                    if (record.get('status') !== 1)return;
                    var me = grid.up('dzp');
                    me.asignToRider(record.get('id'), record.get('displayName'));

                },
                rowdblclick: function (grid, record, element, rowIndex, e, eOpts) {
                    e.preventDefault();
                    var me = grid.up('dzp');
                    me.queryById('shopOrderGrid').setSelection(null);
                    Ext.Ajax.request({
                        method: 'POST',
                        url: '/api/rider/orders/',
                        params: {size: 100, token: record.get('id'), status: '11,12'},
                        success: function (response, opts) {
                            var obj = Ext.decode(response.responseText);
                            var recs = [];
                            Ext.create("Kits.store.Order", {data: obj.list}).getData().each(function (r) {
                                recs.push(r);
                            });
                            var bMap = grid.up('dzp').down('bmap');
                            bMap.markerOrderClear();
                            bMap.markerOrder(recs);
                        },
                        failure: function (response, opts) {
                            console.log('server-side failure with status code ' + response.status);
                        }
                    });
                }
            },
            width: 185
        }
    ],

    // tbar: [
    //     {
    //         xtype: 'combo',
    //         fieldLabel: '调度类型',
    //         forceSelection: true,
    //         value: '手动',
    //         store: ['手动']
    //     },
    //     '->',
    //     '-',
    //     {
    //         xtype: 'displayfield',
    //         fieldLabel: '负载',
    //         value: '2.16'
    //     }, '', '', ''
    // ],

    afterComponentLayout: function (w, h) {
        this.callParent(arguments);
    },


    asignToRider: function (riderId, displayName) {
        var me = this;
        var grid = me.down('grid[itemId=shopOrderGrid]');
        if (!grid.getSelection() || grid.getSelection().length == 0)return;
        var orderRecords = grid.getSelection().map(function (item) {
            return item.get('id')
        }).join(",");
        Ext.Msg.confirm('确认', '确认指派' + displayName + "?", function (r) {
            if (r !== 'yes') return;
            Ext.Ajax.request({
                method: 'POST',
                url: '/api/orders/' + orderRecords + '/asign/' + riderId,
                success: function (response, opts) {
                    grid.getStore().load({
                        scope: this,
                        callback: function (records, operation, success) {
                            grid.setSelection(records[0])
                            console.log(records);
                        }
                    });
                },
                failure: function (response, opts) {
                    console.log('server-side failure with status code ' + response.status);
                }
            });
        }, this);
    },

    reasignToRider: function (riderId, displayName, orderId, callback) {
        var me = this;
        Ext.Msg.confirm('确认', '确认指派' + displayName + "?", function (r) {
            if (r !== 'yes') return;
            Ext.Ajax.request({
                method: 'POST',
                url: '/api/orders/' + orderId + '/asign/' + riderId,
                callback: function (options, success, response) {
                    if (callback) callback();
                }
            });
        }, this);
    }


});