Ext.define('Kits.model.Rider', {
    extend: 'Ext.data.Model',
    identifier: 'uuid',
    fields: [
        {name: 'id',  type: 'string'},
        {name: 'phone',  type: 'string'},
        {name: 'name',  type: 'string'},
        {name: 'loginName', type: 'string'},
        {name: 'loginPassword', type: 'string'},
        {name: 'lng', type: 'string'},
        {name: 'lat', type: 'string'},
        {name: 'lastModifyTime', type: 'date',dateFormat:'timestamp'},
    ]

})