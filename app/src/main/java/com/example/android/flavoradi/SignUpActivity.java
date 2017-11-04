package com.example.android.flavoradi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Babak on 10/4/2017.
 */

public class SignUpActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    Intent signInIntent;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signInIntent = new Intent(this,SignInActivity.class);
        signUpButton =(Button)findViewById(R.id.button_signup);
        signUpButton.setOnClickListener(onClickListener);
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
                case R.id.button_signup:
                    startActivity(signInIntent);
                    break;
            }
        }
    };
}
