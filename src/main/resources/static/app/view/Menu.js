Ext.define('Kits.view.Menu', {
    extend: 'Ext.list.Tree',
    rootVisible: false,
    useArrows: true,
    id: 'mainMenu',
    ui:'nav',
    listeners: {
        selectionchange: function (me, selected, eOpts) {
            var center = Ext.getCmp('center');
            center.removeAll(true);
            var cmp = selected.get('cmp')
            if (!cmp)return;
            center.add(Ext.create(cmp));
        }
    },
    store: {
        root: {
            text: 'Ext JS',
            expanded: true,
            children: [
                {
                    iconCls: 'x-fa fa-home',
                    text: '首页',
                    cmp: 'Kits.view.Home',
                    leaf: true
                },
                {
                    iconCls: 'x-fa fa-th',
                    text: '数据',
                    children: [
                        {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '运单数据',
                            cmp: 'Kits.view.Order'
                        },
                        {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '商家数据',
                            cmp: 'Kits.view.Order'
                        }, {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '骑手数据',
                            cmp: 'Kits.view.Order'
                        }
                    ]
                },
                {
                    iconCls: 'x-fa fa-shopping-cart',
                    text: '商家管理',
                    cmp: 'Kits.view.Shop',
                    leaf: true
                },
                {
                    iconCls: 'x-fa fa-server',
                    text: '运单管理',
                    cmp: 'Kits.view.Order',
                    leaf: true
                },
                {
                    iconCls: 'x-fa fa-map',
                    text: '调度管理',

                    children: [
                        {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '待指派',
                            cmp: 'Kits.view.schedule.Dzp'
                        },
                        {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '配送中',
                            cmp: 'Kits.view.schedule.Psz'
                        }, {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '异常订单',
                            cmp: 'Kits.view.schedule.Yc'
                        }, {
                            iconCls: 'x-fa fa-table',
                            leaf: true,
                            text: '完成订单',
                            cmp: 'Kits.view.schedule.Wc'
                        }
                    ]
                },
                {
                    iconCls: 'x-fa fa-bicycle',
                    text: '骑手管理',
                    cmp: 'Kits.view.Rider',
                    leaf: true
                },
                {
                    iconCls: 'x-fa fa-users',
                    text: '系统用户',
                    cmp: 'Kits.view.User',
                    leaf: true
                },
                {
                    text: 'app',
                    children: [
                        {leaf: true, text: 'Application.js'}
                    ]
                }


            ]
        }
    }

});
