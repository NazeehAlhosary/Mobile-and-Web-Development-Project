<!-- ownerListProducts.vue -->
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
        <div class="btn-group float-right mt-1">
            <button type="button" class="close" @click="addProduct()" > 
                <img src="../icons/add.png">
                <p style="font-size: 14px">Create Product</p>
            </button>
            <button type="button" class="close" style="padding-left: 5%" @click="addCategory()" >
                <img src="../icons/add.png">
                <p style="font-size: 14px">Add Category</p>
            </button>
            <button type="button" class="close" style="padding-left: 5%" @click="() => this.modalOpenCategoriesDelete = true" >
                <img src="../icons/rubbish-bin.png">
                <p style="font-size: 14px">Delete All Categories</p>
            </button>
        </div>
        <h3>Products</h3>       
        <p>See, create, change or delete from all products. Owner page</p>        
        <div clas="container">
        <div class="row">
            <!-- Here I get the items 4 by 4 and attempt to place them 4 on each line -->
            <!-- <div v-for="p in Math.ceil(products.length / 1)" >
                <div v-for="product in products.slice((p - 1) * 1, p * 1)" v-bind:key="product._id">-->
                <div v-for="product in products" v-bind:key="product._id" >
                    <div class="col-md p-3 m-3"> 
                            <div class="card card-body">
                                <div class="card-title btn-group d-flex justify-content-center">
                                    <button type="button" class="btn close" style="margin-right:15%" @click="editProduct(product._id)"> <img src="../icons/edit.png"></button>
                                    <button type="button" class="btn close" style="margin-right:10%" @click="deleteProduct(product._id)"><img src="../icons/rubbish-bin.png"></button>
                                </div>
                                    <p>Color: {{product.color}}.</p>
                                    <p>Model: {{product.model}}.</p>
                                    <p>Brand: {{product.brand}}.</p>
                                    <p>Price: {{product.price}}.</p>
                                    <p>OS: {{product.OS}}.</p>
                                    <p>Category: {{getCategoryName(product.categoryId)}}.</p>
                                </div>
                            </div>
                        </div>
                </div>
        </div>
     </div>

       <div v-show="modalOpen" class="modal-mask" tabindex="-1" role="dialog" >
           <div class="modal-dialog" role="document">
             <div class="modal-content">
                         <div class="modal-header">
                    <div v-if="isEditing">
                        <h4 class="modal-title">Choose what do you want to change</h4>
                    </div>
                    <div v-if="!isEditing">
                        <h4 class="modal-title">Add a new product</h4>
                    </div>
                    
                    <button type="button" class="close" data-dismiss="modal" @click="closeModal()">&times;</button>
                </div>
                <div class="modal-body">
                        <div class="container">
                        <form>
                            <div class="form-group">
                                <label for="color">Color:</label>
                            <input v-model="selectedProduct.color" type="text" class="form-control" id="color" name="color">
                            </div>
                            <div class="form-group">
                                <label for="model">Model:</label>
                            <input v-model="selectedProduct.model" type="text" class="form-control" id="model" name="model">
                            </div>
                            <div class="form-group">
                                <label for="brand">Brand:</label>
                            <input v-model="selectedProduct.brand" type="text" class="form-control" id="brand" name="brand">
                            </div>
                            <div class="form-group">
                                <label for="price">Price:</label>
                            <input v-model="selectedProduct.price" type="number" class="form-control" id="price" name="price">
                            </div>
                            <div class="form-group">
                                <label for="os">OS:</label>
                            <input v-model="selectedProduct.OS" type="text" class="form-control" id="os" name="os">
                            </div>
                            <div class="form-group">
                                 <select name="category" v-model="selectedProduct.categoryId">
                                    <option v-for="category in categories" v-bind:key="category._id" v-bind:value="category._id">{{category.name}}</option>
                                </select>
                            </div>
                            
                        </form>
                     </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" @click="submitProduct(selectedProduct._id, selectedProduct)" data-dismiss="modal">Save</button>
                </div>
            </div>
        </div>
    </div>

           <div v-show="modalOpenCategory" class="modal-mask" tabindex="-1" role="dialog" >
           <div class="modal-dialog" role="document">
             <div class="modal-content">
                <div class="modal-header">
                    <p>Add new Category</p>
                    <button type="button" class="close" data-dismiss="modal" @click="closeModal()">&times;</button>
                </div>
                <div class="modal-body">
                        <div class="container">
                        <form>
                            <div class="form-group">
                                <label for="name">Name:</label>
                            <input v-model="category_name" type="text" class="form-control" id="name" name="name">
                            </div>
                        </form>
                     </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" @click="postCategory(category_name)" data-dismiss="modal">Save</button>
                </div>
            </div>
        </div>
    </div>
    <div v-show="modalOpenCategoriesDelete" class="modal-mask" tabindex="-1" role="dialog" >
           <div class="modal-dialog" role="document">
             <div class="modal-content">
                <div class="modal-header">
                    <p>WARNING! you are about to delete all categories</p>
                </div>
                <div class="modal-body">
                        <div class="container">
                            <p>{{this.deleteMessage}}</p>
                        </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" @click="() => this.modalOpenCategoriesDelete = false" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" @click="deleteAllCategories()" data-dismiss="modal">Delete All Categories</button>
                </div>
            </div>
        </div>
    </div>
