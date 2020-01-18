package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    Button btn_update;
    Button btn_delete;
    EditText mUsername;
    EditText mEmail;
    EditText mFirstName;
    EditText mLastName;
    EditText mPhone;
    EditText mAddress;
    EditText mCity;
    EditText mPostalCode;
    EditText mCountry;
    EditText mNif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_update = findViewById(R.id.button_update);
        btn_delete = findViewById(R.id.button_delete);
        mUsername = findViewById(R.id.editText_userPage_username);
        mEmail = findViewById(R.id.editText_userPage_email);
        mFirstName = findViewById(R.id.editText_userPage_firstName);
        mLastName = findViewById(R.id.editText_userPage_lastName);
        mPhone = findViewById(R.id.editText_userPage_phone);
        mAddress = findViewById(R.id.editText_userPage_address);
        mCity = findViewById(R.id.editText_userPage_city);
        mPostalCode = findViewById(R.id.editText_userPage_postalCode);
        mCountry = findViewById(R.id.editText_userPage_country);
        mNif = findViewById(R.id.editText_userPage_nif);

        //Gets all user information from the shared preferences
        final SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        try {
            mUsername.setText(preferences.getString("username", null));
            mEmail.setText(preferences.getString("email", null));
            mFirstName.setText(preferences.getString("firstName", null));
            mLastName.setText(preferences.getString("lastName", null));
            mPhone.setText(preferences.getString("phone", null));
            mAddress.setText(preferences.getString("address", null));
            mCity.setText(preferences.getString("city", null));
            mPostalCode.setText(preferences.getString("postal_code", null));
            mCountry.setText(preferences.getString("country", null));
            mNif.setText(preferences.getString("nif", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String authentication_key = preferences.getString("authkey", null);
        final String user_id = preferences.getString("id", null);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If there's no form errors, updates the user in the shared preferences and API
                if (updateUser() != null){
                    updateUserAPI(updateUser() ,getApplicationContext(), authentication_key, user_id);
                }
            }
        });

        //If the delete account btn is pressed it will show a confirmation dialog
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setMessage(R.string.user_delete_alert_dialog)
                        .setPositiveButton(R.string.user_delete_alert_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteUserAPI(getApplicationContext(),authentication_key, user_id);
                                User.deleteUser(getApplicationContext());
                                finish();
                            }
                        }).setNegativeButton(R.string.user_delete_alert_cancel, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private User updateUser(){
        //Gets all the text inside the form
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String phone = mPhone.getText().toString();
        String address = mAddress.getText().toString();
        String nif = mNif.getText().toString();
        String postal_code = mPostalCode.getText().toString();
        String city = mCity.getText().toString();
        String country = mCountry.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!User.checkUsername(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }

        if (!User.checkEmail(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        }

        if (!User.checkFirstName(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        }

        if (!User.checkLastName(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }

        if (!User.checkAddress(address)) {
            mAddress.setError(getString(R.string.error_field_required));
            focusView = mAddress;
            cancel = true;
        }

        if (!User.checkCity(city)) {
            mCity.setError(getString(R.string.error_field_required));
            focusView = mCity;
            cancel = true;
        }

        if (!User.checkCountry(country)) {
            mCountry.setError(getString(R.string.error_field_required));
            focusView = mCountry;
            cancel = true;
        }

        if (!User.checkPostalCode(postal_code)) {
            mPostalCode.setError(getString(R.string.error_field_required));
            focusView = mPostalCode;
            cancel = true;
        }

        if (!User.checkPhone(phone)) {
            mPhone.setError(getString(R.string.error_field_required));
            focusView = mPhone;
            cancel = true;
        }

        if (!User.checkNif(nif)) {
            mNif.setError(getString(R.string.error_field_required));
            focusView = mNif;
            cancel = true;
        }

        if (cancel) {
            // Se existirem erros será focado o primeiro input do form com erros
            focusView.requestFocus();
            return null;
        } else {
            return new User(username, email, null, firstName, lastName, phone, address, nif, postal_code, city, country);
        }
    }

    private void updateUserAPI(final User user, final Context context, final String authentication_key, String user_id) {
        Map<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("email", user.getEmail());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("phone", user.getPhone());
        params.put("address", user.getAddress());
        params.put("nif", user.getNif());
        params.put("postal_code", user.getPostalCode());
        params.put("city", user.getCity());
        params.put("country", user.getCountry());

        JSONObject object = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, SingletonStore.getInstance(this).getApiUrl() + "/api/users/" + user_id
                + "?access-token=" + authentication_key, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Updates shared preferences
                            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_preferences), Context.MODE_PRIVATE);
                            User.saveUser(response, preferences);
                            Toast.makeText(context, R.string.update_success, Toast.LENGTH_LONG).show();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.update_error, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Update Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("Techpower", "Update Error: " + error.toString());
                    }
                }
        );
        SingletonStore.getInstance(this).addToRequestQueue(request);
    }

    private void deleteUserAPI(final Context context, final String authentication_key, String user_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, SingletonStore.getInstance(this).getApiUrl() + "/api/users/" + user_id +
                "?access-token=" + authentication_key,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Delete Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {

        };
        SingletonStore.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}