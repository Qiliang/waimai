Ext.define('Kits.view.schedule.Dzp', {
    extend: 'Ext.panel.Panel',
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
            store: Ext.create('Kits.store.Shop'),
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
                {text: '商家名称', dataIndex: 'name', flex: 1}
            ],
            width: 200,
            listeners: {
                selectionchange: function (grid, selected, eOpts) {
                    var rec = selected[0];


                }
            }
        },
        {
            xtype: 'bmap',
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
    },
});