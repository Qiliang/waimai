Ext.define('Kits.view.Order', {
    extend: 'Ext.grid.Panel',
    title: '订单',
    store: Ext.create('Kits.store.Order'),

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
        {text: 'id', dataIndex: 'id'},
        {text: '描述', dataIndex: 'description'},
        {text: '下单时间', dataIndex: 'time', width: 150, xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '状态', dataIndex: 'state'},
        {text: '客户', dataIndex: 'userName'},
        {text: '地址', dataIndex: 'userAddress'},
        {text: '电话', dataIndex: 'userPhone'},
        {text: '备注', dataIndex: 'remark'},
        {text: '店铺', dataIndex: 'shopName'},
        {text: '系统创建时间', dataIndex: 'createdDate', width: 150, xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '系统最后修改时间', dataIndex: 'lastModifiedDate', width: 150, xtype: 'datecolumn', format: 'Y-m-d H:i:s'}
        // {text: '店铺经度', dataIndex: 'shopLng'},
        // {text: '店铺纬度', dataIndex: 'shopLat'},
        // {text: '用户经度', dataIndex: 'orderLng'},
        // {text: '用户纬度', dataIndex: 'orderLat'}
    ]
});