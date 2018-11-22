package se.chalmers.cse.wm1819.dit341template;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.chalmers.cse.wm1819.dit341template.model.Category;
import se.chalmers.cse.wm1819.dit341template.model.Product;

public class MainAddProduct extends AppCompatActivity {

    private Spinner spinner;
    private CategoryRecyclerViewAdapter mAdapterpublic;
    public String getSelectedItem;
    public String resultIDFinal = "";
    public String resultNameFinal = "";
    // private List<String> categoryNames = new ArrayList<String>();
    ArrayList<Category> categoryNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_product);
        getSupportActionBar().setTitle("Add new Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spinner = findViewById(R.id.spinner);
        getAllCategories();
        final Button addProductsButton = findViewById(R.id.addProductButton);
        addProductsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postProduct();
            }
        });
    }

    public void postProduct() {

        final TextView colorView = findViewById(R.id.colorText);
        final TextView modelView = findViewById(R.id.modelText);
        final TextView brandView = findViewById(R.id.brandText);
        final TextView priceView = findViewById(R.id.priceText);
        final TextView OSView = findViewById(R.id.OSText);

        final String color = colorView.getText().toString();
        final String model = modelView.getText().toString();
        final String brand = brandView.getText().toString();
        final String price = priceView.getText().toString();
        final String OS = OSView.getText().toString();
        getSelectedItem = spinner.getSelectedItem().toString();
        final String Category = getIdByName(getSelectedItem);
        if (spinner.getSelectedItem().toString().equals("Choose Category") && spinner.getSelectedItem().toString() != null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_LONG).show();
        }
        if (color.isEmpty() && model.isEmpty() && brand.isEmpty() && price.isEmpty() && OS.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields. ", Toast.LENGTH_LONG).show();
        } else {
            JSONObject product = new JSONObject();
            try {
                product.put("color", color);
                product.put("model", model);
                product.put("brand", brand);
                product.put("price", price);
                product.put("OS", OS);
                product.put("categoryId", Category);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String urlPost = getString(R.string.server_url) + "/api/admin/products";

            JsonObjectRequest productPost = new JsonObjectRequest(
                    Request.Method.POST, urlPost, product, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "Product has been added", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(productPost);

        }
    }

    public void getAllCategories() {
        String url = getString(R.string.server_urlCategory);
        //This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String dataArray = null;

                        try {
                            dataArray = response.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        List<String> categoriesSpinner = new ArrayList<String>();
                        categoriesSpinner.add(0, "Choose Category");
                        Category[] categories = gson.fromJson(dataArray, Category[].class);
                        ArrayList<Category> result = new ArrayList<>();
                        for (Category current : categories) {
                            result.add(current);
                            categoriesSpinner.add(current.name);
                            categoryNames.add(current);
                        }

                        ArrayAdapter<String> dataAdapter;
                        dataAdapter = new ArrayAdapter(MainAddProduct.this, android.R.layout.simple_spinner_item, categoriesSpinner);
                        spinner.setAdapter(dataAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Choose Category")) {

                                } else {
                                    String newItem = parent.getItemAtPosition(position).toString();

                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        //mAdapter = new CategoryRecyclerViewAdapter(result);
                        //mCategoryRecyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Text print error! ", error.toString());
                    }
                });

        //The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(request);
    }


    public String getIdByName(final String CategoryName) {

        String url = "http://10.0.2.2:3000/api/admin/categories";

        //This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String dataArray = null;
                        String resultID = "";
                        //final String CatName = CategoryName;
                        try {
                            dataArray = response.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Category[] categories = gson.fromJson(dataArray, Category[].class);
                        ArrayList<Category> result = new ArrayList<>();
                        for (Category current : categories) {
                            if (current.name.equals(CategoryName)) {
                                resultID = current._id;
                            }
                            result.add(current);
                        }
                        resultIDFinal = resultID;


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Text print error! ", error.toString());
                    }
                });

        //The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(request);
        return resultIDFinal;
    }



}
