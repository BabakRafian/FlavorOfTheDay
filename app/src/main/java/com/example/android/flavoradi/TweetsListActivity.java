package com.example.android.flavoradi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.flavoradi.Utilities.GooglePlaceObject;
import com.example.android.flavoradi.Utilities.TWITTERObject;
import com.example.android.flavoradi.Utilities.TwitterHelper;

/**
 * Created by Babak on 11/5/2017.
 */

public class TweetsListActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private TweetsListAdapter mAdapter;
    private RecyclerView mNumbersList;
    private TextView test;

    Intent settingsIntent, favoritesListIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets_list);

        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        TWITTERObject twitterObject = null;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            twitterObject = (TWITTERObject) bundle.getSerializable("tweets");
        //String geocode = getIntent().getStringExtra("geocode");
        //TwitterHelper twitterObject = new TwitterHelper(this, placeName, geocode);
        //TWITTERObject tObject = new TWITTERObject(placeList);
        mAdapter = new TweetsListAdapter(twitterObject);
        mNumbersList.setAdapter(mAdapter);

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Resuming...");
    }

    @Override
    protected void onPause(){
        Log.d(TAG, "Pausing");
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        settingsIntent = new Intent(this,SettingsActivity.class);
        favoritesListIntent = new Intent(this,FavoritesListActivity.class);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(settingsIntent);

            return true;
        } else if (id == R.id.action_favoritesList) {
            startActivity(favoritesListIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
