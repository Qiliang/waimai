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
            layout: 'fit',
            items: {
                xtype: 'cartesian',
                store:Ext.create('Kits.store.DailyOrder'),
                insetPadding: {
                    top: 40,
                    bottom: 40,
                    left: 20,
                    right: 40
                },
                axes: [{
                    type: 'numeric',
                    position: 'left',
                    minimum: 30,
                    titleMargin: 20,
                    title: {
                        text: 'Temperature in °F'
                    }
                }, {
                    type: 'category',
                    position: 'bottom'
                }],
                animation: Ext.isIE8 ? false : true,
                series: {
                    type: 'bar',
                    xField: 'month',
                    yField: 'highF',
                    style: {
                        minGapWidth: 20
                    },
                    highlight: {
                        strokeStyle: 'black',
                        fillStyle: 'gold'
                    },
                    label: {
                        field: 'highF',
                        display: 'insideEnd',
                        renderer: 'onSeriesLabelRender'
                    }
                },
                listeners: {
                   // afterrender: 'onAfterRender',
                }
            }
        }
    ]
});