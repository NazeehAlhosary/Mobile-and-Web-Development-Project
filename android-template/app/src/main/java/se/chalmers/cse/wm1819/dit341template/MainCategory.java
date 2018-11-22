package se.chalmers.cse.wm1819.dit341template;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.cse.wm1819.dit341template.model.Category;

public class MainCategory extends AppCompatActivity {

    private RecyclerView mCategoryRecyclerView;
    private CategoryRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getCategories();

        mCategoryRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        mCategoryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mCategoryRecyclerView.setLayoutManager(mLayoutManager);

        String url = getString(R.string.server_urlCategory);
        //This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String dataArray = null;

                        try{
                            dataArray = response.getString("data");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Category[] categories = gson.fromJson(dataArray, Category[].class);
                        ArrayList<Category> result = new ArrayList<>();
                        for (Category current : categories) {
                            result.add(current);
                        }
                        mAdapter = new CategoryRecyclerViewAdapter(result);
                        mCategoryRecyclerView.setAdapter(mAdapter);

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

    public Context context() {
        return this;
    }


}





