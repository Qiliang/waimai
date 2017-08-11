Ext.define('Kits.view.schedule.Psz', {
    extend: 'Ext.grid.Panel',
    title: '调度--配送中',
    plugins: [{
        ptype: 'rowediting',
        clicksToMoveEditor: 1,
        autoCancel: false,
        listeners: {
            edit: function (editor, context, eOpts) {
                debugger
                var displayName = context.record.get('riderName');
                var orderId = context.record.get('id');
                Ext.Ajax.request({
                    method: 'POST',
                    url: '/meituan/orders/' + orderId + '/reasign/' + displayName,
                    success: function (response, opts) {

                    },
                    failure: function (response, opts) {
                        console.log('server-side failure with status code ' + response.status);
                    }
                });
            }
        }
    }],
    store: Ext.create('Kits.store.Order', {
        proxy: {
            type: 'rest',
            url: '/meituan/orders?state=psz'
        }
    }),

    layout: 'fit',

    tools: [
        {
            type: 'refresh',
            tooltip: '刷新',
            callback: function (panel, tool, event) {
                panel.getStore().load();
            }
        }
    ],

    columns: [
        {text: 'id', dataIndex: 'id', width: 170},
        {
            text: '骑手', dataIndex: 'riderName', width: 160,
            editor: {
                xtype: 'combo',
                displayField: 'displayName',
                valueField: 'displayName',
                store: Ext.create('Kits.store.Rider')
            }
        },
        {text: '指派时间', dataIndex: 'riderAssignTime', width: 150, xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '描述', dataIndex: 'description'},
        {text: '时间', dataIndex: 'time', xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '状态', dataIndex: 'state'},
        {text: '客户', dataIndex: 'userName'},
        {text: '地址', dataIndex: 'userAddress'},
        {text: '电话', dataIndex: 'userPhone'},
        {text: '备注', dataIndex: 'remark'}
    ]
});