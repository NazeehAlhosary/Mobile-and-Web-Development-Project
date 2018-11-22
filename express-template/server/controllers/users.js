var express = require('express');
var router = express.Router();
var User = require('../models/user');

// create new user, POST request
router.post('/', function(req,res,next){
    var u = new User(req.body);
    u.save(function(err) {
        if (err) { return next(err); }
        res.status(201).json(u);
    });
});


// GET all Users
router.get('/', function(req,res,next){
    User.find(function(err, User) {
        if (err) { return next(err); }
        res.json({"data": User});
    });
});


//GET user by id
router.get('/:id', function(req,res,next) {
    var id = req.params.id;
    User.findById({_id: id}, function(err,User){
        if(err) { return next (err); }
        if(User == null){
            return res.status(404).json(
             {"message": "User not found!"});
        }
        res.json(User);
    });
});


// Delete User by id
router.delete('/:id', function(req,res,next) {
    var id = req.params.id;
    User.findOneAndRemove({_id: id}, function(err, User ){
        if (err) { return next(err) ; }
        if (User == null) {
            return res.status(404).json({"message": "User not found!"});
        } 
    res.json(User);
    });
});
 
// Update User by id
 router.patch('/:id', function(req, res, next) {
    
    User.findOneAndUpdate({_id: req.params.id}, req.body, function(err, user) {
        if (err) { return next(err); }
        if (user == null) {
            return res.status(404).json({"message": "User not found!"});
        }
        res.json(req.body);
    });
});


module.exports = router
