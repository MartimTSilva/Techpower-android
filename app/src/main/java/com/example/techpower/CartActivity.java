package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpower.adapters.CartListAdapter;
import com.example.techpower.adapters.ProductListAdapter;
import com.example.techpower.models.CartItem;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private ArrayList<CartItem> cart;
    private ListView lv_Products;
    private TextView tv_total;
    private Button btn_checkout;
    private CartListAdapter mCartListAdapter;
    private float total;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle(R.string.Cart_title);

        lv_Products = findViewById(R.id.listCartProducts);
        tv_total = findViewById(R.id.textView_Total);
        btn_checkout = findViewById(R.id.button_Checkout);
        cart = SingletonStore.getInstance(this).getCart();

        mCartListAdapter = new CartListAdapter(this, cart);
        lv_Products.setAdapter(mCartListAdapter);

        //Thread to update Total
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(200);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                total = SingletonStore.getInstance(getApplicationContext()).getCartTotal();
                                tv_total.setText(total + "€");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();

        //Checks if there are any items on cart to be able to checkout
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total != 0) {
                    btn_checkout.setEnabled(true);
                    Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                    startActivity(intent);
                } else {
                    btn_checkout.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Cannot checkout without items in Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
