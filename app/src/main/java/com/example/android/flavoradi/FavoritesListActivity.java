package com.example.android.flavoradi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;

public class FavoritesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(this, null, null, 1);

        SharedPreferences preferences = getSharedPreferences("FLAVORADI", MODE_PRIVATE);
        String user = preferences.getString("currentUser", "<user>");
        String[] myStringArray = mDatabaseHelper.getFavoritesList(user);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myStringArray);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

}
