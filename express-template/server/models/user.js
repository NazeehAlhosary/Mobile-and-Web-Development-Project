var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var usersModel = new Schema({
    name: { type: String },
    email: { type: String },
    phone: { type: String },
    address: { type: String }
    
});

module.exports = mongoose.model('users', usersModel);

//{"name":"Nazeeh", "email":"gusalhmn","phone":"073600xxxx","address":"any address"}