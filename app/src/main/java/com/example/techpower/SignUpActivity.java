package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ProgressBar;

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
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration() {
        // Reseta os erros no formulário
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

        // Vê se o utilizador inseriu uma password e compara as duas passwords
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Vê se o utilizador inseriu um e-amil e se é válido
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        } else if (email.length() > 255) {
            mEmail.setError(getString(R.string.et_email) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mEmail;
            cancel = true;
        } else if (email.length() < 6) {
            mEmail.setError(getString(R.string.et_email) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mEmail;
            cancel = true;
        }

        // Vê se o utilizador inseriu um username
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (username.length() > 255) {
            mUsername.setError(getString(R.string.et_username) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mUsername;
            cancel = true;
        } else if (username.length() < 2) {
            mUsername.setError(getString(R.string.et_username) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mUsername;
            cancel = true;
        }

        // Vê se o utilizador inseriu um nome
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        } else if (firstName.length() > 50) {
            mFirstName.setError(getString(R.string.et_firstName) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mFirstName;
            cancel = true;
        } else if (firstName.length() < 2) {
            mFirstName.setError(getString(R.string.et_firstName) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mFirstName;
            cancel = true;
        }


        // Vê se o utilizador inseriu um apelido
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        } else if (lastName.length() > 50) {
            mLastName.setError(getString(R.string.et_lastName) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mLastName;
            cancel = true;
        } else if (lastName.length() < 2) {
            mLastName.setError(getString(R.string.et_lastName) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mLastName;
            cancel = true;
        }


        // Vê se o utilizador inseriu uma morada
        if (TextUtils.isEmpty(address)) {
            mAddress.setError(getString(R.string.error_field_required));
            focusView = mAddress;
            cancel = true;
        } else if (address.length() > 255) {
            mAddress.setError(getString(R.string.et_address) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mAddress;
            cancel = true;
        } else if (address.length() < 2) {
            mAddress.setError(getString(R.string.et_address) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mAddress;
            cancel = true;
        }


        // Vê se o utilizador inseriu uma cidade
        if (TextUtils.isEmpty(city)) {
            mCity.setError(getString(R.string.error_field_required));
            focusView = mCity;
            cancel = true;
        } else if (city.length() > 255) {
            mCity.setError(getString(R.string.et_city) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mCity;
            cancel = true;
        } else if (city.length() < 2) {
            mCity.setError(getString(R.string.et_city) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mCity;
            cancel = true;
        }


        // Vê se o utilizador inseriu um país
        if (TextUtils.isEmpty(country)) {
            mCountry.setError(getString(R.string.error_field_required));
            focusView = mCountry;
            cancel = true;
        } else if (country.length() > 255) {
            mCountry.setError(getString(R.string.et_country) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mCountry;
            cancel = true;
        } else if (country.length() < 2) {
            mCountry.setError(getString(R.string.et_country) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mCountry;
            cancel = true;
        }


        // Vê se o utilizador inseriu um código postal
        if (TextUtils.isEmpty(postal_code)) {
            mPostalCode.setError(getString(R.string.error_field_required));
            focusView = mPostalCode;
            cancel = true;
        } else if (country.length() > 8) {
            mPostalCode.setError(getString(R.string.et_postalCode) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mPostalCode;
            cancel = true;
        } else if (postal_code.length() < 4) {
            mPostalCode.setError(getString(R.string.et_postalCode) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mPostalCode;
            cancel = true;
        }


        // Vê se o utilizador inseriu um telefone
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError(getString(R.string.error_field_required));
            focusView = mPhone;
            cancel = true;
        } else if (phone.length() != 9) {
            mPhone.setError(getString(R.string.error_invalid_phone));
            focusView = mPhone;
            cancel = true;
        }


        // Vê se o utilizador inseriu um nif
        if (TextUtils.isEmpty(nif)) {
            mNif.setError(getString(R.string.error_field_required));
            focusView = mNif;
            cancel = true;
        } else if (nif.length() != 9) {
            mNif.setError(getString(R.string.error_invalid_nif));
            focusView = mNif;
            cancel = true;
        }


        if (cancel) {
            // Se existirem erros será focado o primeiro input do form com erros
            focusView.requestFocus();
        } else {
            SingletonStore.getInstance(getApplicationContext()).signupUserAPI(signupUser(), getApplicationContext());
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        String confirmPassword = mConfirmPassword.getText().toString();
        return confirmPassword.equals(password) && password.length() > 5;
    }

    private User signupUser(){
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String phone = mPhone.getText().toString();
        String address = mAddress.getText().toString();
        String nif = mNif.getText().toString();
        String postal_code = mPostalCode.getText().toString();
        String city = mCity.getText().toString();
        String country = mCountry.getText().toString();
        return new User(username, email, password, firstName, lastName, phone, address, nif, postal_code, city, country);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
