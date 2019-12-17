package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        btn_signUp = findViewById(R.id.button_signup);
        mUsername = findViewById(R.id.editText_username);
        mEmail = findViewById(R.id.editText_email);
        mPassword = findViewById(R.id.editText_ApiUrl);
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
        mEmail.setError(null);
        mPassword.setError(null);

        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String phone = mPhone.getText().toString();
        String address = mAddress.getText().toString();
        String postalCode = mPostalCode.getText().toString();
        String city = mCity.getText().toString();
        String country = mCountry.getText().toString();
        String nif = mNif.getText().toString();


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
        } else if (firstName.length() > 255) {
            mEmail.setError(getString(R.string.et_email) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mEmail;
            cancel = true;
        } else if (firstName.length() < 6) {
            mEmail.setError(getString(R.string.et_email) + " " + getString(R.string.error_invalid_tooShort));
            focusView = mEmail;
            cancel = true;
        }

        // Vê se o utilizador inseriu um username
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (firstName.length() > 255) {
            mUsername.setError(getString(R.string.et_username) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mUsername;
            cancel = true;
        } else if (firstName.length() < 2) {
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
        if (TextUtils.isEmpty(postalCode)) {
            mPostalCode.setError(getString(R.string.error_field_required));
            focusView = mPostalCode;
            cancel = true;
        } else if (country.length() > 8) {
            mPostalCode.setError(getString(R.string.et_postalCode) + " " + getString(R.string.error_invalid_tooLong));
            focusView = mPostalCode;
            cancel = true;
        } else if (postalCode.length() < 4) {
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
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: INSERT INTO DB
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        String confirmPassword = mConfirmPassword.getText().toString();
        return confirmPassword.equals(password) && password.length() > 5;
    }
}
