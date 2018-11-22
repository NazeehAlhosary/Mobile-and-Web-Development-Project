var express = require('express');
var router = express.Router();
var Category = require('../models/category');
var Product = require ('..//models/product');


// create category, POST request
router.post('/', function(req,res,next){
    console.log("Saving category: " + req)
    var cate = new Category(req.body);
    console.log("Saving category: " + cate)
    cate.save(function(err) {
        if (err) { return next(err); }
        res.status(201).json(cate);
    });
});

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

// Delete all categories
router.delete('/', function(req,res,next) {
    Product.find({categoryId: { $ne: null }}, function(err, products){
        if(products.length > 0) {
            res.json({"message": "Cannot delete all categories beacuse product(s) has categories!"});
            return
        } else {
            Category.remove({}, function(err){
                if (err) { return next (err); }
                    res.json({data: "All categories deleted"});
            });
        }
    });
    
});

// Delete Category by id
router.delete('categories/:id', function(req,res,next) {
    var id = req.params.id;
    Product.find({categoryId: id}, function(err, products){
        if(products.length > 0) {
            return res.status(406).json({"message": "Cannot delete because there is a product with this category!"});
        }
    });

    Category.findOneAndRemove({_id: id}, function(err, Category){
        if (err) { return next (err); }
        if (Category == null) {
            return res.status(404).json({"message": "Category not found!"});
        } 
    res.json(Category);
    });
});

 
// Update Category by id
 router.patch('/:id', function(req, res, next) {
    
    Category.findOneAndUpdate({_id: req.params.id}, req.body, function(err, category) {
        if (err) { return next(err); }
        if (category == null) {
            return res.status(404).json({"message": "Category not found!"});
        }
    
        res.json(req.body);
    });
});


module.exports = router