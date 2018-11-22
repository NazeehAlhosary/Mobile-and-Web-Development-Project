var express = require('express');
var router = express.Router();
var Shoppingcart = require('../models/shoppingcart');

//Return all the shoppingcarts
router.get('/', function (req, res) {
    Shoppingcart.find(function(err, shoppingcarts) {
        if (err) { return next(err); }
        res.json({"data": shoppingcarts});
        });
});

//Get shoppingcart by Id
router.get('/:id', function(req, res, next) {
    Shoppingcart.findById({_id: req.params.id}, function(err, shoppingcart) {
    if (err) { return next(err); }
    if (shoppingcart == null) {
        return res.status(404).json(
            {"message": "Shoppingcart not found"});
    }
    res.json(shoppingcart);
    });
});
    


//create new shoppingcart
router.post('/', function(req, res,next) {
    var s = new Shoppingcart(req.body);
    s.save(function(err) {
        if(err) {
            return next(err); }

            res.status(201).json(s);
    });
 
});



//update shoppingcart
router.patch ('/:id', function(req,res,next) {
    var id = req.params.id;
    Shoppingcart.findByIdAndUpdate({_id: req.params.id}, req.body, function(err, shoppingcart) {
        if (err) { return next(err); }
        if (shoppingcart == null) {
                return res.status(404).json(
                    {"message": "Shoppingcart not found"});
        };
        res.json(req.body);
    });
});

//delete shoppingcart



router.delete('/:id',function(req,res,next) {
    var id = req.params.id;
    Shoppingcart.findOneAndRemove({_id: id}, function(err, Shoppingcart) {
        if (err) { return next(err); }
        if (Shoppingcart == null) {
            return res.status(404).json({"message": "Shoppingcart not found"});
        }
        res.json(Shoppingcart);
        });
    });

    


module.exports = router