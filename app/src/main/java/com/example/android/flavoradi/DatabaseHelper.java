package com.example.android.flavoradi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by Lyreks on 10/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flavoradi.db";
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ACCOUNTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP IF TABLE EXISTS " + TABLE_ACCOUNTS;
        db.execSQL(upgradeTable);
        onCreate(db);
    }

    // Adds account to the database (accounts table)
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.get_username());
        values.put(COLUMN_EMAIL, account.get_email());
        values.put(COLUMN_PASSWORD, account.get_password());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close();
    }

    // Removes account from the database (accounts table)
    public void deleteAccount(String accountUsername) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ACCOUNTS + " WHERE " + COLUMN_USERNAME + "=\"" + accountUsername + "\";";
        db.execSQL(query);
    }

    // Checks username and password combination against existing entries in database (accounts table)
    public boolean authenticate(String username, String password) {
        boolean result;
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD};
        String selection = "username =?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_ACCOUNTS, columns, selection, selectionArgs, null, null, null, null);
        int iUsername = cursor.getColumnIndex(COLUMN_USERNAME);
        int iPassword = cursor.getColumnIndex(COLUMN_PASSWORD);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            if (username.equals(cursor.getString(iUsername)) && password.equals(cursor.getString(iPassword))) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        cursor.close();
        return result;
    }
}
