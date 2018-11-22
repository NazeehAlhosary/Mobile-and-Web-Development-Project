package se.chalmers.cse.wm1819.dit341template;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainAddCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_category);
        getSupportActionBar().setTitle("Add new Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategory(view);
            }
        });
    }

    public void addNewCategory(View view) {
        String url = getString(R.string.server_urlCategory);
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject params = new JSONObject();
        try {
            final EditText category_name = findViewById(R.id.editText);
            if(!category_name.getText().toString().isEmpty()){
                params.put("name", category_name.getText().toString());
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Category has been added", Toast.LENGTH_SHORT).show();
                         //pDialog.hide();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(), error + "", Toast.LENGTH_LONG).show();
                        //pDialog.hide();
                    }
                });

// Adding request to request queue
                queue.add(jsonObjReq);
            }else{
                Toast.makeText(this,  "Please type the category name ", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
