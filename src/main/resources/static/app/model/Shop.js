Ext.define('Kits.model.Shop', {
    extend: 'Ext.data.Model',
    identifier: 'uuid',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'name', type: 'string',},
        {name: 'address', type: 'string'},
        {name: 'meituanToken', type: 'string'},
        {name: 'shippingFee', type: 'float'}
    ]
})