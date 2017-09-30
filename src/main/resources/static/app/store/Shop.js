Ext.define('Kits.store.Shop', {
    extend: 'Ext.data.Store',
    model: 'Kits.model.Shop',
    proxy: {
        type: 'rest',
        url: '/api/shops'
    },
    autoLoad: true,
    autoSync: true
});