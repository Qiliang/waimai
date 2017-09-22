Ext.define('Kits.view.Shop', {
    extend: 'Ext.grid.Panel',
    title: '商家',
    store: Ext.create('Kits.store.Shop'),
    tools: [
        {
            type: 'refresh',
            tooltip: '刷新',
            callback: function (panel, tool, event) {
                panel.getStore().load();
            }
        }
    ],
    plugins: [{
        ptype: 'rowediting',
        clicksToMoveEditor: 1,
        autoCancel: false
    }],
    tbar: [{
        iconCls: 'x-fa fa-plus',
        text: '商家',
        handler: function () {
            var me = this.up('grid');
            var rec = Ext.create('Kits.model.Shop', {
                id: null
            });

            me.getStore().insert(0, rec);
        }
    }
    ],
    columns: [
        {
            text: 'id',
            dataIndex: 'id'
        },
        {
            flex:1,
            text: '名称',
            dataIndex: 'name',
            editor: {
                allowBlank: false
            }
        },
        {
            flex:2,
            text: '地址',
            dataIndex: 'address',
            editor: {
                allowBlank: false
            }
        },
        {
            flex:1,
            text: '电话',
            dataIndex: 'phone',
            editor: {
                allowBlank: false,
                regex: /^(0|86|17951)?(13[0-9]|15[012356789]|17[012356789]|18[0-9]|14[57])[0-9]{8}$/,
                invalidText: '请输入正确的手机号码'
            }
        },
        {
            flex: 1,
            text: '外送单价',
            dataIndex: 'mpt',
            editor: {
                allowBlank: false,
                xtype:'numberfield',
                maxValue: 99,
                minValue: 1
            }
        },
        {
            flex:1,
            xtype: 'actioncolumn',
            sortable: false,
            menuDisabled: true,
            items: [
                {
                    iconCls: 'actionColumnRed x-fa fa-ban',
                    tooltip: '删除',
                    handler: function (view, recIndex, cellIndex, item, e, record) {
                        Ext.Msg.confirm('确认', '确认删除?', function (r) {
                            if (r == 'yes') record.drop();
                        }, this);
                    }
                }
            ]
        }
    ]
});