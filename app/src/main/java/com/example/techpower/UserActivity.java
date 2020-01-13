package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;
import com.example.techpower.utils.Client;

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
                    SingletonStore.getInstance(getApplicationContext()).updateUserAPI(updateUser() ,getApplicationContext(),
                            authentication_key, user_id);
                }
                //TODO: Se o user alterar o username, fazer logout automaticamente
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setMessage(R.string.user_delete_alert_dialog)
                        .setPositiveButton(R.string.user_delete_alert_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SingletonStore.getInstance(getApplicationContext()).deleteUserAPI(getApplicationContext(),authentication_key, user_id);
                                Client.clientLogout(getApplicationContext());
                                finish();
                            }
                        }).setNegativeButton(R.string.user_delete_alert_cancel, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private User updateUser(){
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

        if (!Client.checkUsername(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }

        if (!Client.checkEmail(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        }

        if (!Client.checkFirstName(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        }

        if (!Client.checkLastName(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }

        if (!Client.checkAddress(address)) {
            mAddress.setError(getString(R.string.error_field_required));
            focusView = mAddress;
            cancel = true;
        }

        if (!Client.checkCity(city)) {
            mCity.setError(getString(R.string.error_field_required));
            focusView = mCity;
            cancel = true;
        }

        if (!Client.checkCountry(country)) {
            mCountry.setError(getString(R.string.error_field_required));
            focusView = mCountry;
            cancel = true;
        }

        if (!Client.checkPostalCode(postal_code)) {
            mPostalCode.setError(getString(R.string.error_field_required));
            focusView = mPostalCode;
            cancel = true;
        }

        if (!Client.checkPhone(phone)) {
            mPhone.setError(getString(R.string.error_field_required));
            focusView = mPhone;
            cancel = true;
        }

        if (!Client.checkNif(nif)) {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}