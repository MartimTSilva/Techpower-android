package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.nio.charset.StandardCharsets;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mQueue = Volley.newRequestQueue(this);
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

                try {
                    // Save authkey to shared preferences
                    SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id", response.getString("id"));
                    editor.putString("username", response.getString("username"));
                    editor.putString("authkey", response.getString("auth_key"));
                    editor.putString("email", response.getString("email"));
                    editor.putString("firstName", response.getString("firstName"));
                    editor.putString("lastName", response.getString("lastName"));
                    editor.putString("phone", response.getString("phone"));
                    editor.putString("address", response.getString("address"));
                    editor.putString("nif", response.getString("nif"));
                    editor.putString("postal_code", response.getString("postal_code"));
                    editor.putString("city", response.getString("city"));
                    editor.putString("country", response.getString("country"));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

        mQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