</div>

</template>

<script>

var axios = require('axios');

module.exports = {
  name:"ListProducts",
  props: ['categoryId'],
  data () {
    return {
      products: [],
      modalOpen: false,
      modalOpenCategory: false,
      modalOpenCategoriesDelete: false,
      // When you are editing then it is set to true to make a difference when creating a new one.
      isEditing: false,
      selectedProduct: {},
      categories: [],
      deleteMessage: ""
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

        //Performs a PATCH request to the product with the provided ID.
        patchProduct: function(productID, product) {
          console.log("Editing product with ID " + productID);
          axios.patch('/api/admin/products/'+productID, product)
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

        //Performs a POST request to the product with the provided ID.
        postProduct: function(product) {
          console.log("Adding product");
          axios.post('/api/admin/products/',product)
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


        //Performs a DELETE request to the product with the provided ID.
        deleteProduct: function (productID) {
              console.log("Deleting product with ID " + productID);
              axios.delete('/api/admin/products/'+productID)
              .then(
                  response => {
                      var localIndex = -1;
                      for (var i=0; i < this.products.length; i++) {
                          if (this.products[i]._id === productID) {
                              localIndex = i;
                              i = this.products.length;
                          }
                      }
                      if (localIndex !== -1) {
                          this.products.splice(localIndex, 1);
                      }
                      console.log("Success: " + response.status);
              })
              .catch(error => {
                  console.log(error);
              })
              .then(function () {
              });
        },
        editProduct: function(productID) {
            console.log("Clicked edit product for: " + productID)
            this.modalOpen = true;
            this.isEditing = true;
            this.selectedProduct = {}
            for (var i=0; i < this.products.length; i++) {
                          if (this.products[i]._id === productID) {
                              this.selectedProduct._id = this.products[i]._id;
                              this.selectedProduct.color = this.products[i].color;
                              this.selectedProduct.model = this.products[i].model;
                              this.selectedProduct.brand = this.products[i].brand;
                              this.selectedProduct.price = this.products[i].price;
                              this.selectedProduct.OS = this.products[i].OS;
                          }
            }
        },
        deleteAllCategories: function() {
            console.log("Deleting all categories")
            axios.delete('/api/admin/categories')
              .then(
                  response => {
                      this.getCategories();
                      console.log("Success: " + response.status);
                      this.deleteMessage = "";
              })
              .catch(error => {
                  console.log("Unable to delete categories")
                  this.deleteMessage = "You still have products that has a category.";
                  console.log(error);
              })
              .then(function () {
              });
        },
        closeModal: function() {
            this.modalOpen = false;
            this.modalOpenCategory = false;
            this.modalOpenCategoriesDelete = false;
            this.isEditing = false;
        },
        addProduct: function () {
            this.isEditing = false;
            this.modalOpen = true;
            this.selectedProduct={};
        },
        
        // when you click the save buttom
        submitProduct: function(productID,product) {
            if (this.isEditing) {
                this.patchProduct(productID, product);
            }
            else {
                this.postProduct(product);
            } 
        },

        getCategories :function () {
        console.log("Client getting categories")
            axios.get('/api/categories')
            .then(
                response => {
                    console.log("Result received: " + JSON.stringify(response.data))
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
        addCategory : function() {
            this.isEditing = false;
            this.modalOpenCategory = true;
            this.selectedProduct={};
        },
        postCategory : function(category_name) {
            console.log("Adding category: " + category_name);
          axios.post('/api/admin/categories/', {name: category_name})
                .then(
                  response => {
                      console.log("Success: " + response.status);
                      this.getCategories();
                      this.closeModal();
              })
              .catch(error => {
                  console.log(error);
              })
              .then(function () { 
                  
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
      }
      },

    //This basically means that once Vue is ready, we call getProducts() to fetch
    //all products and populate our DOM tree with them.
    mounted () {
        this.getProducts();
        this.getCategories();
    }
};

</script>

<!-- Where the CSS code goes-->
<style scoped>


.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: flex;
  transition: opacity .3s ease;
    max-height: 100%;
    overflow-y: auto;
}

.row {
    border: solid 1px #404040;
    padding: 10px;
}

.card-title {
    padding-bottom: 10%;
}


</style>
