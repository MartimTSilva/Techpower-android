package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText mApiUrl;
    Button mConnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.nav_settings);

        mConnectButton = findViewById(R.id.button_settings_connect);
        mApiUrl = findViewById(R.id.editText_ApiUrl);

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mApiUrl.getText().toString();
            }
        });
    }
}
