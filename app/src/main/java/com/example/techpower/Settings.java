package com.example.techpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    EditText mApiUrl;
    Button mConnectButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String API = "";

    private String loadedAPI;

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
                saveData();
            }
        });

        loadData();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(API, mApiUrl.getText().toString());

        editor.apply();

        Toast.makeText(this, R.string.settings_api_saved, Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        loadedAPI = sharedPreferences.getString(API, "");
        mApiUrl.setText(loadedAPI);
    }
}

