Ext.define('Kits.view.Main', {
    extend: 'Ext.container.Viewport',
    xtype: 'layout-border',
    requires: [
        'Ext.layout.container.Border'
    ],
    layout: 'border',

    bodyBorder: false,

    defaults: {
        collapsible: false,
        split: true,
        bodyPadding: 10
    },

    items: [
        {
            width: 250,
            split: true,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            border: false,
            scrollable: 'y',
            title: '菜单',
            region: 'west',
            colspan: 2,
            items:Ext.create('Kits.view.Menu',{})

        },
        {
            collapsible: false,
            id:'center',
            region: 'center',
            margin: '5 0 0 0',
            //items:Ext.create('Kits.view.Shop',{})
        }
    ]

});
