package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String mApiUrl;
    Button mLoginButton;
    EditText mUsernameEditText;
    EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.nav_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginButton = findViewById(R.id.button_login);
        mUsernameEditText = findViewById(R.id.editText_username);
        mPasswordEditText = findViewById(R.id.editText_password);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        mApiUrl = preferences.getString(getString(R.string.app_api), "");

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    public void login() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,  mApiUrl + "/api/users/login", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Saves data in the shared preferences
                SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), Context.MODE_PRIVATE);
                User.saveUser(response, preferences);
                finish();
                Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response.statusCode == 401) {
                    Toast.makeText(getApplicationContext(), R.string.login_incorrect, Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Make post body
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                byte[] loginBytes = null;

                try {
                    loginBytes = (username + ":" + password).getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String loginBase64 = Base64.encodeToString(loginBytes, Base64.NO_WRAP);

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + loginBase64);
                return headers;
            }
        };
        SingletonStore.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
