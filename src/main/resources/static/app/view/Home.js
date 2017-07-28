Ext.define('Kits.view.Home', {
    extend: 'Ext.grid.Panel',
    title: '首页',
    store: Ext.create('Kits.store.Order'),
    columns: [
        {text: 'id', dataIndex: 'id'},
        {text: '描述', dataIndex: 'description'},
        {text: '时间', dataIndex: 'time'},
        {text: '状态', dataIndex: 'state'},
        {text: '客户', dataIndex: 'userName'},
        {text: '地址', dataIndex: 'userAddress'},
        {text: '电话', dataIndex: 'userPhone'},
        {text: '备注', dataIndex: 'remark'},
        {text: '店铺经度', dataIndex: 'shopLng'},
        {text: '店铺纬度', dataIndex: 'shopLat'},
        {text: '用户经度', dataIndex: 'orderLng'},
        {text: '用户纬度', dataIndex: 'orderLat'},
    ]
});