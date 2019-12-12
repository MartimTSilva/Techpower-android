package com.example.techpower.models;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.techpower.helpers.StoreDBHelper;
import com.example.techpower.listeners.ProductListener;

import java.util.ArrayList;

public class SingletonStore {

    private static SingletonStore instance = null;
    private static RequestQueue sVolleyQueue;

    private ArrayList<Product> mProductList;
    private StoreDBHelper mStoreDB;
    private ProductListener mProductListener;

    public static synchronized SingletonStore getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonStore(context);
        }
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
}
