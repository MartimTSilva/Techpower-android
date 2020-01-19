package com.example.techpower.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.techpower.MainActivity;
import com.example.techpower.R;
import com.example.techpower.helpers.StoreDBHelper;
import com.example.techpower.listeners.ProductListener;
import com.example.techpower.utils.CategoryJsonParser;
import com.example.techpower.utils.ProductJsonParser;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SingletonStore {

    private static SingletonStore instance = null;
    private static String sApiUrl;
    private static Context sContext;

    private RequestQueue mRequestQueue;
    private ArrayList<Product> mProductList;
    private ArrayList<Category> mCategoryList;
    private StoreDBHelper mStoreDB;
    private ProductListener mProductListener;
    private ArrayList<CartItem> mCart;

    private SingletonStore(Context context) {
        sContext = context;
        mRequestQueue = getRequestQueue();
        mProductList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mStoreDB = new StoreDBHelper(context);
        mCart = new ArrayList<>();
    }

    public static synchronized SingletonStore getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonStore(context);
        }
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_preferences), context.MODE_PRIVATE);
        sApiUrl = preferences.getString(context.getString(R.string.app_api), "");

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public String getApiUrl() {
        return sApiUrl;
    }

    public static boolean isConnectedInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
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
        for (Product product : productList) {
            mStoreDB.insertProductDB(product);
        }
    }

    public Product getProduct(int idProduct) {
        for (Product product : mProductList) {
            if (product.getId() == idProduct) {
                return product;
            }
        }

        return null;
    }

    public void insertProductDB(Product product) {
        mStoreDB.insertProductDB(product);
    }

    public void getProductsByCategory(final Context context, int id_category) {
        //Gets all the products of the category
        mProductList = mStoreDB.getAllProductsByCategoryDB(id_category);

        //If there's products in the category it gets displayed else shows a Toast.
        if (mProductListener != null) {
            mProductListener.onRefreshProductList(mProductList);
        }
        if (mProductList.isEmpty()) {
            Toast.makeText(context, "This category doesn't have any products", Toast.LENGTH_SHORT).show();
        }
    }

    /* CRUD Categories */

    public void insertCategoriesDB(ArrayList<Category> categoryList) {
        mStoreDB.deleteAllCategoriesDB();
        for (Category category : categoryList) {
            mStoreDB.insertCategoryDB(category);
        }
    }

    public ArrayList<Category> getCategoriesDB() {
        return mStoreDB.getAllCategoriesDB();
    }

    /* CRUD CART */

    public void addProductCart(int productId, int quantity) {
        int exists = 0;
        //Checks if cart empty
        if (mCart.size() == 0) {
            CartItem new_item = new CartItem(productId, quantity);
            mCart.add(new_item);
        } else {
            //Runs through cart trying to find a match
            for (CartItem item : mCart) {
                if (item.getId() == productId) {
                    exists = 1;
                }
            }
            //If no match is found, add product to cart
            if (exists != 1){
                CartItem new_item = new CartItem(productId, quantity);
                mCart.add(new_item);
            }
        }
    }


    public ArrayList<CartItem> getCart() {
        return mCart;
    }

    public CartItem getCartItem(int pos) {
        return mCart.get(pos);
    }

    public Float getCartTotal() {
        DecimalFormat df = new DecimalFormat("0.00");
        double total = 0;
        for (int i = 0; i < mCart.size(); i++) {
            CartItem item = SingletonStore.getInstance(sContext).getCartItem(i);
            Product product = SingletonStore.getInstance(sContext).getProduct(item.getId());
            total += product.getPrice() * item.getQuantity();
        }
        return Float.parseFloat(df.format(total));
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
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, sApiUrl + "/api/products", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mProductList = ProductJsonParser.parserJsonProducts(response, context, sApiUrl);
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
            addToRequestQueue(request);
        }
    }

    public void getAllCategoriesAPI(final Context context, boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, sApiUrl + "/api/categories", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mCategoryList = CategoryJsonParser.parserJsonCategories(response, context);
                    insertCategoriesDB(mCategoryList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            addToRequestQueue(request);
        }
    }
}
