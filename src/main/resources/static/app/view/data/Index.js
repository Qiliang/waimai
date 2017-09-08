Ext.define('Kits.view.data.Index', {
    extend: 'Ext.panel.Panel',
    title: '数据',
    layout: 'fit',

    tools: [
        {
            type: 'refresh',
            tooltip: '刷新',
            callback: function (panel, tool, event) {
                panel.load();
            }
        }
    ],

    listeners:{
      afterrender:function (panel) {
          panel.load();
      }
    },

    load:function(){
        var panel=this;
        panel.mask('正在统计，请耐心等待');
        Ext.Ajax.request({
            method: 'POST',
            timeout: 60 * 1000,
            url: '/api/stat/days',
            callback: function (options, success, response) {
                panel.unmask();
                if (!success)return;
                var store = panel.down('cartesian').getStore();
                store.loadData(Ext.decode(response.responseText));
            }

        });
    },

    items: [
        {
            xtype: 'panel',
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
                store: Ext.create('Ext.data.Store', {
                    fields: ['d', 'count']
                }),
                insetPadding: 40,
                innerPadding: {
                    left: 40,
                    right: 40
                },
                sprites: [{
                    type: 'text',
                    text: '订单概览',
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
                        return layoutContext.renderer(label);
                    },
                    label: {
                        rotate: {
                            degrees: -45
                        }
                    }
                }],
                series: [{
                    type: 'line',
                    xField: 'd',
                    yField: 'count',
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
                            tooltip.setHtml(record.get('d') + '日,订单:' + record.get('count'));
                        }
                    }
                }]

            }
        }

    ],
    bbar: [
        ''
    ]
});