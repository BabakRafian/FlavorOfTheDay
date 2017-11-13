package com.example.android.flavoradi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Babak on 10/4/2017.
 */

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this, null, null, 1);
    Intent signInIntent;
    private Button signUpButton;
    private EditText username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.txt_username);
        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);
        signUpButton =(Button)findViewById(R.id.button_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length() != 0 && email.length() != 0 && password.length() != 0) {
                    if (isValid(username.getText().toString())) {
                        Account new_account = new Account(username.getText().toString(), email.getText().toString(), password.getText().toString());
                        mDatabaseHelper.addAccount(new_account);
                        finish();
                    } else {
                        toastMessage("Invalid username.");
                    }
                } else {
                    toastMessage("Missing required field(s).");
                }
            }
        });
    }

    private boolean isValid(String string) {
        boolean result = true;
        String c = " !@#$%^&*()-=+{}|\\\"\';:/?.>,<`~";
        for (int i = 0; i < c.length(); i++) {
            for (int j = 0; j < string.length(); j++) {
                if (c.charAt(i) == string.charAt(j)) {
                    result = false;
                }
            }
        }
        return result;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
