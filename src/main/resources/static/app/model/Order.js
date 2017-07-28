Ext.define('Kits.model.Order', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'time', type: 'string'},
        {name: 'description', type: 'string'},
        {name: 'stime', type: 'string',},
        {name: 'state', type: 'string'},
        {name: 'userName', type: 'string'},
        {name: 'userPhone', type: 'string'},
        {name: 'userAddress', type: 'string'},
        {name: 'remark', type: 'string'},
        {name: 'shopLng', type: 'string'},
        {name: 'shopLat', type: 'string'},
        {name: 'orderLng', type: 'string'},
        {name: 'orderLat', type: 'string'},
    ]
})