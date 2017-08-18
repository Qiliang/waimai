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
            tbar: [
                {
                    xtype: 'combo',
                    emptyText: '输入商家名称筛选',
                    store: Ext.create('Kits.store.Shop'),
                    displayField: 'name',
                    valueField: 'id',
                    flex: 1
                }
            ],
            columns: [
                {
                    text: '订单', dataIndex: 'description', flex: 1,
                    // xtype: 'widgetcolumn', text: '订单地址', dataIndex: 'userAddress', flex: 1,
                    // widget: {
                    //     height: 24,
                    //     xtype: 'image',
                    //     bind: {src: '{record.userAddressImg}'}
                    // },
                    // renderer: function (value, metaData, record) {
                    //     var userAddressImg = record.get('userAddressImg');
                    //     return userAddressImg ? userAddressImg : record.get('userAddress');
                    // }
                }
            ],
            width: 250,
            listeners: {
                selectionchange: function (grid, selected, eOpts) {
                    var rec = selected[0];
                    var bMap = this.up('panel').down('bmap');
                    bMap.markerOrderClear();
                    bMap.markerOrder(rec);
                }
            }
        },
        {
            xtype: 'bmap',
            asignToRider: function (riderId, displayName) {
                var me = this.up('dzp');
                var grid = me.down('grid[itemId=shopOrderGrid]');
                if (!grid.getSelection() || grid.getSelection().length == 0)return;
                var orderRecord = grid.getSelection()[0];
                Ext.Msg.confirm('确认', '确认指派' + displayName + "?", function (r) {
                    if (r !== 'yes') return;
                    Ext.Ajax.request({
                        method: 'POST',
                        url: '/meituan/orders/' + orderRecord.get('id') + '/asign/' + riderId,
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
        {
            xtype: 'grid',
            store: Ext.create('Kits.store.Rider'),
            tbar: [
                {
                    xtype: 'combo',
                    emptyText: '输入骑手姓名筛选',
                    store: Ext.create('Kits.store.Rider'),
                    displayField: 'name',
                    valueField: 'id',
                    flex: 1
                }
            ],
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
                {text: '骑手姓名', dataIndex: 'name', flex: 1},
                {text: '骑手电话', dataIndex: 'phone', flex: 1}
            ],
            width: 250
        }
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