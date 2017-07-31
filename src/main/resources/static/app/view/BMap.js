Ext.define('Kits.view.BMap', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.bmap',
    requires: ['Ext.window.MessageBox'],
    // height: 250,
    html: '<div style="height: 900px" id="allmap"></div>',

    initComponent : function(){
        Ext.applyIf(this,{
            plain: true,
            gmapType: 'map',
            border: false
        });

        this.callParent(arguments);
    },

    onBoxReady: function (width, height) {
        //this.html = '<div style="height: ' + height + 'px" id="allmap"></div>'
        var center = this.center;
        this.callParent(arguments);


        // 百度地图API功能
        this.bmap = new BMap.Map('allmap');    // 创建Map实例
        this.bmap.centerAndZoom('宜昌');  // 初始化地图,设置中心点坐标和地图级别
        this.bmap.addControl(new BMap.MapTypeControl());   //添加地图类型控件
        this.bmap.setCurrentCity("宜昌");          // 设置地图显示的城市 此项是必须设置的
        this.bmap.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
        //
        // if (center) {
        //     if (center.geoCodeAddr) {
        //         this.lookupCode(center.geoCodeAddr, center.marker);
        //     } else {
        //         this.createMap(center);
        //     }
        // } else {
        //     Ext.raise('center is required');
        // }

    },

    createMap: function(center, marker) {
        var options = Ext.apply({}, this.mapOptions);

        options = Ext.applyIf(options, {
            zoom: 14,
            center: center,
            mapTypeId: google.maps.MapTypeId.HYBRID
        });
        this.gmap = new google.maps.Map(this.body.dom, options);
        if (marker) {
            this.addMarker(Ext.applyIf(marker, {
                position: center
            }));
        }

        Ext.each(this.markers, this.addMarker, this);
        this.fireEvent('mapready', this, this.gmap);
    },

    addMarker: function(marker) {
        marker = Ext.apply({
            map: this.gmap
        }, marker);

        if (!marker.position) {
            marker.position = new google.maps.LatLng(marker.lat, marker.lng);
        }
        var o =  new google.maps.Marker(marker);
        Ext.Object.each(marker.listeners, function(name, fn){
            google.maps.event.addListener(o, name, fn);//测试111
        });
        return o;
    },

    lookupCode : function(addr, marker) {
        this.geocoder = new google.maps.Geocoder();
        this.geocoder.geocode({
            address: addr
        }, Ext.Function.bind(this.onLookupComplete, this, [marker], true));
    },

    onResize: function (width, height, oldWidth, oldHeight) {
        console.log('onResize')
    },

    onLookupComplete: function(data, response, marker){
        if (response != 'OK') {
            Ext.MessageBox.alert('Error', 'An error occured: "' + response + '"');
            return;
        }
        this.createMap(data[0].geometry.location, marker);
    },

    afterRender: function () {
        this.callParent(arguments);
        var me = this;
        Ext.create('Kits.store.Order', {
            listeners: {
                load: function (store, records, successful, operation, eOpts) {
                    if (!successful)return;
                    console.log(records)
                    Ext.each(records, function (rec) {
                        var mFrom = new BMap.Icon('/marker/from.png', new BMap.Size(16, 32));
                        var mTo = new BMap.Icon('/marker/to.png', new BMap.Size(16, 32));
                        var myGeo = new BMap.Geocoder();
                        var orderLng = parseFloat(rec.get('orderLng')) / 1000000;
                        var orderLat = parseFloat(rec.get('orderLat')) / 1000000;
                        var convertor = new BMap.Convertor();
                        convertor.translate([new BMap.Point(orderLng, orderLat)], 3, 5, function (data) {
                            console.log(data)
                            var label = new BMap.Label(rec.get('userAddress'), {offset: new BMap.Size(20, -10)});
                            var marker = new BMap.Marker(data.points[0]);
                            marker.setIcon(mTo);
                            me.bmap.addOverlay(marker);
                            marker.setLabel(label);
                        });

                        // myGeo.getPoint(rec.get('userAddress'), function (point, address, addressComponents, surroundingPois, business) {
                        //     if (point) {
                        //         var label = new BMap.Label(rec.get('userAddress'), {offset: new BMap.Size(20, -10)});
                        //         var marker = new BMap.Marker(point);
                        //         marker.setIcon(mTo);
                        //         me.bmap.addOverlay(marker);
                        //         marker.setLabel(label);
                        //     } else {
                        //         console.log("您选择地址没有解析到结果!");
                        //         console.log(rec.get('userAddress'));
                        //         //alert("您选择地址没有解析到结果!");
                        //     }
                        // }, "宜昌");
                        //
                        // myGeo.getPoint(rec.get('shopAddress'), function (point, address, addressComponents, surroundingPois, business) {
                        //     if (point) {
                        //         var label = new BMap.Label(rec.get('shopName'), {offset: new BMap.Size(20, -10)});
                        //         var marker = new BMap.Marker(point);
                        //         marker.setIcon(mFrom);
                        //         me.bmap.addOverlay(marker);
                        //         marker.setLabel(label);
                        //     } else {
                        //         console.log("您选择地址没有解析到结果!");
                        //         console.log(rec.get('shopAddress'));
                        //     }
                        // }, "宜昌");


                    })
                }
            }

        });
    },

    afterComponentLayout : function(w, h){
        this.callParent(arguments);


        //this.redraw();
    },

    redraw: function(){
        var map = this.bmap;
        if (map) {
            map.reset();
        }
    },
    onDockedAdd: function (component) {
        this.callParent(arguments);
        console.log('onDockedAdd');
    },
    onShow: function () {
        this.callParent(arguments);
        console.log('onShow');
    },
    onDestroy: function () {
        this.callParent(arguments);
        console.log('onDestroy');
    }

});