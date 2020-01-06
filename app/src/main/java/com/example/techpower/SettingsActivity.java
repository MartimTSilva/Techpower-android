package com.example.techpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText mApiUrl;
    private Button mSaveButton;
    private String loadedAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.nav_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSaveButton = findViewById(R.id.button_settings_connect);
        mApiUrl = findViewById(R.id.editText_ApiUrl);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        loadData();
    }

    public void saveData(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.app_api), mApiUrl.getText().toString());
        editor.apply();

        Toast.makeText(this, R.string.settings_api_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void loadData(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        loadedAPI = preferences.getString(getString(R.string.app_api), "");
        mApiUrl.setText(loadedAPI);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}

