package com.example.techpower.listeners;

import com.example.techpower.models.Product;

import java.util.ArrayList;

public interface ProductListener {
    void onRefreshProductList(ArrayList<Product> productArrayList);
}
