package com.example.techpower.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StoreDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "storeDB";
    private static final int DB_VERSION = 1;

    private final SQLiteDatabase database;

    public StoreDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO: Create database SQL
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: Drop database
    }

    // TODO: Create crud
}
