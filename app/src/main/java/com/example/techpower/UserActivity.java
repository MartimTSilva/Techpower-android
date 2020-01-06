package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Update user data
            }
        });
    }
}