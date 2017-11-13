package com.example.android.flavoradi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AddRestaurantActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    Intent settingsIntent;
    Button addRestaurantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        settingsIntent = new Intent(this,SettingsActivity.class);
        addRestaurantButton =(Button)findViewById(R.id.button_addrestaurant);
        addRestaurantButton.setOnClickListener(onClickListener);
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button_addrestaurant:
                    finish();
                    break;
            }
        }
    };

}
