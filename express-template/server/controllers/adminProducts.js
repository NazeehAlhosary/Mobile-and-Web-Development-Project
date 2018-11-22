var express = require('express');
var router = express.Router();
var Product = require('../models/product');
var Shoppingcart = require('../models/shoppingcart');

//get by id
router.get('/:id', function(req,res,next) {

    var id = req.params.id;
    Product.findById({_id: id}, function(err,Product){
        if(err) { return next (err); }
        if(Product == null){
            return res.status(404).json(
             {"message": "products not found."});
    
        }
        res.json(Product);
    });
});

// get all
router.get('/', function(req,res,next){
    console.log("Listing products");
     let categoryId = req.query.categoryId;
     console.log("showing category!!!!" + categoryId);
     if(req.query.categoryId != 'undefined') {
         Product.find({categoryId: categoryId}, function(err, products) {
             if (err) { return next(err); }
             res.status(201).json({"data": products}); 
             console.log("Returning: " + JSON.stringify(products))
             });
     }
     else {
     Product.find(function(err, products) {
         if (err) { return next(err); }
         res.status(201).json({"data": products}); 
         console.log("Returning: " + JSON.stringify(products))
         });
     }
});

// create product
router.post('/', function(req,res,next){

    var p = new Product(req.body);
    p.save(function(err) {
        if (err) { return next(err); }
        res.status(201).json(p);
    });
});

// Delete products
router.delete('/:id', function(req,res,next) {
    var id = req.params.id;
// NEEDS TESTING!!
    Shoppingcart.find({productList: {"$in": [id]}, function(err, shoppingcarts){
        if(shoppingcarts.length > 0) {
            return res.status(406).json({"message": "Can't delete product because shoppingcart contains it!"});
        }
     }
    });

    Product.findOneAndRemove({_id: id}, function(err, Product ){
        if (err) { return next(err) ; }
        if (Product == null) {
            return res.status(404).json({"message": "products not found"});
        }
    res.json(Product);
    });
});

router.delete('/', function(req,res,next) {
    Product.remove( function(err, Product ){
        if (err) { return next(err);
        }
    res.json(Product);
    });
});


// Update products
router.patch('/:id', function(req, res, next) {
    
    Product.findOneAndUpdate({_id: req.params.id}, req.body, function(err, product) {
        if (err) { return next(err); }
        if (product == null) {
            return res.status(404).json({"message": "product not found"});
        }
        res.json(req.body);
    });
});
    
module.exports = router