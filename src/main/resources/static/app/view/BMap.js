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

    markerOrder: function (rec) {
        if (!rec)return;
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
            markerSong.addEventListener("mouseover", me.markerMouseover.bind(markerSong));
            markerSong.addEventListener("mouseout", me.markerMouseout.bind(markerSong));


            var markerQu = new BMap.Marker(data.points[1]);
            me.orderMarkers.push(markerQu);
            markerQu.setIcon(me.mQu);
            me.bmap.addOverlay(markerQu);
            markerQu.setLabel(new BMap.Label(rec.get('shopName'), {offset: new BMap.Size(20, -10)}));
            markerQu.addEventListener("mouseover", me.markerMouseover.bind(markerQu));
            markerQu.addEventListener("mouseout", me.markerMouseout.bind(markerQu));

            me.bmap.panTo(data.points[0]);
            var line = new BMap.Polyline(data.points);
            me.orderMarkers.push(line);
            line.setStrokeOpacity(0.5);
            line.setStrokeStyle("dashed");
            me.bmap.addOverlay(line);

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
                        lng += Ext.Number.randomInt(1, 10) / 1000.0;
                        lat += Ext.Number.randomInt(1, 10) / 1000.0;
                        var riderId = rec.get("id");
                        var displayName = rec.get("displayName");
                        if (isNaN(lng) || isNaN(lat))return;
                        var convertor = new BMap.Convertor();
                        convertor.translate([new BMap.Point(lng, lat)], 3, 5, function (data) {
                            var label = new BMap.Label(rec.get('name'), {offset: new BMap.Size(20, -10)});
                            var marker = new BMap.Marker(data.points[0]);
                            me.riderMarkers.push(marker);
                            marker.addEventListener("mouseover", me.markerMouseover.bind(marker));
                            marker.addEventListener("mouseout", me.markerMouseout.bind(marker));
                            marker.setIcon(me.mQi);
                            marker.setZIndex(10);
                            me.bmap.addOverlay(marker);
                            marker.setLabel(label);
                            var markerMenu = new BMap.ContextMenu();
                            markerMenu.addItem(new BMap.MenuItem('指派',
                                function () {
                                console.log(riderId)
                                    me.asignToRider(riderId,displayName);
                                }.bind(me)));
                            marker.addContextMenu(markerMenu);
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