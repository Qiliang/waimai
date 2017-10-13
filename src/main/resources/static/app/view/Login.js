Ext.define('Kits.view.Login', {
    extend: 'Ext.container.Viewport',
    layout: 'center',
    items: {
        title: '登录',
        frame: true,
        width: 320,
        bodyPadding: 10,

        defaultType: 'textfield',

        items: [{
            allowBlank: false,
            fieldLabel: '登录名',
            name: 'user',
            emptyText: 'user id',
            msgTarget: 'under'
        }, {
            allowBlank: false,
            fieldLabel: '密 码',
            name: 'pass',
            emptyText: 'password',
            inputType: 'password'
        }],

        buttons: [
            {
                text: '进入',
                handler: function () {
                    var viewport = this.up('viewport');
                    viewport.mask();
                    Ext.Ajax.request({
                        method: 'POST',
                        url: '/api/login/',
                        params:{
                            username:viewport.down('textfield[name=user]').getValue(),
                            password:viewport.down('textfield[name=pass]').getValue(),
                        },
                        callback: function (options, success, response) {
                            if (!success) {
                                viewport.unmask();
                                Ext.toast({
                                    html: response.responseText,
                                    align: 't'
                                });
                            } else {
                                Ext.util.Cookies.set('token', response.responseText);
                                window.location.reload();
                            }
                        }
                    });
                }
            }
        ],

        defaults: {
            anchor: '100%',
            labelWidth: 120
        }
    }


});
