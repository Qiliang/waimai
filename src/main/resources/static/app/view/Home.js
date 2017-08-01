Ext.define('Kits.view.Home', {
    extend: 'Ext.panel.Panel',
    title: '首页',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    items: [
        {
            xtype: 'panel',
            // title: '1111',
            flex: 2,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [
                {html: '店铺', flex: 1, border: true, frame: true},
                {html: '订单', flex: 1, border: true, frame: true},
                {html: '骑手', flex: 1, border: true, frame: true}
            ]
        },
        {
            xtype: 'panel',
            flex: 1,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [
                {html: '接单量', flex: 1, border: true, frame: true},
                {html: '完成单量', flex: 1, border: true, frame: true},
                {html: '异常取消单量', flex: 1, border: true, frame: true},
                {html: '超时单量', flex: 1, border: true, frame: true},
                {html: '平均配送时长', flex: 1, border: true, frame: true},
                {html: '活跃骑手', flex: 1, border: true, frame: true}
            ]
        },
        {
            xtype: 'panel',
            // title: '1111',
            flex: 3,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [
                {html: '111222', flex: 1, border: true, frame: true},
                {html: '111222', flex: 1, border: true, frame: true},
                {html: '111222', flex: 1, border: true, frame: true},
            ]
        }
    ]
});