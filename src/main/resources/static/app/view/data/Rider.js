Ext.define('Kits.view.data.Rider', {
    extend: 'Ext.grid.Panel',
    title: '骑手数据',
    features: [{
        ftype: 'summary'
    }],
    plugins: [{
        ptype: 'gridexporter'
    }],
    tbar: [{
        xtype: 'datefield',
        fieldLabel: '起始日期',
        format: 'Y/m/d',
        name: 'from_date',
        value: new Date()
    }, {
        xtype: 'datefield',
        fieldLabel: '结束日期',
        format: 'Y/m/d',
        name: 'to_date',
        value: new Date()
    }, {
        iconCls: 'x-fa fa-server',
        text: '统计',
        handler: function () {
            var grid = this.up('grid');
            grid.mask('正在统计，请耐心等待');

            Ext.Ajax.request({
                method: 'POST',
                timeout: 60 * 1000,
                url: '/api/stat/riders',
                params: {
                    status: 8,
                    from: Ext.Date.format(grid.down('datefield[name=from_date]').getValue(), 'Y-m-d'),
                    to: Ext.Date.format(grid.down('datefield[name=to_date]').getValue(), 'Y-m-d')
                },
                callback: function (options, success, response) {
                    grid.unmask();
                    if (!success)return;
                    var store = Ext.create('Ext.data.Store', {
                        fields: ['rider_id', 'num', 'price', 'rprice', 'name', 'phone'],
                        data: Ext.decode(response.responseText)
                    });
                    grid.setStore(store);
                }

            });
        }
    }, {
        iconCls: 'x-fa fa-server',
        text: '导出',
        handler: function () {
            var grid = this.up('grid');
            grid.saveDocumentAs({
                type: 'xlsx',
                title: 'My export',
                fileName: 'myExport.xlsx'
            });
        }
    }],
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            text: 'id', dataIndex: 'rider_id',
            summaryRenderer: function (value, summaryData, dataIndex) {
                return '总计';
            }
        },
        {
            flex: 1,
            text: '名称',
            dataIndex: 'name'
        },
        {
            flex: 1,
            text: '手机',
            dataIndex: 'phone'
        },
        {
            flex: 1,
            text: '订单数量',
            dataIndex: 'num',
            summaryType: 'sum',
            summaryRenderer: function (value, summaryData, dataIndex) {
                return Ext.String.format('{0}', value);
            }
        }, {
            flex: 1,
            text: '运单金额',
            dataIndex: 'rprice',
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                return "￥ " + value;
            },
            summaryType: 'sum',
            summaryRenderer: function (value, summaryData, dataIndex) {
                return Ext.String.format('￥ {0}', value);
            }
        },
        {
            flex: 1,
            text: '订单金额',
            dataIndex: 'price',
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                return "￥ " + value;
            },
            summaryType: 'sum',
            summaryRenderer: function (value, summaryData, dataIndex) {
                return Ext.String.format('￥ {0}', value);
            }
        },
        {
            xtype: 'actioncolumn',
            width:50,
            items: [
                {
                    iconCls: 'x-fa fa-th-large',
                    tooltip: '查看每日',
                    handler: function (view, recIndex, cellIndex, item, e, record) {
                        var me = view.up('grid');
                        me.mask();
                        Ext.Ajax.request({
                            method: 'POST',
                            url: '/api/stat/days/riders/' + record.get('rider_id'),
                            params: {
                                status: 8,
                                from: Ext.Date.format(me.down('datefield[name=from_date]').getValue(), 'Y-m-d'),
                                to: Ext.Date.format(me.down('datefield[name=to_date]').getValue(), 'Y-m-d')
                            },
                            callback: function (options, success, response) {
                                me.unmask();
                                if (!success) {
                                    Ext.toast({
                                        html: response.responseText,
                                        align: 't'
                                    });
                                } else {
                                    Ext.create('Ext.window.Window', {
                                        title: record.get('name'),
                                        width: 400,
                                        height: 500,
                                        layout: 'fit',
                                        modal: true,
                                        items: {
                                            xtype: 'grid',
                                            border: false,
                                            columns: [
                                                {
                                                    flex: 1,
                                                    text: '日期',
                                                    dataIndex: 'd'
                                                },
                                                {
                                                    flex: 1,
                                                    text: '运单数量',
                                                    dataIndex: 'count'
                                                },
                                                {
                                                    flex: 1,
                                                    text: '运单金额',
                                                    dataIndex: 'rprice'
                                                }
                                            ],
                                            store: Ext.create('Ext.data.Store', {
                                                fields: ['d', 'count', 'rprice'],
                                                data: Ext.decode(response.responseText)
                                            })
                                        }
                                    }).show();
                                }
                            }
                        });
                    }
                }, {
                    iconCls: 'x-fa fa-th',
                    tooltip: '查看详情',
                    handler: function (view, recIndex, cellIndex, item, e, record) {
                        var me = view.up('grid');
                        me.mask();
                        Ext.Ajax.request({
                            method: 'POST',
                            url: '/api/rider/stat/details',
                            params: {
                                status: 8,
                                token: record.get('rider_id'),
                                from: Ext.Date.format(me.down('datefield[name=from_date]').getValue(), 'Y-m-d'),
                                to: Ext.Date.format(me.down('datefield[name=to_date]').getValue(), 'Y-m-d')
                            },
                            callback: function (options, success, response) {
                                me.unmask();
                                if (!success) {
                                    Ext.toast({
                                        html: response.responseText,
                                        align: 't'
                                    });
                                } else {
                                    Ext.create('Ext.window.Window', {
                                        title: record.get('name'),
                                        width: 900,
                                        height: 500,
                                        layout: 'fit',
                                        modal: true,
                                        items: {
                                            xtype: 'grid',
                                            border: false,
                                            columns: [
                                                {
                                                    flex: 1,
                                                    text: '时间',
                                                    xtype: 'datecolumn',
                                                    format: 'Y-m-d',
                                                    dataIndex: 'time'
                                                },
                                                {
                                                    flex: 2,
                                                    text: '店铺名称',
                                                    dataIndex: 'shop_name'
                                                },
                                                {
                                                    flex: 1,
                                                    text: '用户名称',
                                                    dataIndex: 'recipient_name'
                                                }, {
                                                    flex: 2,
                                                    text: '用户地址',
                                                    dataIndex: 'recipient_address'
                                                }, {
                                                    flex: 1,
                                                    text: '运单金额',
                                                    dataIndex: 'shipping_fee'
                                                }, {
                                                    flex: 1,
                                                    text: '订单金额',
                                                    dataIndex: 'total'
                                                }
                                            ],
                                            store: Ext.create('Ext.data.Store', {
                                                fields: [
                                                    {
                                                        name: 'time',
                                                        type: 'date',
                                                        dateFormat: 'time'
                                                    },
                                                    'shopName', 'recipientName', 'recipientAddress','shippingFee','total'],
                                                data: Ext.decode(response.responseText)
                                            })
                                        }
                                    }).show();
                                }
                            }
                        });
                    }



                }
            ]
        }

    ]

});