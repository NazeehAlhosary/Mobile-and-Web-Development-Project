var express = require('express');
var router = express.Router();
var Category = require('../models/category');

// GET all Categories
router.get('/', function(req,res,next){
    Category.find(function(err, categories) {
        if (err) { return next(err); }
        res.json({"data": categories});
    });
});


//GET Category by id
router.get('/:id', function(req,res,next) {

    var id = req.params.id;
    Category.findById({_id: id}, function(err,Category){
        if(err) { return next (err); }
        if(Category == null){
            return res.status(404).json(
             {"message": "Category not found!"});
        }
        res.json(Category);
    });
});



module.exports = router