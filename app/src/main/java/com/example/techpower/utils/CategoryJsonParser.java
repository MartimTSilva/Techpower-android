package com.example.techpower.utils;

import android.content.Context;

import com.example.techpower.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryJsonParser {

    public static ArrayList<Category> parserJsonCategories (JSONArray response, Context context) {
        ArrayList<Category> categoryList = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject category = (JSONObject) response.get(i);
                int id = category.getInt("id");
                String description = category.getString("description");
                int parentId = !category.isNull("parent_id") ? category.getInt("parent_id") : -1;

                Category auxCategory = new Category(id, description, parentId);
                categoryList.add(auxCategory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoryList;
    }
}
