Ext.define('Kits.Application', {
    extend: 'Ext.app.Application',
    namespace: 'Kits',

    requires: [
        'Kits.override.ProxyServer',
        'Kits.override.Sorter',
        'Kits.view.Main'
    ],

    // controllers: [
    //     'Global',
    //     'Samples',
    //     'Direct'
    // ],

    init: function () {
        //Ext.setGlyphFontFamily('Pictos');
        Ext.tip.QuickTipManager.init();
        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));

    },

    launch: function () {
        var view = 'Kits.view.Main';
        if (/[?&]solo\b/.test(location.search)) {
            view = 'Kits.view.main.Solo'
        }
        this.setMainView({
            xclass: view
        });
    }
}, function () {
    Kits.toast = function (title) {
        var html = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));

        if (!html) {
            html = title;
            title = null;
        }

        new Ext.window.Toast({
            title: title,
            html: html,
            width: 400,
            align: 't'
        }).show();
    };
});
