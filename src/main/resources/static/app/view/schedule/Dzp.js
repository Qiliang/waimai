Ext.define('Kits.view.schedule.Dzp', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dzp',
    title: '调度--待指派',
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
            store: Ext.create('Kits.store.Order', {
                proxy: {
                    type: 'ajax',
                    url: '/meituan/orders?state=dzp'
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
                    renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                        return '<div style="padding: 5px;"><span style="color:#4c82b1" class="fa fa-arrow-circle-left"></span>' + record.get('shopName') + '</div>'
                            + '<div style="padding: 5px;"><span style="color:saddlebrown" class="fa fa-arrow-circle-right"></span><span>' + record.get('userAddress') + '</span></div>'
                    }
                }
            ],
            width: 350,
            listeners: {
                selectionchange: function (grid, selected, eOpts) {
                    //var rec = selected[0];
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
                var grid = me.down('grid[itemId=shopOrderGrid]');
                if (!grid.getSelection() || grid.getSelection().length == 0)return;
                var orderRecords = grid.getSelection().map(function (item) {return item.get('id')}).join(",");
                Ext.Msg.confirm('确认', '确认指派' + displayName + "?", function (r) {
                    if (r !== 'yes') return;
                    Ext.Ajax.request({
                        method: 'POST',
                        url: '/meituan/orders/' +orderRecords + '/asign/' + riderId,
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
            flex: 1
        },
        // {
        //     xtype: 'grid',
        //     store: Ext.create('Kits.store.Rider'),
        //     tbar: [
        //         {
        //             xtype: 'combo',
        //             emptyText: '输入骑手姓名筛选',
        //             store: Ext.create('Kits.store.Rider'),
        //             displayField: 'name',
        //             valueField: 'id',
        //             flex: 1
        //         }
        //     ],
        //     tools: [
        //         {
        //             type: 'refresh',
        //             tooltip: '刷新',
        //             callback: function (panel, tool, event) {
        //                 panel.getStore().load();
        //             }
        //         }
        //     ],
        //     columns: [
        //         {text: '骑手姓名', dataIndex: 'name', flex: 1},
        //         {text: '骑手电话', dataIndex: 'phone', flex: 1}
        //     ],
        //     width: 250
        // }
    ],

    tbar: [
        {
            xtype: 'combo',
            fieldLabel: '调度类型',
            forceSelection: true,
            value: '手动',
            store: ['手动']
        },
        '->',
        '-',
        {
            xtype: 'displayfield',
            fieldLabel: '负载',
            value: '2.16'
        }, '', '', ''
    ],

    afterComponentLayout: function (w, h) {
        this.callParent(arguments);
    }


});