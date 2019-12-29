package com.example.techpower.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.techpower.R;
import com.example.techpower.helpers.StoreDBHelper;
import com.example.techpower.listeners.ProductListener;
import com.example.techpower.utils.ProductJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingletonStore {

    private static SingletonStore instance = null;
    private static RequestQueue sVolleyQueue;

    private static String mApiUrl;

    private ArrayList<Product> mProductList;
    private StoreDBHelper mStoreDB;
    private ProductListener mProductListener;

    public static synchronized SingletonStore getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonStore(context);
        }
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_preferences), context.MODE_PRIVATE);
        mApiUrl = preferences.getString(context.getString(R.string.app_api), "");

        sVolleyQueue = Volley.newRequestQueue(context);

        return instance;
    }

    private SingletonStore(Context context) {
        mProductList = new ArrayList<>();
        mStoreDB = new StoreDBHelper(context);
    }

    public void setProductListener(ProductListener productListener) {
        this.mProductListener = productListener;
    }

    /* CRUD Products */

    public ArrayList<Product> getProductsDB() {
        return mStoreDB.getAllProductsDB();
    }

    public void insertProductsDB(ArrayList<Product> productList) {
        mStoreDB.deleteAllProductDB();
        for (Product product: productList) {
            mStoreDB.insertProductDB(product);
        }
    }

    public Product getProduct(int idProduct) {
        for (Product product: mProductList) {
            if (product.getId() == idProduct) {
                return product;
            }
        }

        return null;
    }

    public void insertProductDB(Product product) {
        mStoreDB.insertProductDB(product);
    }

    /* API ACCESS */

    public void getAllProductsAPI(final Context context, boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            mProductList = mStoreDB.getAllProductsDB();

            if (mProductListener != null) {
                mProductListener.onRefreshProductList(mProductList);
            }
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mApiUrl + "/api/products", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mProductList = ProductJsonParser.parserJsonProducts(response, context);
                    insertProductsDB(mProductList);
                    if (mProductListener != null) {
                        mProductListener.onRefreshProductList(mProductList);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            sVolleyQueue.add(request);
        }
    }
}
