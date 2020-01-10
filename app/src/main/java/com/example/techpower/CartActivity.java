package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.techpower.adapters.ProductListAdapter;
import com.example.techpower.models.Product;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private Product products;
    private ListView lv_Products;
    private TextView tv_total;
    private Button btn_checkout;
    private ProductListAdapter mProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv_Products = findViewById(R.id.listCartProducts);
        tv_total = findViewById(R.id.textView_Total);
        btn_checkout = findViewById(R.id.button_Checkout);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(intent);
            }
        });


    }

    public void onRefreshProductList(ArrayList<Product> productArrayList) {
        mProductListAdapter = new ProductListAdapter(getApplicationContext(), productArrayList);
        lv_Products.setAdapter(mProductListAdapter);
    }
}
