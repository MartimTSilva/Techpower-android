package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private String mApiUrl;
    Button mLoginButton;
    EditText mUsernameEditText;
    EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mQueue = Volley.newRequestQueue(this);
        mLoginButton = findViewById(R.id.button_login);
        mUsernameEditText = findViewById(R.id.editText_username);
        mPasswordEditText = findViewById(R.id.editText_password);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        mApiUrl = preferences.getString(getString(R.string.app_api), "");

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    public void login() {
        // Make post body
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("username", mUsernameEditText.getText().toString());
        paramsMap.put("password", mPasswordEditText.getText().toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,  mApiUrl + "/api/users/login", new JSONObject(paramsMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Save authkey to shared preferences
                SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                try {
                    editor.putString("id", response.getString("id"));
                    editor.putString("username", response.getString("username"));
                    editor.putString("authkey", response.getString("auth-key"));
                    editor.putString("email", response.getString("email"));
                    editor.putString("firstName", response.getString("firstName"));
                    editor.putString("lastName", response.getString("lastName"));
                    editor.putString("phone", response.getString("phone"));
                    editor.putString("address", response.getString("address"));
                    editor.putString("nif", response.getString("nif"));
                    editor.putString("postal_code", response.getString("postal_code"));
                    editor.putString("city", response.getString("city"));
                    editor.putString("country", response.getString("country"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                editor.apply();

                finish();
                Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response.statusCode == 406) {
                    Toast.makeText(getApplicationContext(), R.string.login_incorrect, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mQueue.add(request);
    }
}
