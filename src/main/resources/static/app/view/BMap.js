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
        this.bmap.centerAndZoom('宜昌', 15);  // 初始化地图,设置中心点坐标和地图级别
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

    markerOrder: function (rec) {
        var me = this;
        var orderLng = parseFloat(rec.get('orderLng')) / 1000000;
        var orderLat = parseFloat(rec.get('orderLat')) / 1000000;
        var shopLng = parseFloat(rec.get('shopLng')) / 1000000;
        var shopLat = parseFloat(rec.get('shopLat')) / 1000000;
        var convertor = new BMap.Convertor();
        me.orderMarkers = [];
        convertor.translate([new BMap.Point(orderLng, orderLat), new BMap.Point(shopLng, shopLat)], 3, 5, function (data) {

            var markerSong = new BMap.Marker(data.points[0]);
            me.orderMarkers.push(markerSong);
            markerSong.setIcon(me.mSong);
            me.bmap.addOverlay(markerSong);
            markerSong.setLabel(new BMap.Label(rec.get('userAddress'), {offset: new BMap.Size(20, -10)}));

            var markerQu = new BMap.Marker(data.points[1]);
            me.orderMarkers.push(markerQu);
            markerQu.setIcon(me.mQu);
            me.bmap.addOverlay(markerQu);
            markerQu.setLabel(new BMap.Label(rec.get('shopName'), {offset: new BMap.Size(20, -10)}));

            me.bmap.panTo(data.points[0]);
            var line = new BMap.Polyline(data.points);
            me.orderMarkers.push(line);
            line.setStrokeOpacity(0.5);
            line.setStrokeStyle("dashed");
            me.bmap.addOverlay(line);
            //var markerMenu = new BMap.ContextMenu();
            //markerMenu.addItem(new BMap.MenuItem('指定骑手', me.asignToRider.bind(marker)));
            //marker.addContextMenu(markerMenu);
        });


    }
    ,

    afterRender: function () {
        this.callParent(arguments);

        var me = this;
        Ext.create('Kits.store.Rider', {
            listeners: {
                load: function (store, records, successful, operation, eOpts) {
                    if (!successful)return;
                    console.log(records)
                    Ext.each(records, function (rec) {
                        var lng = parseFloat(rec.get('lng')) / 1000000;
                        var lat = parseFloat(rec.get('lat')) / 1000000;
                        var riderId = rec.get("id");
                        if (isNaN(lng) || isNaN(lat))return;
                        var convertor = new BMap.Convertor();
                        convertor.translate([new BMap.Point(lng, lat)], 3, 5, function (data) {
                            var label = new BMap.Label(rec.get('name'), {offset: new BMap.Size(20, -10)});
                            var marker = new BMap.Marker(data.points[0]);
                            marker.setIcon(me.mQi);
                            me.bmap.addOverlay(marker);
                            marker.setLabel(label);
                            var markerMenu = new BMap.ContextMenu();
                            markerMenu.addItem(new BMap.MenuItem('指派',
                                function () {
                                    me.asignToRider(riderId);
                                }.bind(me)));
                            marker.addContextMenu(markerMenu);
                        });

                    })
                }
            }

        });
        // Ext.create('Kits.store.Order', {
        //     listeners: {
        //         load: function (store, records, successful, operation, eOpts) {
        //             if (!successful)return;
        //             console.log(records)
        //             Ext.each(records, function (rec) {
        //                 var mFrom = new BMap.Icon('/marker/from.png', new BMap.Size(16, 32));
        //                 var mTo = new BMap.Icon('/marker/to.png', new BMap.Size(16, 32));
        //                 var orderLng = parseFloat(rec.get('orderLng')) / 1000000;
        //                 var orderLat = parseFloat(rec.get('orderLat')) / 1000000;
        //                 var convertor = new BMap.Convertor();
        //                 convertor.translate([new BMap.Point(orderLng, orderLat)], 3, 5, function (data) {
        //                     console.log(data)
        //                     var label = new BMap.Label(rec.get('userAddress'), {offset: new BMap.Size(20, -10)});
        //                     var marker = new BMap.Marker(data.points[0]);
        //                     marker.setIcon(mTo);
        //                     me.bmap.addOverlay(marker);
        //                     marker.setLabel(label);
        //                     var markerMenu = new BMap.ContextMenu();
        //                     markerMenu.addItem(new BMap.MenuItem('指定骑手', me.asignToRider.bind(marker)));
        //                     marker.addContextMenu(markerMenu);
        //                 });
        //
        //             })
        //         }
        //     }
        //
        // });
    }




});