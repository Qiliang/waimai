Ext.define('Kits.store.Order', {
    extend: 'Ext.data.Store',
    model: 'Kits.model.Order',
    proxy: {
        type: 'rest',
        url: '/api/orders',
        limitParam: 'size',
        pageParam: 'page',
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'total',
        }
    },
    remoteSort: true,
    sorters: [{
        property: 'time',
        direction: 'DESC'
    }],
    autoLoad: true

});