package se.chalmers.cse.wm1819.dit341template;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;


public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    //Field for parameter name
    public static final String HTTP_PARAM = "httpResponse";
    //SparkButton sparkButton = findViewById(R.id.spark_Categories);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SparkButton sparkCategory = findViewById(R.id.spark_Categories);
        SparkButton sparkAddCategory = findViewById(R.id.spark_AddCategory);
        SparkButton sparkProducts = findViewById(R.id.spark_products);
        SparkButton sparkAddProducts = findViewById(R.id.spark_AddProduct);

        //sparkButton.setOnClickListener(this);

        sparkCategory.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                   // Toast.makeText(getApplicationContext(), "active", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(makeDelay_Category,600);
                    //onClickGoToCategory();
                } else {
                    // Button is inactive
                  //  Toast.makeText(getApplicationContext(), "inactive", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        sparkAddCategory.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                   // Toast.makeText(getApplicationContext(), "active", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(makeDelay_AddCategory,600);
                    //onClickGoToCategory();
                } else {
                    // Button is inactive
                   // Toast.makeText(getApplicationContext(), "inactive", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        sparkProducts.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                   // Toast.makeText(getApplicationContext(), "active", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(makeDelay_Product,600);
                    //onClickGoToCategory();
                } else {
                    // Button is inactive
                   // Toast.makeText(getApplicationContext(), "inactive", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        sparkAddProducts.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                   // Toast.makeText(getApplicationContext(), "active", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(makeDelay_AddProduct,600);
                    //onClickGoToCategory();
                } else {
                    // Button is inactive
                 //   Toast.makeText(getApplicationContext(), "inactive", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }

    private Runnable makeDelay_Category = new Runnable() {
        @Override
        public void run() {
            onClickGoToCategory();
        }
    };

    private Runnable makeDelay_AddCategory = new Runnable() {
        @Override
        public void run() {
            onClickGoToAddCategory();
        }
    };

    private Runnable makeDelay_Product = new Runnable() {
        @Override
        public void run() {
            onClickGoToProduct();
        }
    };

    private Runnable makeDelay_AddProduct = new Runnable() {
        @Override
        public void run() {
            onClickGoToAddProduct();
        }
    };

    public void onClickNewActivity (View view) {
       // TextView mCamelView = findViewById(R.id.catView);
        //Starts a new activity, providing the text from my HTTP text field as an input
        Intent intent = new Intent(this, SecondActivity.class);
       // intent.putExtra(HTTP_PARAM, mCamelView.getText().toString());
        Toast.makeText(getApplicationContext(), "onClickNewActivity", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onClickGoToCategory () {
        Intent intent = new Intent(this, MainCategory.class);
        Toast.makeText(getApplicationContext(), "Moving to see all categories", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    public void onClickGoToAddCategory () {
        Intent intent = new Intent(this, MainAddCategory.class);
        Toast.makeText(getApplicationContext(), "Moving to add new category", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    public void onClickGoToProduct () {
        Intent intent = new Intent(this, MainProducts.class);
        Toast.makeText(getApplicationContext(), "Moving to see all products", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    public void onClickGoToAddProduct () {
        Intent intent = new Intent(this, MainAddProduct.class);
        Toast.makeText(getApplicationContext(), "Moving to add new product", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }




}
