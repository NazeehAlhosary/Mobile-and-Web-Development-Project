<!-- baserouter.vue -->
<template>
  <div>
    <!-- This is our header. Just a Jumbotron with some static info and a navigation bar -->
 <div class="jumbotron">
  <div class="container text-center">
    <h1>Electronic Store</h1>      
    <p>Welcome to the best online marketplace!</p>
  </div>
</div>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                           
      </button>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="#/">Home</a></li>
        <li><a href="#/products"> All Products</a></li>        
        <li v-for="category in categories" v-bind:key="category._id">
          <a class="nav-link" :href="'#/products?categoryId=' + category._id">{{category.name}}</a> 
        </li>
        <li><a href="#footer" id="contact-us">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#/shoppingcarts"><span class="glyphicon glyphicon-shopping-cart"></span> Cart </a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container">    
  <div class="row">
    <div class="col-sm-4">
      <div class="panel panel-primary">
        <div class="panel-heading">Laptops</div>
        <div class="panel-body"><img src="analytics.png" class="img-responsive" style="width:50%" alt="Image"></div>
        
        <div class="panel-footer">Browse Laptops</div>
        
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-danger">
        <div class="panel-heading">Smartphones</div>
        <div class="panel-body"><img src="smartphone.png" class="img-responsive" style="width:50%" alt="Image"></div>
         
        <div class="panel-footer"> Browse Smartphones</div>
      
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-success">
        <div class="panel-heading">Desktop Computers</div>
        <div class="panel-body"><img src="workstation.png" class="img-responsive" style="width:50%" alt="Image"></div>
        
        <div class="panel-footer">Browse Desktop Computers</div>
         
      </div>
    </div>
  </div>
</div><br>




    <!-- This is our footer -->
    <div class="row">
      <!-- The empty col-2 here are just used as a placeholder on the right and left -->
      <div class="col-sm-1">
      </div>

      <!-- Router view is where we will mount our dynamic content -->
      <div class="col-sm-10 text-center">
         <router-view :key="$route.fullPath">
        </router-view>
      </div>

      <div class="col-sm-1">
      </div>
    </div>

  <!-- This is our footer -->
  <footer class="container-fluid text-center" id="#footer">
    <p>Contact information</p>  
    <form class="textarea">Email: Electronic@homtail.com
    
  </form>
  </footer>

</div>
</template>

<script>
var axios = require("axios");
module.exports = {
  name: "BaseRouter",
  data() {
    return {
      categories: [],
      products: [],
      cartID: ""
    };
  },

  methods: {
    postNewShoppingCart: function() {
      console.log("Adding New shoppingcarts");
      //window.alert("Adding New shoppingcarts ");
      axios
        .post("/api/shoppingcarts/", {
          productList: []
        })
        .then(response => {
          console.log("Success: " + response.status);
        })
        .catch(error => {
          console.log(error);
        })
        .then(function() {});
    },
    getStart: function() {
      console.log("Get Start");
      axios
        .get("/api/shoppingcarts")
        .then(response => {
          //This is only an example of how you can access the status code
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //If we get a response, empty the shoppingcarts array and fill it with all shoppingcarts from our endpoints
          if (response.data.data.length == 0) {
            this.postNewShoppingCart();
            console.log("Created new Cart");
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

    getShoppingCart: function() {
      axios
        .get("/api/shoppingcarts")
        .then(response => {
          //  window.alert(response.data.data.length);
          //This is only an example of how you can access the status code
          if (response.status !== 200) {
            console.log("Wrong status code: " + response.status);
          }
          //If we get a response, empty the shoppingcarts array and fill it with all shoppingcarts from our endpoints
          this.productsInCart.length = 0;
          if (response.data.data.length === 0) {
            this.postNewShoppingCart();
            this.numberOfCart += 1;

            this.mounted();
            this.cartID = response.data.data[0]._id;
            this.productsInCart.push.apply(
              this.productsInCart,
              response.data.data[0].productList
            );
          }

          if (response.data.data.length > 0) {
            this.cartID = response.data.data[0]._id;
            this.productsInCart.push.apply(
              this.productsInCart,
              response.data.data[0].productList
            );
            this.numberOfCart = response.data.data[0].productList.length;
            console.log(" Number of element  " + this.numberOfCart);
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

    getCategories: function() {
      console.log("Client getting categories");
      axios
        .get("/api/categories")
        .then(response => {
          console.log("Result received: " + JSON.stringify(response));
          //This is only an example of how you can access the status code
          if (response.status !== 200) {
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
        .then(function() {
          //This code is always executed, independent of the request being successful or not.
        });
    },
  

  getCategoryName: function(categoryId) {
    for (var i = 0; i < this.categories.length; i++) {
      console.log(
        "comparing: " + categoryId + " with " + this.categories[i]._id
      );
      if (categoryId === this.categories[i]._id) {
        return this.categories[i].name;
      }
    }
    return "Unknown";
  },
  },
  mounted() {
    this.getStart();
    this.getCategories();
  }
};

$("#contact-us").on("click", function() {
  const footer = $("#footer").position().top;

  $("html , body").animate(
    {
      scrollTop: footer
    },
    900 // 900 milliseconds to scroll
  );
});
</script>



<style scoped>
body {
  overflow-x: hidden;
}
</style>
