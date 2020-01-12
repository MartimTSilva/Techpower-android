package com.example.techpower.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.techpower.R;

public class Logout {
    public static void clientLogout(Context context){
        try {
            // Removes user data from shared preferences
            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("id");
            editor.remove("username");
            editor.remove("authkey");
            editor.remove("email");
            editor.remove("firstName");
            editor.remove("lastName");
            editor.remove("phone");
            editor.remove("address");
            editor.remove("nif");
            editor.remove("postal_code");
            editor.remove("city");
            editor.remove("country");
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
