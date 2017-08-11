Ext.define('Kits.view.schedule.Wc', {
    extend: 'Ext.grid.Panel',
    title: '调度--完成订单',
    store: Ext.create('Kits.store.Order', {
        proxy: {
            type: 'rest',
            url: '/meituan/orders?state=wc'
        }
    }),
    layout: 'fit',


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
        {text: 'id', dataIndex: 'id', width: 170},
        {text: '骑手', dataIndex: 'riderName', width: 100},
        {text: '指派时间', dataIndex: 'riderAssignTime', width: 150, xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '描述', dataIndex: 'description'},
        {text: '时间', dataIndex: 'time', xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '状态', dataIndex: 'state'},
        {text: '客户', dataIndex: 'userName'},
        {text: '地址', dataIndex: 'userAddress'},
        {text: '电话', dataIndex: 'userPhone'},
        {text: '备注', dataIndex: 'remark'}
    ]
});