package com.example.techpower.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.techpower.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductJsonParser {

    public static ArrayList<Product> parserJsonProducts (JSONArray response, Context context) {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject product = (JSONObject) response.get(i);
                int id = product.getInt("id");
                String name = product.getString("product_name");
                float price = (float) product.getDouble("unit_price");
                String description = product.getString("description");
                String image = product.getString("product_image");
                int idCategory = product.getInt("id_category");

                Product auxProduct = new Product(id, name, price, description, image, idCategory);
                productList.add(auxProduct);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  productList;
    }

    public static Product parserJsonProduct (String response, Context context) {
        Product auxProduct = null;

        try {
            JSONObject product = new JSONObject(response);
            int id = product.getInt("id");
            String name = product.getString("product_name");
            float price = (float) product.getDouble("unit_price");
            String description = product.getString("description");
            String image = product.getString("product_image");
            int idCategory = product.getInt("id_category");

            auxProduct = new Product(id, name, price, description, image, idCategory);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return auxProduct;
    }
}
