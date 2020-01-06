package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    Button btn_update;
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
    int user_id = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_update = findViewById(R.id.button_update);
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

        final SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        String userData = preferences.getString("user",null);
        String profileData = preferences.getString("profile",null);
        if (userData != null && profileData != null) {
            try {
                JSONObject userResponse = new JSONObject(userData);
                JSONObject profileResponse = new JSONObject(profileData);

                user_id = userResponse.getInt("id");
                mUsername.setText(userResponse.get("username").toString());
                mEmail.setText(userResponse.get("email").toString());
                mFirstName.setText(profileResponse.get("firstName").toString());
                mLastName.setText(profileResponse.get("lastName").toString());
                mPhone.setText(profileResponse.get("phone").toString());
                mAddress.setText(profileResponse.get("address").toString());
                mCity.setText(profileResponse.get("city").toString());
                mPostalCode.setText(profileResponse.get("postal_code").toString());
                mCountry.setText(profileResponse.get("country").toString());
                mNif.setText(profileResponse.get("nif").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String authentication_key = preferences.getString("auth", null);
                SingletonStore.getInstance(getApplicationContext()).updateUserAPI(updateUser() ,getApplicationContext(), authentication_key, user_id);
                //TODO: Se o user alterar o username, fazer logout automaticamente
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
        return new User(username, email, null, firstName, lastName, phone, address, nif, postal_code, city, country);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}