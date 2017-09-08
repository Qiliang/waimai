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
                    from: Ext.Date.format(grid.down('datefield[name=from_date]').getValue(), 'Y-m-d'),
                    to: Ext.Date.format(grid.down('datefield[name=to_date]').getValue(), 'Y-m-d')
                },
                callback: function (options, success, response) {
                    grid.unmask();
                    if (!success)return;
                    var store = Ext.create('Ext.data.ArrayStore', {
                        fields: ['rider_id', 'num', 'price', 'name', 'phone'],
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
        }

    ],
    bbar: [
        ''
    ]
});