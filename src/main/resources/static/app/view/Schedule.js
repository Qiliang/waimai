Ext.define('Kits.view.Schedule', {
    extend: 'Ext.grid.Panel',
    title: '调度管理',
    store: Ext.create('Kits.store.User'),
    columns: [
        {text: 'id', dataIndex: 'id'},
        {text: '名称', dataIndex: 'name'},
        {text: '登录名', dataIndex: 'loginName'},
        {text: '登录密码', dataIndex: 'loginPassword'},
        {text: '状态', dataIndex: 'alive'},
        {text: '角色', dataIndex: 'role'}
    ]
});