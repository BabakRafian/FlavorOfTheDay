package com.example.android.flavoradi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

/**
 * Created by Lyreks on 10/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flavoradi";

    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String COLUMN_ACCT_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_FAVE_ID = "_id";

    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String COLUMN_REST_ID = "_id";
    private static final String COLUMN_RESTNAME = "restname";
    private static final String COLUMN_TOTAL_TWEETS = "total_tweets";
    private static final String COLUMN_NUM_UPDATED = "num_updated";

    private static final String CREATE_ACCOUNTS = "CREATE TABLE " + TABLE_ACCOUNTS + "(" +
                    COLUMN_ACCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT" +
                    ");";

    private static final String CREATE_RESTAURANTS = "CREATE TABLE " + TABLE_RESTAURANTS + "(" +
                    COLUMN_REST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESTNAME + " TEXT, " +
                    COLUMN_TOTAL_TWEETS + " INTEGER, " +
                    COLUMN_NUM_UPDATED + " INTEGER" +
                    ");";

    private static final String CREATE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "(" +
                    COLUMN_FAVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_RESTNAME + " TEXT" +
                    ");";
    private static final String UPGRADE_ACCOUNTS = "DROP IF TABLE EXISTS " + TABLE_ACCOUNTS;
    private static final String UPGRADE_RESTAURANTS = "DROP IF TABLE EXISTS " + TABLE_RESTAURANTS;
    private static final String UPGRADE_FAVORITES = "DROP IF TABLE EXISTS " + TABLE_FAVORITES;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNTS);
        db.execSQL(CREATE_RESTAURANTS);
        db.execSQL(CREATE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UPGRADE_ACCOUNTS);
        db.execSQL(UPGRADE_RESTAURANTS);
        db.execSQL(UPGRADE_FAVORITES);
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
        db.close();
    }

    // Checks username and password combination against existing entries in database (accounts table)
    public boolean authenticate(String username, String password) {
        boolean result = false;
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
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    // Adds favorite to the database (favorites table)
    public void addFavorite(Account account, Restaurant restaurant) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_USERNAME, account.get_username());
        values.put(COLUMN_RESTNAME, restaurant.get_restname());
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    // Adds favorite to the database (favorites table)
    public void addFavorite(String accountUsername, String restaurantName) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_USERNAME, accountUsername);
        values.put(COLUMN_RESTNAME, restaurantName);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    // Removes favorite from the database (favorites table)
    public void deleteFavorite(String accountUsername, String restaurantName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_USERNAME + "=\"" + accountUsername + "\"" +
                " AND " + COLUMN_RESTNAME + "=\"" + restaurantName + "\";";
        db.execSQL(query);
        db.close();
    }

    // Checks if user has favorite for specified restaurant
    public boolean isFavorite(String accountUsername, String restaurantName) {
        boolean result = false;
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_RESTNAME};
        String selection = "username =?";
        String[] selectionArgs = {accountUsername};

        Cursor cursor = db.query(TABLE_FAVORITES, columns, selection, selectionArgs, null, null, null, null);
        int iUsername = cursor.getColumnIndex(COLUMN_USERNAME);
        int iRestname = cursor.getColumnIndex(COLUMN_RESTNAME);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            if (accountUsername.equals(cursor.getString(iUsername)) && restaurantName.equals(cursor.getString(iRestname))) {
                result = true;
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    // Returns number of favorites associated with given account name
    public int getNumberOfFavorites(String accountUsername) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_RESTNAME};
        String selection = "username =?";
        String[] selectionArgs = {accountUsername};

        Cursor cursor = db.query(TABLE_FAVORITES, columns, selection, selectionArgs, null, null, null, null);
        int numOfFavorites = cursor.getCount();

        cursor.close();
        db.close();
        return numOfFavorites;
    }

    // Returns a String array of all restaurant names an account has favorited
    public String[] getFavoritesList(String accountUsername) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_RESTNAME};
        String selection = "username =?";
        String[] selectionArgs = {accountUsername};

        String[] restaurantNames;
        // Get query and column indexes
        Cursor cursor = db.query(TABLE_FAVORITES, columns, selection, selectionArgs, null, null, null, null);
        int iUsername = cursor.getColumnIndex(COLUMN_USERNAME);
        int iRestname = cursor.getColumnIndex(COLUMN_RESTNAME);
        // Populate array
        int rows = cursor.getCount();
        cursor.moveToFirst();
        if (rows > 0) {
            restaurantNames = new String[rows];
            int i = 0;
            while (!cursor.isAfterLast()) {
                restaurantNames[i] = cursor.getString(iRestname);
                cursor.moveToNext();
                i++;
            }
        }
        else {
            restaurantNames = new String[1];
            restaurantNames[0] = "No Favorites";
        }
        // Conclude
        cursor.close();
        db.close();
        return restaurantNames;
    }

    // Adds restaurant to the database (restaurant table)
    public void addRestaurant(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_RESTNAME, restaurant.get_restname());
        values.put(COLUMN_TOTAL_TWEETS, restaurant.get_total_tweets());
        values.put(COLUMN_NUM_UPDATED, restaurant.get_num_updated());
        db.insert(TABLE_RESTAURANTS, null, values);
        db.close();
    }

    // Removes restaurant from the database (restaurant table)
    public void deleteRestaurant(String restaurantName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_RESTAURANTS + " WHERE " + COLUMN_RESTNAME + "=\"" + restaurantName + "\";";
        db.execSQL(query);
        db.close();
    }

    // Checks if user has favorite for specified restaurant
    public boolean isRestaurant(String restaurantName) {
        boolean result = false;
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_RESTNAME};
        String selection = "restname =?";
        String[] selectionArgs = {restaurantName};

        Cursor cursor = db.query(TABLE_RESTAURANTS, columns, selection, selectionArgs, null, null, null, null);
        int iRestname = cursor.getColumnIndex(COLUMN_RESTNAME);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            if (restaurantName.equals(cursor.getString(iRestname))) {
                result = true;
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    // Gets restaurant data and creates Restaurant object
    public Restaurant getRestaurant(String restaurantName) {
        SQLiteDatabase db = getReadableDatabase();
        Restaurant restaurant = new Restaurant();
        String[] columns = {COLUMN_RESTNAME};
        String selection = "restname =?";
        String[] selectionArgs = {restaurantName};

        Cursor cursor = db.query(TABLE_RESTAURANTS, columns, selection, selectionArgs, null, null, null, null);
        int iRestname = cursor.getColumnIndex(COLUMN_RESTNAME);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            if (restaurantName.equals(cursor.getString(iRestname))) {
                restaurant.set_restname(cursor.getString(cursor.getColumnIndex(COLUMN_RESTNAME)));
                restaurant.set_total_tweets(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_TWEETS)));
                restaurant.set_num_updated(cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_UPDATED)));
            }
        }
        cursor.close();
        db.close();
        return restaurant;
    }

    // Updates total tweets associated with a restaurant
    public void updateRestaurant(String restaurantName, int num_tweets) {
        Restaurant restaurant = getRestaurant(restaurantName);
        restaurant.update_total_tweets(num_tweets);
        restaurant.increment_num_updated();
        deleteRestaurant(restaurant.get_restname());
        addRestaurant(restaurant);
    }

    // Returns a double corresponding to how much a restaurant is being talked about versus the norm
    public double getTweetPopularityRatio(String restaurantName, int num_tweets) {
        double ratio = (double) num_tweets;
        Restaurant restaurant = getRestaurant(restaurantName);
        ratio /= ((double)restaurant.get_total_tweets()/restaurant.get_num_updated());
        return ratio;
    }

}
