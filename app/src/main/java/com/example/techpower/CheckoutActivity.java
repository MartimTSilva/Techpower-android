package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.techpower.adapters.CheckoutListAdapter;
import com.example.techpower.models.CartItem;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    private TextView tv_username;
    private TextView tv_address;
    private TextView tv_postalCode;
    private TextView tv_city;
    private TextView tv_country;
    private TextView tv_total;
    private Button btn_placeOrder;
    private String total;
    private CheckoutListAdapter mCheckoutListAdapter;
    private ListView lv_Products;
    private ArrayList<CartItem> cart;
    private String auth_key;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setTitle(R.string.checkout_title);

        tv_username = findViewById(R.id.textViewName);
        tv_address = findViewById(R.id.textViewAdress);
        tv_postalCode = findViewById(R.id.textViewPostalCode);
        tv_city = findViewById(R.id.textViewCity);
        tv_country = findViewById(R.id.textViewCountry);
        tv_total = findViewById(R.id.textView_Total);
        btn_placeOrder = findViewById(R.id.buttonPlaceOrder);
        lv_Products = findViewById(R.id.listViewCartItems);
        mContext = getApplicationContext();
        cart = SingletonStore.getInstance(this).getCart();

        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), mContext.MODE_PRIVATE);
        auth_key = preferences.getString("authkey", null);

        total = SingletonStore.getInstance(mContext).getCartTotal();
        tv_total.setText(total + "€");

        mCheckoutListAdapter = new CheckoutListAdapter(this, cart);
        lv_Products.setAdapter(mCheckoutListAdapter);


        showUserData(preferences);
    }

    protected void onResume() {
        super.onResume();
        lv_Products.setAdapter(mCheckoutListAdapter);
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), mContext.MODE_PRIVATE);
        auth_key = preferences.getString("authkey", null);
        //Checks if there is a user logged in and fills the activity information accordingly
        showUserData(preferences);
    }

    private void showUserData(final SharedPreferences preferences) {
        if (auth_key != null) {
            tv_username.setText(preferences.getString("firstName", null)+ " "+(preferences.getString("lastName", null)));
            tv_address.setText(preferences.getString("address", null));
            tv_postalCode.setText(preferences.getString("postal_code", null));
            tv_city.setText(preferences.getString("city", null));
            tv_country.setText(preferences.getString("country", null));
            btn_placeOrder.setText(R.string.btn_PlaceOrder);
            btn_placeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createSaleAPI(cart, mContext, auth_key);
                }
            });
        } else {
            tv_username.setText(R.string.checkout_no_login);
            btn_placeOrder.setText("Login");
            btn_placeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


    private void createSaleAPI(final ArrayList<CartItem> cart, final Context context, final String authentication_key) {
        JSONArray array = new JSONArray();
        try {
            for (CartItem item : cart) {
                JSONObject object = new JSONObject();
                object.put("id", item.getId());
                object.put("quantity", item.getQuantity());

                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("techpower", "createSaleAPI: " +  array.toString());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, SingletonStore.getInstance(this).getApiUrl() + "/api/sales?access-token=" + authentication_key, array,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Empty Cart and respond with success
                            cart.clear();
                            Log.d("techpower", "onResponse: " + response.toString());
                            Toast.makeText(context, R.string.sale_completed, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.update_error, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Purchase Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("Techpower", "Purchase Error: " + error.toString());
                    }
                }
        ) {
            // Convert jsonobject response to jsonarray
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                JSONArray responseArray = new JSONArray();
                if (response != null) {
                    try {
                        String s = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject object = new JSONObject(s);
                        (responseArray).put(object);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                        return super.parseNetworkResponse(response);
                    }
                }

                return Response.success(responseArray, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        SingletonStore.getInstance(this).addToRequestQueue(request);
    }
}
