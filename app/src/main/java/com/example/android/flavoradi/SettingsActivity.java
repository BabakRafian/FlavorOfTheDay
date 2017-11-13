package com.example.android.flavoradi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Intent addRestaurantIntent, signOutIntent;
    Button addRestaurantButton, signOutButton;
    private final String TAG = getClass().getSimpleName();
    //SharedPreferences preferences = getSharedPreferences("FLAVORADI", MODE_PRIVATE);
    //SharedPreferences.Editor editor = preferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addRestaurantIntent = new Intent(this,AddRestaurantActivity.class);
        addRestaurantButton =(Button)findViewById(R.id.button_addrestaurant);
        addRestaurantButton.setOnClickListener(onClickListener);

        signOutIntent = new Intent(this,SignInActivity.class);
        signOutButton =(Button)findViewById(R.id.button_signout);
        signOutButton.setOnClickListener(onClickListener);
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
                    //startActivity(addRestaurantIntent);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/maps/answer/6320846?co=GENIE.Platform%3DAndroid&hl=en"));
                    startActivity(browserIntent);
                    break;
                case R.id.button_signout:
                    //editor.remove("currentUser");
                    startActivity(signOutIntent);
                    break;
            }
        }
    };

}
