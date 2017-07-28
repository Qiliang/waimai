Ext.define('Kits.view.Shop', {
    extend: 'Ext.grid.Panel',
    title: '商家',
    store: Ext.create('Kits.store.Shop'),
    columns: [
        {text: 'id', dataIndex: 'id'},
        {text: '名称', dataIndex: 'name'},
        {text: '地址', dataIndex: 'address'},
        {text: '登录名', dataIndex: 'loginName'},
        {text: '登录密码', dataIndex: 'loginPassword'},
        {text: '电话', dataIndex: 'phone'}
    ]
});