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
        {name: 'shopName', type: 'string'},
        {name:'status',type:'int'},
        {name:'orders',persist :false},
        {
            name: 'displayName', type: 'string', persist: false,
            mapping: function (data) {
                return data.phone + ':' + data.name;
            }
        }
    ]

})