package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.techpower.adapters.CartListAdapter;
import com.example.techpower.adapters.ProductListAdapter;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private Map<Integer, Integer> cart;
    private ListView lv_Products;
    private TextView tv_total;
    private Button btn_checkout;
    private CartListAdapter mCartListAdapter;

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

        mCartListAdapter = new CartListAdapter(this, SingletonStore.getInstance(this).getCart());
        lv_Products.setAdapter(mCartListAdapter);
    }
}
