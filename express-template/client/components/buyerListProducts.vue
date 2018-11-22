<!-- buyerListProducts.vue -->
<!-- This is a Vue component with some AJAX magic.
In the JS Code, we have defined a camel array (products) that we populate using the function
getProducts(), using an AXIOS get request. Furthermore, we provide a delete function that can
delete camels using a DELETE request (again using AXIOS).

In the template tag, the only interesting part is the div with the v-for attribute.
Here, Vue creates a div per camel in our camels array. Each div has an image and
another div containing some basic information on the Camel (its color and position).-->

<template>
<div>
    <div class="text-center">
        
        <h3>All Products</h3>
        <p>All Products displayed</p>   
        <div class="container">
            <div class="row">
                <div v-for="product in products" v-bind:key="product._id">
                    <div class="col-sm-4 p-3 m-3">
                        <div class="card card-body">
                            <div class="card-title d-flex justify-content-center"><img src="laptop.png" alt="Product"> </div>
                            <p><b>Color: {{product.color}}.</b></p>
                            <p><b>Model: {{product.model}}.</b></p>
                            <p><b>Brand: {{product.brand}}.</b></p>
                            <p><b>Price: {{product.price}}.</b></p>
                            <p><b>OS:    {{product.OS}}.</b></p>
                            <p><b>Category: {{getCategoryName(product.categoryId)}}.</b></p>
                             <button type="button" id="btn-add" class="btn btn-success glyphicon glyphicon-shopping-cart " @click="patchProductToCart(product._id)"  style="float: bottom;">  Add to Cart</button>
                            <p></p>
                           <br>
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
    


</div>
</template>


<script>


var axios = require('axios');

module.exports = {
  name:"AllProducts",
  props: ['categoryId'],
  data () {
    return {
      products: [],
      productsInCart: [],
      newArray: [],
      cartID: '',
      modalOpen: false,
      // When you are editing then it is set to true to make a difference when creating a new one.
      isEditing: false,
      selectedProduct: {},
      categories: []
    }
  },
  methods: {
      //Performs a GET request to /api/products using AXIOS.
        getProducts: function () {
           console.log("Category is: " + this.categoryId)
            console.log("Client getting products")
            axios.get('/api/products?categoryId=' + this.categoryId)
            .then(
                response => {
                    console.log("Result received: " + JSON.stringify(response))
                    //This is only an example of how you can access the status code
                    if (response.status!==200) {
                        console.log("Wrong status code: " + response.status);
                    }
                    //If we get a response, empty the products array and fill it with all products from our endpoints
                    this.products = [];
                    for (var i = 0; i < response.data.data.length; i++) {
                        this.products.push(response.data.data[i]);
                    }
            })
            .catch(error => {
                //In case of error, empty the products array.
                this.products.length = 0;
                console.log(error);
            })
            .then(function () {
                //This code is always executed, independent of the request being successful or not.
            });

        },
        getCategories :function () {
        console.log("Client getting categories")
            axios.get('/api/categories')
            .then(
                response => {
                    console.log("Result received: " + JSON.stringify(response))
                    //This is only an example of how you can access the status code
                    if (response.status!==200) {
                        console.log("Wrong status code: " + response.status);
                    }
                    //If we get a response, empty the products array and fill it with all products from our endpoints
                    this.categories = [];
                    for (var i = 0; i < response.data.data.length; i++) {
                        this.categories.push(response.data.data[i]);
                    }
            })
            .catch(error => {
                //In case of error, empty the products array.
                this.categories.length = 0;
                console.log(error);
            })
            .then(function () {
                //This code is always executed, independent of the request being successful or not.
            });

        },


        getCategoryName : function(categoryId) {
          
          for(var i=0; i<this.categories.length;i++) {
              console.log("comparing: " + categoryId + " with " + this.categories[i]._id);
            if(categoryId === this.categories[i]._id) {
                
                return this.categories[i].name;
            }
          } 
          return "Unknown";
      },



      patchProductToCart: function(productID) {
      console.log("Products " + this.products);
      this.productsInCart.push(productID);
      //window.alert("Added new product to cart with ID: " + productID);
      console.log("Added new product to cart with ID: " + productID);
      console.log("productsInCart line 103 " + this.productsInCart);
      axios
        .patch("/api/shoppingcarts/" + this.cartID, {
          productList: this.productsInCart
        })
        .then(response => {
          console.log("Success: " + response.status);
        })
        .catch(error => {
          console.log(error);
        })
        .then(function() {});
    },


     getShoppingCart: function() {
      axios
        .get("/api/shoppingcarts")
        .then(response => {
        
          //This is only an example of how you can access the status code
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //If we get a response, empty the shoppingcarts array and fill it with all shoppingcarts from our endpoints
          this.productsInCart.length = 0;
          console.log(
            "Number of Shopping cart(s): " + response.data.data.length
          );
          console.log(
            "Number of elements in the first cart " +
              response.data.data[0].productList.length
          );
          if (response.data.data.length > 0) {
                this.cartID = response.data.data[0]._id;
                this.productsInCart.push.apply(this.productsInCart,response.data.data[0].productList
            );
          }
        })
        .catch(error => {
          //In case of error, empty the shoppingcarts array.
          this.productsInCart.length = 0;
          console.log(error);
        })
        .then(function() {
          //This code is always executed, independent of the request being successful or not.
        });
    },


      },


        closeModal: function() {
            this.modalOpen = false;
        },




    

  
    //This basically means that once Vue is ready, we call getProducts() to fetch
    //all products and populate our DOM tree with them.
    mounted () {
        this.getShoppingCart();
        this.getProducts();
        this.getCategories();
    }
}




</script>

<!-- Where the CSS code goes-->
<style scoped>
.row {
    border: solid 1px #404040;
    padding: 10px;
}

.card-title {
    padding-bottom: 10%;
}

.card {
    margin-bottom: 10%;
}
</style>
