var mongoose = require('mongoose');
var Schema = mongoose.Schema;
//Connect to MongoDB
var db = mongoose.connect(
    'mongodb://localhost:27017/electronics',
    {useNewUrlParser: true}
);
//Define Mongoose shoppingcart model
var shoppingcartModel = new Schema({
    productList: { type : [{type: Schema.Types.ObjectId, ref: 'products'}] },
});


module.exports = mongoose.model('shoppingcarts', shoppingcartModel);