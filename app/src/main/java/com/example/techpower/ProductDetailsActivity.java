package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;

public class ProductDetailsActivity extends AppCompatActivity {

    private Product mProduct;
    private TextView tv_productName;
    private ImageView img_productImage;
    private TextView tv_productPrice;
    private Button btnAddToCart;
    private TextView tv_productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_productName = findViewById(R.id.textView_productName);
        img_productImage = findViewById(R.id.imageView_productImage);
        tv_productPrice = findViewById(R.id.textView_productPrice);
        btnAddToCart = findViewById(R.id.button_addToCart);
        tv_productDescription = findViewById(R.id.textView_productDetails);
        
        //Gets the product ID
        int id = getIntent().getIntExtra("ID", 0);

        //Gets the product with the ID
        mProduct = SingletonStore.getInstance(getApplicationContext()).getProduct(id);

        //Sets the text with all the product information
        tv_productName.setText(mProduct.getName());
        Glide.with(getApplicationContext())
                .load(mProduct.getImage())
                .placeholder(R.drawable.no_image)
                .thumbnail(0f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_productImage);
        tv_productPrice.setText(mProduct.getPrice() + "€");
        tv_productDescription.setText(mProduct.getDescription());

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sends the product ID to the cart
                SingletonStore.getInstance(getApplicationContext()).addProductCart(mProduct.getId(), 1);
                Toast.makeText(getApplicationContext(), R.string.add_cart, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
