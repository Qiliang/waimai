Ext.define('Kits.model.Shop', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'meituanId', type: 'string'},
        {name: 'name', type: 'string',},
        {name: 'address', type: 'string'},
        {name: 'loginName', type: 'string'},
        {name: 'loginPassword', type: 'string'},
    ]
})