Ext.define('Kits.model.Order', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'time', type: 'date', dateFormat: 'time'},
        {name: 'shopId', type: 'string'},
        {name: 'shopPhone', type: 'string'},
        {name: 'shopName', type: 'string'},
        {name: 'shopAddress', type: 'string'},
        {name: 'shopLng', type: 'string'},
        {name: 'shopLat', type: 'string'},
        'recipientName',
        'recipientPhone',
        'recipientAddress',
        'recipientLng',
        'recipientLat',
        {name: 'shippingFee', type: 'float'},
        {name: 'total', type: 'float'},
        'riderId',
        'riderName',
        'riderPhone',
        {name: 'status', type: 'int'},
        {name: 'riderAssignTime', type: 'date', dateFormat: 'time'},
        {name: 'riderReadTime', type: 'date', dateFormat: 'time'},
        {name: 'riderGetGoodsTime', type: 'date', dateFormat: 'time'},
        'riderGetGoodsLng',
        'riderGetGoodsLat',
        {name: 'riderCompleteTime', type: 'date', dateFormat: 'time'},
        'riderCompleteLng',
        'riderCompleteLat',
        {name: 'riderCoast', type: 'float'},
        {
            name: 'riderName', type: 'string', persist: false,
            mapping: function (data) {
                return data.rider ? +data.rider.phone + ':' + data.rider.name : null;
            }
        },
        {name: 'riders',persist :false},
    ]
})
