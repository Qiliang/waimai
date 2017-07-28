Ext.define('Kits.store.Order', {
    extend: 'Ext.data.Store',
    model: 'Kits.model.Order',
    proxy: {
        type: 'rest',
        url: '/meituan/orders'
    },
    autoLoad: true

});