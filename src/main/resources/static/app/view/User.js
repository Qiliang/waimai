Ext.define('Kits.view.User', {
    extend: 'Ext.grid.Panel',
    title: '系统用户',
    store: Ext.create('Kits.store.User'),
    selModel: {
        type: 'cellmodel'
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 2
    },

    tbar: [{
        text: '新增用户',
        handler: function () {
            var me = this.up('grid');
            var rec = Ext.create('Kits.model.User', {
                id: null
            });

            me.getStore().insert(0, rec);
        }
    }],

    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            text: '名称',
            dataIndex: 'name',
            editor: {
                allowBlank: false
            }
        },
        {
            text: '登录名',
            dataIndex: 'loginName',
            editor: {
                allowBlank: false
            }
        },
        {
            text: '登录密码',
            dataIndex: 'loginPassword',
            editor: {
                allowBlank: false
            }
        },
        {
            text: '状态',
            dataIndex: 'alive',
            editor: {
                xtype: 'checkbox',
                cls: 'x-grid-checkheader-editor'
            }
        },
        {
            text: '角色',
            dataIndex: 'role',
            editor: {
                allowBlank: false,
                xtype: 'combo',
                displayField: 'name',
                valueField: 'value',
                store: Ext.create('Ext.data.Store', {
                    fields: ['name', 'value'],
                    data: [{name: "管理员", value: "ADMIN"}, {name: "调度员", value: "SCHEDULE"}]
                })
                // data:['a','b','c']
            }
        },
        {
            xtype: 'actioncolumn',
            width: 30,

            sortable: false,
            menuDisabled: true,
            items: [{
                iconCls: 'x-fa fa-ban',
                tooltip: '删除',
                style: {
                    width: '95%',
                    color: 'red'
                },
                handler: function (view, recIndex, cellIndex, item, e, record) {
                    Ext.Msg.confirm('确认', '确认删除?', function (r) {
                        if (r == 'yes') record.drop();
                    }, this);
                }
            }]
        }

    ]
})
;