<!-- ownerHome.vue -->
<!-- This is a really basic (static) Vue component.
We just demonstrate how data (variables in the Vue instance) can be bound to HTML elements -->
<template>
  <div>
    <h1>{{title}}</h1>
    <p>{{description}}</p>

   <hr>
   
   
     <div v-for="product in products" v-bind:key="product._id" class="media border p-3 mt-3 mb-3">
           <h4>Current product: {{ getProductModel(product) }}  </h4>
           <h4>Current product Price: {{ getProductPrice(product) }} SEK</h4>
           <small><i>Current product OS: {{ getProductOS(product) }}</i></small>
        
        <br>          
          <hr>
           <button type="button" class="btn btn-danger glyphicon glyphicon-trash" @click="deleteShoppingCart(product)"> Remove</button>
        <br>
            <div class="media-body">     
              
            </div>
        </div>
        <div>

          <!-- <h3>Total: {{totalPrices}}</h3> -->
           <a href="#/Thanks"> <button type="button" id="btn-add" class="btn btn-success btn-lg" @click="patchProductToCart(product._id)"  style="float: right;"> Check out</button> </a> 
              <br>
              <br>
              <br>
        </div>
  </div>
  

  
</template>

<script>
var axios = require("axios");
module.exports = {
  name: "MyCart",
  props: ['categoryId'],
  data() {
    return {
      products: [],
      productsInArray:[],
       productsInCart: [],
      productsInCartAfterDelete: [],
      newID: '',
      cartID: '',
      findElement:0,
     // totalPrices:0,
      title: "Your Shopping Cart",
      description:
        "This is the buyer's SPA. You can look at all shoppingcarts, and remove them if you want."
    };
  },
  methods: {

     getShoppingCart: function() {
      console.log("Client getting shoppingcart Id");
      axios
        .get("/api/shoppingcarts")
        .then(response => {
          //This is only an example of how you can access the status code
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //If we get a response, empty the shoppingcarts array and fill it with all shoppingcarts from our endpoints
          this.products.length = 0;
          console.log("Shopping cart elements: " + response.data.data.length);
          if (response.data.data.length > 0) {
            this.cartID = response.data.data[0]._id;
            this.products.push.apply(this.products,response.data.data[0].productList);
          }
        })
        .catch(error => {
          //In case of error, empty the shoppingcarts array.
          this.products.length = 0;
          console.log(error);
        })
        .then(function() {
          //This code is always executed, independent of the request being successful or not.
        });
    },
      
    getProductModel : function(productID) {
         //window.alert("Get Product Model");
        for(var i=0; i<this.productsInArray.length;i++) {
          
          console.log("Checking id: " + productID + " with id" + this.productsInArray[i]._id + " from the new array");
          if(productID === this.productsInArray[i]._id) {
              return this.productsInArray[i].model + " with brand: " +this.productsInArray[i].brand ;
          }
        } 
        return  "undefined" ;
    },

      getProductOS : function(productID) {
        for(var i=0; i<this.productsInArray.length;i++) {
            console.log("Checking id: " + productID + " with id" + this.productsInArray[i]._id + " from the new array");
          if(productID === this.productsInArray[i]._id) {
              return this.productsInArray[i].OS;
          }
        } 
        return  "undefined" ;
    },

    getProductPrice : function(productID) {
        for(var i=0; i<this.productsInArray.length;i++) {
            console.log("Checking id: " + productID + " with id" + this.productsInArray[i]._id + " from the new array");
          if(productID === this.productsInArray[i]._id) {
               console.log("FIND IT " + this.productsInArray[i].price);
              // this.totalPrices += this.productsInArray[i].price;
              return this.productsInArray[i].price ;
          }
        } 
        return  "undefined" ;
    },
    
    getProductsWithID: function () {
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
                     this.productsInArray = []; 
                     for (var i = 0; i < response.data.data.length; i++) {
                     this.productsInArray.push(response.data.data[i]);
                     console.log("  PUSHED TO productsInArray " + i);
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

     patchProductToCart: function(productID) {
      this.productsInCart.push(productID);
      //window.alert("Added new product to cart with ID: " + productID);
      console.log("Added new product to cart with ID: " + productID);
      console.log("productsInCart line 103 " + this.productsInCart);
      axios.patch("/api/shoppingcarts/" + this.cartID, {
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

    deleteShoppingCart:function(productID){
       for (let i = 0; i < this.products.length; i++) {
        console.log("Delete Item for " + this.products[i] + " Check with " + productID);
         if(this.products[i] !== productID ){
 
           this.productsInCart.push(this.products[i]);
         }else if (this.products[i] === productID && this.findElement!==0){
           window.alert("Else if ");
           this.productsInCart.push(this.products[i]);
           this.findElement+=1;
           //  window.alert("FIND AND DELETE");
            console.log("Delete The Item by do not put it in the productsInCart array " );
         }
         else{
           this.findElement+=1;
         }
       }
        axios.patch("/api/shoppingcarts/" + this.cartID, {
         "productList": this.productsInCart
        })
        .then(response => {
          console.log("Deleting DONE");
          this.getProductsWithID();
          this.getShoppingCart();
          //this.getTotalPrices();
          window.alert("Product has been deleted, Please refresh the page")
          console.log("Success: " + response.status);
        })
        .catch(error => {
          console.log(error);
        })
        .then(function() {
         
        });
    }
    //Performs a PATCH request to the shopping cart with the provided shopping cart ID.
},
    mounted() {
      this.getProductsWithID();
      this.getShoppingCart();
      this.findElement=0;
     // this.getTotalPrices();
     
    }
  
};
</script>