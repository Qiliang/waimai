Ext.define('Kits.view.data.Shop', {
    extend: 'Ext.grid.Panel',
    title: '商家数据',
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
                url: '/api/stat/shops',
                params: {
                    from: Ext.Date.format(grid.down('datefield[name=from_date]').getValue(), 'Y-m-d'),
                    to: Ext.Date.format(grid.down('datefield[name=to_date]').getValue(), 'Y-m-d')
                },
                callback: function (options, success, response) {
                    grid.unmask();
                    if (!success)return;
                    var store = Ext.create('Ext.data.ArrayStore', {
                        fields: ['shop_name', 'num', 'price'],
                        data: Ext.decode(response.responseText)
                    });
                    grid.setStore(store);
                }

            });
        },

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
            flex: 1,
            text: '名称',
            dataIndex: 'shop_name',
            summaryRenderer: function (value, summaryData, dataIndex) {
                return '总计';
            }
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
    ]
});