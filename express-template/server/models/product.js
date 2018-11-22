var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var productSchema = new Schema({
    color: { type: String },
    model: { type: String },
    brand: { type: String },
    price: { type: Number },
    OS:    { type: String },
    categoryId: {type: Schema.Types.ObjectId, ref:'categories'}//relationship between categories and products => Every product has the ID of the category that it is in
    
});

module.exports = mongoose.model('products', productSchema);
//{"color":"orange", "model":"LG","brand":"2","price":"2340","OS":"Win"}
