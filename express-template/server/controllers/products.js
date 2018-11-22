var express = require('express');
var router = express.Router();
var Product = require('../models/product');


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
 


    
module.exports = router