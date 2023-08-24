package com.example.cryptopricetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "watchList_crypto";
    private static final int DATABASE_VERSION =1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String Create_Table_Query = "CREATE TABLE watchList"+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "symbol TEXT, " +
            "name TEXT," +
            "rate REAL);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void clearTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        db.close();
    }
}
