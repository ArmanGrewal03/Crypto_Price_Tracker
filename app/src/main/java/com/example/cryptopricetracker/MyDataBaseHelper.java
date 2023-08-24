package com.example.cryptopricetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String tableName = "watchListCrypto";
    private static final String id = "id";
    private static final String column1 = "Name";
    private static final String column2 = "Symbol";
    private static final String column3 = "Rate";

    private SQLiteDatabase db; // Add a SQLiteDatabase object

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase(); // Initialize the SQLiteDatabase object
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + tableName + "(" +
                id + " INTEGER PRIMARY KEY, " +
                column1 + " TEXT, " +
                column2 + " TEXT, " +
                column3 + " REAL" +
                ");";

        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Implement the necessary upgrade logic if needed
    }

    // Implement the methods to insert and retrieve data from the database

    public void insertCurrency(Model currencyItem) {
        ContentValues values = new ContentValues();
        values.put(column1, currencyItem.getName());
        values.put(column2, currencyItem.getSymbol());
        values.put(column3, currencyItem.getRate());

        db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void deleteCurrency(String symbol) {
        String whereClause = column2 + " = ?";
        String[] whereArgs = {symbol};
        db.delete(tableName, whereClause, whereArgs);
    }

    public Cursor getWatchlistCurrencies() {
        String[] projection = {id, column1, column2, column3};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }
}
