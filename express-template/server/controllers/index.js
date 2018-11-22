var path = require('path');
var express = require('express');
var router = express.Router()
var mondgodb = require ('mongodb');


router.get('/api', function(req, res) {
    res.json({"message": "Welcome to your DIT341 backend project!"});
});

// Insert routes below
router.use('/api/products', require('./products'));
router.use('/api/shoppingcarts', require('./shoppingcarts'));
router.use('/api/users', require('./users'));
router.use('/api/categories', require('./categories'));

router.use('/api/admin/products', require('./adminProducts'));
router.use('/api/admin/categories', require('./adminCategories'));


// All other routes redirect to the index.html
router.route('/owner').get(function (req, res) {
    res.sendfile(req.app.get('appPath') + '/owner.html');
});

router.route('/buyer').get(function (req, res) {
    res.sendfile(req.app.get('appPath') + '/buyer.html');
});

router.route('/*').get(function (req, res) {
    var relativeAppPath = req.app.get('appPath');
    var absoluteAppPath = path.resolve(relativeAppPath);
    res.sendfile(absoluteAppPath + '/index.html');
});

module.exports = router
