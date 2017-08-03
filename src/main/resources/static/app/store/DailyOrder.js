Ext.define('Kits.store.DailyOrder', {
    extend: 'Ext.data.Store',
    model: 'Kits.model.Order',
    proxy: {
        type: 'rest',
        url: '/meituan/dailyOrders'
    },
    autoLoad: true

});