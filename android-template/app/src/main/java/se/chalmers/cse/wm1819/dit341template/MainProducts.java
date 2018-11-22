package se.chalmers.cse.wm1819.dit341template;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.cse.wm1819.dit341template.model.Category;
import se.chalmers.cse.wm1819.dit341template.model.Product;

public class MainProducts extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private myRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  String resultNameFinal = "";
    ArrayList<Category> categoryNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAllCategories();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getProducts();
    }

    public void getProducts() {
        String url = getString(R.string.server_GETproductsCategory);

        //This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;
                        String newVal = "";
                        String []categoryArray2 = {};
                        try {
                            dataArray = response.getString("data");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        ArrayList<Product> productsList = new ArrayList<>();

                        Product[] products = gson.fromJson(dataArray, Product[].class);
                        for (Product current : products) {
                            current.categoryId = getNameById(current.categoryId);
                            productsList.add(current);
                        }

                        mAdapter = new myRecyclerViewAdapter(productsList);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
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

    public String getNameById(String CategoryId) {
        for (int i = 0; i < categoryNames.size(); i++) {
            if (categoryNames.get(i)._id.equals(CategoryId)) {
                return categoryNames.get(i).name;
            }
        }
        return "Not Found";
    }



}
