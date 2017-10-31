Ext.define('Kits.view.BMap', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.bmap',
    requires: ['Ext.window.MessageBox'],
    // height: 250,

    html: '<div style="height: 900px" id="allmap"></div>',
    mQi: new BMap.Icon('/marker/Marker_qi.png', new BMap.Size(48, 48)),
    mQu: new BMap.Icon('/marker/Marker_qu.png', new BMap.Size(48, 48)),
    mSong: new BMap.Icon('/marker/Marker_song.png', new BMap.Size(48, 48)),


    initComponent : function(){
        Ext.applyIf(this,{
            plain: true,
            gmapType: 'map',
            border: false
        });

        this.callParent(arguments);
    },

    onBoxReady: function (width, height) {
        var center = this.center;
        this.callParent(arguments);
        // 百度地图API功能
        this.bmap = new BMap.Map('allmap', {minZoom: 14, maxZoom: 18});    // 创建Map实例
        this.bmap.centerAndZoom('宜昌', 16);  // 初始化地图,设置中心点坐标和地图级别
        this.bmap.addControl(new BMap.MapTypeControl());   //添加地图类型控件
        this.bmap.setCurrentCity("宜昌");          // 设置地图显示的城市 此项是必须设置的
        this.bmap.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    },


    markerOrderClear: function () {
        var me = this;
        if (me.orderMarkers && me.orderMarkers.length > 0) {
            for (var i = 0; i < me.orderMarkers.length; i++) {
                me.bmap.removeOverlay(me.orderMarkers[i]);
            }
        }
    },

    markerOrder: function (recs) {
        if (!recs || recs.length == 0)return;
        var me = this;
        me.riderStore.load();
        Ext.Array.each(recs, function (rec, index) {

            var orderLng = parseFloat(rec.get('recipientLng')) / 1000000;
            var orderLat = parseFloat(rec.get('recipientLat')) / 1000000;
            var shopLng = parseFloat(rec.get('shopLng')) / 1000000;
            var shopLat = parseFloat(rec.get('shopLat')) / 1000000;
            var convertor = new BMap.Convertor();
            me.orderMarkers = [];
            convertor.translate([new BMap.Point(orderLng, orderLat), new BMap.Point(shopLng, shopLat)], 3, 5, function (data) {

                var markerSong = new BMap.Marker(data.points[0]);
                me.orderMarkers.push(markerSong);
                markerSong.setIcon(me.mSong);
                me.bmap.addOverlay(markerSong);
                var recipientAddress =rec.get('recipientAddress').split('@#')[0];
                markerSong.setLabel(new BMap.Label(recipientAddress, {offset: new BMap.Size(20, -10)}));
                markerSong.addEventListener("mouseover", me.markerMouseover.bind(markerSong));
                markerSong.addEventListener("mouseout", me.markerMouseout.bind(markerSong));


                var markerQu = new BMap.Marker(data.points[1]);
                me.orderMarkers.push(markerQu);
                markerQu.setIcon(me.mQu);
                me.bmap.addOverlay(markerQu);
                var statusDesc = ''
                if (rec.get('status') === 12) statusDesc = '[已取]'
                if (rec.get('status') === 11) statusDesc = '[未取]'
                markerQu.setLabel(new BMap.Label(rec.get('shopName') + statusDesc, {offset: new BMap.Size(20, -10)}));
                markerQu.addEventListener("mouseover", me.markerMouseover.bind(markerQu));
                markerQu.addEventListener("mouseout", me.markerMouseout.bind(markerQu));
                markerSong.addEventListener('dblclick', function () {
                    Ext.create('Ext.window.Window', {
                        modal: true,
                        layout: 'fit',
                        title: '选择骑手',
                        listeners: {
                            afterrender: function (view) {
                                view.down('combo').expand();
                            }
                        },
                        items: {
                            xtype: 'combo',
                            forceSelection: true,
                            store: Ext.create('Kits.store.Rider', {
                                autoLoad: true,
                                filters: [
                                    function (item) {
                                        return item.get('status') === 1;
                                    }
                                ],
                                sorters: {property: 'status', direction: 'DESC'}
                            }),
                            displayField: 'displayName',
                            valueField: 'riderId',
                            listeners: {
                                select: function (combo, record, eOpts) {
                                    me.reasignToRider(record.get('id'), record.get('displayName'), rec.get('id'), function () {
                                        combo.up('window').close();
                                    });
                                }
                            }

                        }
                    }).show();
                }.bind(me));

                var line = new BMap.Polyline(data.points);
                me.orderMarkers.push(line);
                line.setStrokeOpacity(0.5);
                line.setStrokeStyle("dashed");
                me.bmap.addOverlay(line);
                if (index === (recs.length - 1)) {
                    console.log(index)
                    me.bmap.panTo(data.points[1]);
                }

            });
        });


    },

    markerMouseover: function () {
        this.setTop(true)
    },

    markerMouseout: function () {
        this.setTop(false)
    },

    afterRender: function () {
        this.callParent(arguments);

        var me = this;
        me.riderStore = Ext.create('Kits.store.Rider', {
            proxy: {
                extraParams: {status: 1}
            },
            listeners: {
                load: function (store, records, successful, operation, eOpts) {
                    if (!successful)return;
                    if (me.riderMarkers) {
                        for (var i = 0; i < me.riderMarkers.length; i++) {
                            me.bmap.removeOverlay(me.riderMarkers[i]);
                        }
                    } else {
                        me.riderMarkers = []
                    }
                    Ext.each(records, function (rec) {
                        var lng = parseFloat(rec.get('lng')) / 1000000;
                        var lat = parseFloat(rec.get('lat')) / 1000000;
                        // lng += Ext.Number.randomInt(1, 10) / 1000.0;
                        // lat += Ext.Number.randomInt(1, 10) / 1000.0;
                        if (rec.get('status') != 1)return;
                        var riderId = rec.get("id");
                        var displayName = rec.get("displayName");
                        if (isNaN(lng) || isNaN(lat))return;

                        var label = new BMap.Label(rec.get('name') + ":" + rec.get('orderCount'), {offset: new BMap.Size(20, -10)});
                        var marker = new BMap.Marker(new BMap.Point(lng, lat));
                        me.riderMarkers.push(marker);
                        marker.addEventListener("mouseover", me.markerMouseover.bind(marker));
                        marker.addEventListener("mouseout", me.markerMouseout.bind(marker));
                        marker.setIcon(me.mQi);
                        marker.setZIndex(10);
                        me.bmap.addOverlay(marker);
                        marker.setLabel(label);
                        // var markerMenu = new BMap.ContextMenu();
                        // markerMenu.addItem(new BMap.MenuItem('指派',
                        //     function () {
                        //         console.log(riderId)
                        //         me.asignToRider(riderId, displayName);
                        //     }.bind(me)));
                        // marker.addContextMenu(markerMenu);

                        marker.addEventListener('rightclick', function () {
                            me.asignToRider(riderId, displayName);
                        }.bind(me));


                        marker.addEventListener("click", function () {

                            Ext.Ajax.request({
                                method: 'POST',
                                url: '/api/rider/orders',
                                params: {
                                    token: rec.get('id'),
                                    status: 11,
                                },
                                callback: function (options, success, response) {
                                    if (!success) {
                                        Ext.toast({
                                            html: response.responseText,
                                            align: 't'
                                        });
                                    } else {
                                        Ext.create('Ext.window.Window', {
                                            title: rec.get('name') + ':订单列表',
                                            modal: true,
                                            height: 500,
                                            width: 700,
                                            layout: 'fit',
                                            items: {
                                                xtype: 'grid',
                                                store: Ext.create('Ext.data.Store', {
                                                    model: 'Kits.model.Order',
                                                    data: Ext.decode(response.responseText).list
                                                }),
                                                plugins: [{
                                                    ptype: 'rowediting',
                                                    clicksToMoveEditor: 1,
                                                    autoCancel: false,
                                                    listeners: {
                                                        edit: function (editor, context, eOpts) {
                                                            var displayName = context.record.get('riderName');
                                                            var orderId = context.record.get('id');
                                                            Ext.Ajax.request({
                                                                method: 'POST',
                                                                url: '/api/orders/' + orderId + '/reasign/' + displayName,
                                                                callback: function (options, success, response) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                }],
                                                columns: [
                                                    {
                                                        text: '指派时间',
                                                        dataIndex: 'riderAssignTime',
                                                        width: 150,
                                                        xtype: 'datecolumn',
                                                        format: 'Y-m-d H:i:s'
                                                    },
                                                    {
                                                        text: '订单时间',
                                                        dataIndex: 'time',
                                                        xtype: 'datecolumn',
                                                        width: 150,
                                                        format: 'Y-m-d H:i:s'
                                                    },
                                                    {text: '店铺', flex: 1, dataIndex: 'shopName'},
                                                    {text: '客户地址', flex: 1, dataIndex: 'recipientAddress'},
                                                    {
                                                        text: '骑手', dataIndex: 'riderName', width: 160,
                                                        editor: {
                                                            xtype: 'combo',
                                                            displayField: 'displayName',
                                                            valueField: 'displayName',
                                                            store: Ext.create('Kits.store.Rider')
                                                        }
                                                    }
                                                ],
                                            }
                                        }).show();
                                    }
                                }
                            });


                        });

                    })
                }
            }

        });

        me.riderTask = Ext.TaskManager.start({
            run: function () {
                me.riderStore.load();
            },
            interval: 5000
        });

    }




});