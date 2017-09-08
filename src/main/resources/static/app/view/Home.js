Ext.define('Kits.view.Home', {
    extend: 'Ext.panel.Panel',
    title: '首页',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    tools: [
        {
            type: 'refresh',
            tooltip: '刷新',
            callback: function (panel, tool, event) {
                panel.down('*[itemId=dailyG]').getStore().load();
                panel.stat();
            }
        }
    ],

    stat: function () {
        var me = this;
        me.mask('正在统计，请耐心等待');
        Ext.Ajax.request({
            method: 'GET',
            url: '/api/stat/',
            success: function (response, opts) {
                if (!me.rendered)return;
                me.unmask();
                var stat = Ext.decode(response.responseText);
                var shopPanel = me.down('panel[itemId=shop]');
                shopPanel.removeAll();
                shopPanel.add({
                    xtype: 'button',
                    iconCls: 'fa fa-home',
                    text: '店铺：' + stat.shopCount,
                    iconAlign: 'top',
                    width: 200,
                    height: 200
                });
                var orderPanel = me.down('panel[itemId=order]');
                orderPanel.removeAll();
                orderPanel.add({
                    xtype: 'button',
                    iconCls: 'fa fa-list',
                    text: '订单：' + stat.orderCount,
                    iconAlign: 'top',
                    width: 200,
                    height: 200
                });
                var riderPanel = me.down('panel[itemId=rider]');
                riderPanel.removeAll();
                riderPanel.add({
                    xtype: 'button',
                    iconCls: 'fa fa-users',
                    text: '骑手：' + stat.riderCount,
                    iconAlign: 'top',
                    width: 200,
                    height: 200
                });

                var dzpPanel = me.down('panel[itemId=dzp]');
                dzpPanel.removeAll();
                dzpPanel.add({
                    xtype: 'button',
                    text: '待指派：' + stat.orderPszCount,
                    width: 200,
                    height: 200
                });

                var dqcPanel = me.down('panel[itemId=dqc]');
                dqcPanel.removeAll();
                dqcPanel.add({
                    xtype: 'button',
                    text: '待取餐：' + stat.orderDqcCount,
                    width: 200,
                    height: 200
                });

                var wcPanel = me.down('panel[itemId=wc]');
                wcPanel.removeAll();
                wcPanel.add({
                    xtype: 'button',
                    text: '完成订单：' + stat.orderWcCount,
                    width: 200,
                    height: 200
                });

                var ycPanel = me.down('panel[itemId=yc]');
                ycPanel.removeAll();
                ycPanel.add({
                    xtype: 'button',
                    text: '异常订单：' + stat.orderYcCount,
                    width: 200,
                    height: 200
                });

                var coastPanel = me.down('panel[itemId=coast]');
                coastPanel.removeAll();
                coastPanel.add({
                    style: {
                        fontSize: '20px!important'
                    },
                    xtype: 'button',
                    text: '平均配送时间：' + stat.orderCoast + '分钟',
                    width: 200,
                    height: 200
                });

            },
            failure: function (response, opts) {
                me.unmask();
                console.log('server-side failure with status code ' + response.status);
            }
        });
    },

    listeners: {
        afterrender: function (me) {
            me.stat();
        }
    },
    items: [
        {
            xtype: 'panel',
            flex: 2,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            defaults:{
                items:{
                    xtype: 'button',
                    iconCls: 'fa fa-home',
                    text: '正在加载...',
                    iconAlign: 'top',
                    width: 200,
                    height: 200
                }
            },
            items: [
                {layout: 'center', itemId: 'shop', flex: 1},
                {layout: 'center', itemId: 'order', flex: 1},
                {layout: 'center', itemId: 'rider', flex: 1}
            ]
        },
        {
            xtype: 'panel',
            flex: 1,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            defaults:{
                items:{
                    xtype: 'button',
                    text: '正在加载...',
                    width: 200,
                    height: 200
                }
            },
            items: [
                {itemId: 'dzp', flex: 1},
                {itemId: 'dqc', flex: 1},
                {itemId: 'wc', flex: 1},
                {itemId: 'yc', flex: 1},
                {itemId: 'coast', flex: 1}
            ]
        },
        {
            xtype: 'panel',
            flex: 3,
            layout: 'fit',
            items: {
                xtype: 'cartesian',
                reference: 'chart',
                width: '100%',
                margin: '20 0 0 0',
                itemId: "dailyG",
                //height: 500,
                interactions: {
                    type: 'panzoom',
                    zoomOnPanGesture: true
                },
                animation: {
                    duration: 200
                },
                store: Ext.create('Kits.store.DailyOrder'),
                insetPadding: 40,
                innerPadding: {
                    left: 40,
                    right: 40
                },
                sprites: [{
                    type: 'text',
                    text: '当日订单：' + Ext.Date.format(new Date(), 'Y-m-d'),
                    fontSize: 22,
                    width: 100,
                    height: 30,
                    x: 40,
                    y: 20
                }],
                axes: [{
                    type: 'numeric',
                    position: 'left',
                    grid: true,
                    //minimum: 0,
                    //maximum: 24,
                    //renderer: 'onAxisLabelRender'
                }, {
                    type: 'category',
                    position: 'bottom',
                    grid: true,
                    renderer: function (axis, label, layoutContext) {
                        return layoutContext.renderer(label) + '点';
                    },
                    label: {
                        rotate: {
                            degrees: -45
                        }
                    }
                }],
                series: [{
                    type: 'line',
                    xField: 'hour',
                    yField: 'orders',
                    style: {
                        lineWidth: 2
                    },
                    marker: {
                        radius: 4,
                        lineWidth: 2
                    },
                    label: {
                        field: 'orders',
                        display: 'under'
                    },
                    highlight: {
                        fillStyle: '#000',
                        radius: 5,
                        lineWidth: 2,
                        strokeStyle: '#fff'
                    },
                    tooltip: {
                        trackMouse: true,
                        showDelay: 0,
                        dismissDelay: 0,
                        hideDelay: 0,
                        renderer: function (tooltip, record, item) {
                            tooltip.setHtml(record.get('hour') + '点,订单:' + record.get('orders'));
                        }
                    }
                }]

            }
        }
    ]
});