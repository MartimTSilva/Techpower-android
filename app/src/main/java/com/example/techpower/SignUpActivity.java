package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button btn_signUp;
    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
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
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.nav_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_signUp = findViewById(R.id.button_signup);
        mUsername = findViewById(R.id.editText_username);
        mEmail = findViewById(R.id.editText_email);
        mPassword = findViewById(R.id.editText_password);
        mConfirmPassword = findViewById(R.id.editText_confirmPassword);
        mFirstName = findViewById(R.id.editText_firstName);
        mLastName = findViewById(R.id.editText_lastName);
        mPhone = findViewById(R.id.editText_phone);
        mAddress = findViewById(R.id.editText_address);
        mCity = findViewById(R.id.editText_city);
        mPostalCode = findViewById(R.id.editText_postalCode);
        mCountry = findViewById(R.id.editText_country);
        mNif = findViewById(R.id.editText_nif);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If there's no form errors, sign ups the user with the API
                if (attemptRegistration() != null){
                    signupUserAPI(attemptRegistration(), getApplicationContext());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }

    private User attemptRegistration() {
        // Resets all form errors
        mUsername.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);
        mFirstName.setError(null);
        mLastName.setError(null);
        mPhone.setError(null);
        mAddress.setError(null);
        mPostalCode.setError(null);
        mCity.setError(null);
        mCountry.setError(null);
        mNif.setError(null);


        final String username = mUsername.getText().toString();
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String firstName = mFirstName.getText().toString();
        final String lastName = mLastName.getText().toString();
        final String phone = mPhone.getText().toString();
        final String address = mAddress.getText().toString();
        final String postal_code = mPostalCode.getText().toString();
        final String city = mCity.getText().toString();
        final String country = mCountry.getText().toString();
        final String nif = mNif.getText().toString();


        boolean cancel = false;
        View focusView = null;

        String confirmPassword = mConfirmPassword.getText().toString();
        if (!confirmPassword.equals(password) || password.length() < 6) {
            mConfirmPassword.setError(getString(R.string.error_invalid_tooShort) + " or " + getString(R.string.error_password_not_match));
            focusView = mPassword;
            cancel = true;
        }

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
            // If there's an form input with errors it will focus on that editText
            focusView.requestFocus();
            return null;
        } else {
            return new User(username, email, password, firstName, lastName, phone, address, nif, postal_code, city, country);
        }
    }

    public void signupUserAPI(final User user, final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SingletonStore.getInstance(this).getApiUrl() + "/api/users",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("isSuccess");

                            if (success.equals("201")) {
                                Toast.makeText(context, R.string.signup_success, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.signup_error, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Register Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", user.getUsername());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("firstName", user.getFirstName());
                params.put("lastName", user.getLastName());
                params.put("phone", user.getPhone());
                params.put("nif", user.getNif());
                params.put("address", user.getAddress());
                params.put("postal_code", user.getPostalCode());
                params.put("city", user.getCity());
                params.put("country", user.getCountry());
                return params;
            }
        };
        SingletonStore.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
