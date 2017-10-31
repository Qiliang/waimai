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
    store: Ext.create('Kits.store.Order', {proxy: {extraParams: {status: 12}}}),
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
            text: '骑手', dataIndex: 'riderDisplayName', width: 160,
            // editor: {
            //     xtype: 'combo',
            //     displayField: 'displayName',
            //     valueField: 'displayName',
            //     store: Ext.create('Kits.store.Rider')
            // }
        },
        {text: 'id', dataIndex: 'id',flex:1},
        {text: '当日序号', dataIndex: 'daySeq',width:80},
        {text: '下单时间', dataIndex: 'time', width: 150, xtype: 'datecolumn', format: 'Y-m-d H:i:s'},
        {text: '状态', dataIndex: 'status',flex:1},
        {text: '客户', dataIndex: 'recipientName',flex:1},
        {text: '地址', dataIndex: 'recipientAddress',flex:1},
        {text: '电话', dataIndex: 'recipientPhone',flex:1},
        {text: '店铺', dataIndex: 'shopName',flex:1},
        {text: '店铺地址', dataIndex: 'shopAddress',flex:1},
        {text: '店铺电话', dataIndex: 'shopPhone',flex:1}
    ],

    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        displayMsg: 'Displaying topics {0} - {1} of {2}',
        emptyMsg: "No topics to display"
    }
});