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
        split: false,
        bodyPadding: 10
    },
    listeners: {
        afterRender: function (me, eOpts) {
            var menu = Ext.ComponentQuery.query('#mainMenu')[0];
            menu.setSelection(menu.getStore().getAt(0))
            //menu.setSelection();
            console.log(menu)
        }
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
            items: Ext.create('Kits.view.Menu', {}),
            tools: [
                {
                    type: 'unpin',
                    id:'menuUnpin',
                    tooltip: '收起菜单',
                    callback: function (panel, tool, event) {
                        panel.down('#menuPin').show();
                        panel.down('#mainMenu').setMicro(true)
                        this.hide();
                    }
                },
                {
                    type: 'pin',
                    id:'menuPin',
                    hidden: true,
                    tooltip: '展开菜单',
                    callback: function (panel, tool, event) {
                        panel.down('#menuUnpin').show();
                        panel.down('#mainMenu').setMicro(true)
                        this.hide();
                    }
                }
            ],

        },
        {
            collapsible: false,
            id:'center',
            layout: 'fit',
            region: 'center',
            margin: '5 0 0 0',
            //items:Ext.create('Kits.view.Shop',{})
        }
    ]

});
