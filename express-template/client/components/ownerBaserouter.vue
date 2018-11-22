<!-- baserouter.vue -->
<template>
  <div>
    <!-- This is our header. Just a Jumbotron with some static info and a navigation bar -->

    <div class="jumbotron text-center w-75 mx-auto">
      <h1>Product Owner</h1>
      <nav class="navbar navbar-expand-sm bg-light navbar-light">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="#/">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/products">All Products</a>
          </li>
        <li v-for="category in categories" v-bind:key="category._id">
          <a class="nav-link" :href="'#/products?categoryId=' + category._id">{{category.name}}</a> 
        </li>
        </ul>
      </nav>
    </div>

    <!-- This is our footer -->
    <div class="row">
      <!-- The empty col-2 here are just used as a placeholder on the right and left -->
      <div class="col-sm-2">
      </div>

      <!-- Router view is where we will mount our dynamic content -->
      <div class="col-sm-8 text-center">
        <router-view :key="$route.fullPath">
        </router-view>
      </div>

      <div class="col-sm-2">
      </div>
    </div>

  <!-- This is our footer -->
  <div class="jumbotron text-center w-75 mx-auto " style="margin-bottom:0">
    <p>Created by Group 4, October 2018.</p>
  </div>
</div>
</template>

<script>

var axios = require('axios');
  module.exports = {
    name:"BaseRouter",
     data () {
    return {
      categories: []
      }
    },

    methods: {
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
      
      //Performs a DELETE request to the category with the provided ID.
        deleteCategory: function (categoryId) {
              console.log("Deleting product with ID " + categoryId);
              axios.delete('/api/admin/categories/'+categoryId)
              .then(
                  response => {
                      var localIndex = -1;
                      for (var i=0; i < this.categories.length; i++) {
                          if (this.categories[i]._id === categoryId) {
                              localIndex = i;
                              i = this.categories.length;
                          }
                      }
                      if (localIndex !== -1) {
                          this.categories.splice(localIndex, 1);
                      }
                      console.log("Success: " + response.status);
              })
              .catch(error => {
                  console.log(error);
              })
              .then(function () {
              });
        },

         //Performs a POST request to the category with the provided ID.
        postCategory: function(category) {
          console.log("Adding category");
          axios.post('/api/admin/categories/',category)
                .then(
                  response => {
                      console.log("Success: " + response.status);
                      this.getCategories();
                      //this.closeModal();
              })
              .catch(error => {
                  console.log(error);
              })
              .then(function () { 
                  
              });
        },
      
    },
     mounted () {
        this.getCategories();
    }

  };

</script>