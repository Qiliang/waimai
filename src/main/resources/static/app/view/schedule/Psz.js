Ext.define('Kits.view.schedule.Psz', {
    extend: 'Ext.panel.Panel',
    title: '调度--配送中',
    store: Ext.create('Kits.store.User'),
    layout: 'fit',
    requires: ['Kits.view.BMap'],
    items: {xtype: 'bmap'},

    afterComponentLayout: function (w, h) {
        this.callParent(arguments);



    },
});