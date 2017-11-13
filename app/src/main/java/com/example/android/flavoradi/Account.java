package com.example.android.flavoradi;

/**
 * Created by Lyreks on 10/14/2017.
 */

public class Account {
    private int _id;
    private String _username;
    private String _email;
    private String _password;

    public Account(){
    }

    public Account(String username, String email, String password) {
        this._username = username;
        this._email = email;
        this._password = password;
    }

    public void set_username(String new_username) {
        this._username = new_username;
    }

    public void set_password(String new_password) {
        this._password = new_password;
    }

    public void set_email(String new_email) {
        this._email = new_email;
    }

    public String get_username()  {
        return _username;
    }

    public String get_email() {
        return _email;
    }

    public String get_password() {
        return _password;
    }
}
