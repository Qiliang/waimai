Ext.define('Kits.view.schedule.Wc', {
    extend: 'Ext.panel.Panel',
    title: '调度--完成订单',
    store: Ext.create('Kits.store.User'),
    layout: 'fit',
    requires: ['Kits.view.BMap'],
    items: {xtype: 'bmap'},

    afterComponentLayout: function (w, h) {
        this.callParent(arguments);

    },
});