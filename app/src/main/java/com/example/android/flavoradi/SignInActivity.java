package com.example.android.flavoradi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Babak on 10/4/2017.
 */

public class SignInActivity extends AppCompatActivity {

    Intent trendingListIntent, signupIntent;
    private Button signInButton, signUpButton;
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this, null, null, 1);
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        trendingListIntent = new Intent(this,TrendingListActivity.class);

        username = (EditText) findViewById(R.id.txt_username);
        password = (EditText) findViewById(R.id.txt_password);

        signInButton =(Button)findViewById(R.id.signIn_button);
        signInButton.setOnClickListener(onClickListener);

        signupIntent = new Intent(this,SignUpActivity.class);
        signUpButton =(Button)findViewById(R.id.button_signup);
        signUpButton.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.signIn_button:
                    if (mDatabaseHelper.authenticate(username.getText().toString(), password.getText().toString())) {
                        SharedPreferences preferences = getSharedPreferences("FLAVORADI", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("currentUser", username.getText().toString());
                        editor.commit();

                        startActivity(trendingListIntent);
                    } else {
                        toastMessage("Invalid login credentials.");
                    }
                    break;
                case R.id.button_signup:
                    startActivity(signupIntent);
                    break;
            }
        }
    };

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
