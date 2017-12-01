package com.example.android.flavoradi;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    Intent intent;

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new ActivityTestRule<>(SignUpActivity.class, true, false);

    @Test
    public void addAccountTest() throws Exception {
        mActivityTestRule.launchActivity(intent);
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(mActivityTestRule.getActivity(), null, null, 1);
        Account account = new Account("Username", "Email@Address.com", "Password");
        mDatabaseHelper.addAccount(account);
        assertTrue(mDatabaseHelper.authenticate("Username", "Password"));
    }

    @Test
    public void deleteAccountTest() throws Exception {
        mActivityTestRule.launchActivity(intent);
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(mActivityTestRule.getActivity(), null, null, 1);
        Account account = new Account("Username", "Email@Address.com", "Password");
        mDatabaseHelper.addAccount(account);
        assertTrue(mDatabaseHelper.authenticate("Username", "Password"));
        mDatabaseHelper.deleteAccount("Username");
        assertFalse(mDatabaseHelper.authenticate("Username", "Password"));
    }




}
