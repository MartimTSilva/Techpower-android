package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        tv_productName = findViewById(R.id.textView_productName);
        img_productImage = findViewById(R.id.imageView_productImage);
        tv_productPrice = findViewById(R.id.textView_productPrice);
        btnAddToCart = findViewById(R.id.button_addToCart);
        tv_productDescription = findViewById(R.id.textView_productDetails);
        
        int id = getIntent().getIntExtra("ID", 0);
        mProduct = SingletonStore.getInstance(getApplicationContext()).getProduct(id);

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
                //TODO: ADD TO CART
            }
        });
    }
}
