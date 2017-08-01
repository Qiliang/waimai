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
        var center = this.center;
        this.callParent(arguments);
        // 百度地图API功能
        this.bmap = new BMap.Map('allmap');    // 创建Map实例
        this.bmap.centerAndZoom('宜昌', 15);  // 初始化地图,设置中心点坐标和地图级别
        this.bmap.addControl(new BMap.MapTypeControl());   //添加地图类型控件
        this.bmap.setCurrentCity("宜昌");          // 设置地图显示的城市 此项是必须设置的
        this.bmap.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放


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
                            var markerMenu = new BMap.ContextMenu();
                            markerMenu.addItem(new BMap.MenuItem('删除', me.asignToRider.bind(marker)));
                            marker.addContextMenu(markerMenu);
                        });

                    })
                }
            }

        });
    },

    asignToRider: function () {

    }


});