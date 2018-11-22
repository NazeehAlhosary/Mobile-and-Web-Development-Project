<!-- ownerListshoppingcarts.vue -->
<!-- This is a Vue component with some AJAX magic.
In the JS Code, we have defined a Shoppingcart array (shoppingcarts) that we populate using the function
getshoppingcarts(), using an AXIOS get request. Furthermore, we provide a delete function that can
delete shoppingcarts using a DELETE request (again using AXIOS).

In the template tag, the only interesting part is the div with the v-for attribute. 
Here, Vue creates a div per Shoppingcart in our shoppingcarts array. Each div has an image and 
another div containing some basic information on the Shoppingcart (its color and position).-->
<template>
    <div class="text-center">
        <h3>shoppingcarts</h3>
        <p>Here we list all the shoppingcarts the (default) backend provides.</p>
        <div v-for="Shoppingcart in shoppingcarts" v-bind:key="Shoppingcart._id" class="media border p-3 mt-3 mb-3">
            <img src="" alt="Shoppingcart" class="mr-3 mt-3" style="width:60px;">
            <div class="media-body">
                <button type="button" class="close" @click="deleteShoppingcart(Shoppingcart._id)">&times;</button>
                <h4>{{Shoppingcart.color}} Shoppingcart <small><i>Good value!</i></small></h4>
                <p>This Shoppingcart is on position {{Shoppingcart.position}}.</p>      
            </div>
        </div>
    </div>
</template>

<script>
var axios = require('axios');

module.exports = {
  name:"Listshoppingcarts",
  data () {
    return {
      shoppingcarts: []
    }
  },
  methods: {
      //Performs a GET request to /api/shoppingcarts using AXIOS.
      postShoppingcart: function(shoppingcart) {
          console.log("Adding shoppingcart");
          axios.post('/api/shoppingcarts',shoppingcart)
                .then(
                  response => {
                      console.log("Success: " + response.status);
                      this.getShoppingcarts();
                      this.closeModal();
              })
              .catch(error => {
                  console.log(error);
              })
              .then(function () { 
                  
              });
        },

        patchShoppingcart: function(shoppingcartID, shoppingcart) {
          console.log("Editing product with ID " + shoppingcartID);
          axios.patch('/api/shoppingcarts'+shoppingcartID, shoppingcart)
                .then(
                  response => {
                      console.log("Success: " + response.status);
                      this.getProducts();
                      this.closeModal();
              })
              .catch(error => {
                  console.log(error);
              })
              .then(function () { 
                  
              });
        },
  

        getShoppingcarts: function () {
            axios.get('/api/shoppingcarts')
            .then(
                response => {
                    //This is only an example of how you can access the status code
                    if (response.status!==200) {
                        console.log("Wrong status code: " + response.status);
                    }
                    //If we get a response, empty the shoppingcarts array and fill it with all shoppingcarts from our endpoints
                    this.shoppingcarts.length = 0;
                    for (var i = 0; i < response.data.data.length; i++) {
                        this.shoppingcarts.push(response.data.data[i]);
                    }
            })
            .catch(error => {
                //In case of error, empty the shoppingcarts array.
                this.shoppingcarts.length = 0;
                console.log(error);
            })
            .then(function () {
                //This code is always executed, independent of the request being successful or not.
            });
        },
      
        //Performs a DELETE request to the Shoppingcart with the provided ID.
        deleteShoppingcart: function (ShoppingcartID) {
            console.log("Deleting Shoppingcart with ID " + ShoppingcartID);
            axios.delete('/api/shoppingcarts/'+ShoppingcartID)
            .then(
                response => {
                    //This code basically finds the Shoppingcart under question in the array and deletes it (using splice)
                    var localIndex = -1;
                    for (var i=0; i < this.shoppingcarts.length; i++) {
                        if (this.shoppingcarts[i]._id === ShoppingcartID) {
                            localIndex = i;
                            i = this.shoppingcarts.length;
                        }
                    }
                    if (localIndex !== -1) {
                        //Vue reacts to splice. So removing the element here causes the DOM to update.
                        this.shoppingcarts.splice(localIndex, 1); 
                    }
                    console.log("Success: " + response.status);
            })
            .catch(error => {
                console.log(error);
            })
            .then(function () {
            });

        }
    }, 
    //This basically means that once Vue is ready, we call getshoppingcarts() to fetch
    //all shoppingcarts and populate our DOM tree with them.
    mounted () {
        this.getShoppingcarts();
    }
};
</script>
