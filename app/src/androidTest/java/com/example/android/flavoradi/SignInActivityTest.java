package com.example.android.flavoradi;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Babak on 11/30/2017.
 */

public class SignInActivityTest {
    Intent intent;
    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class, true, false);

    @Test
    public void SignInActivityTextLocationTest1() {
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.imageView)).check(isBelow(withId(R.id.title)));
        onView(withId(R.id.txt_username)).check(isBelow(withId(R.id.imageView)));
        onView(withId(R.id.txt_password)).check(isBelow(withId(R.id.txt_username)));
        onView(withId(R.id.signIn_button)).check(isBelow(withId(R.id.txt_password)));
        onView(withId(R.id.button_signup)).check(isBelow(withId(R.id.txt_password)));
    }

    @Test
    public void SignInActivityTextLocationTest2() {
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.title)).check(isAbove(withId(R.id.imageView)));
        onView(withId(R.id.imageView)).check(isAbove(withId(R.id.txt_username)));
        onView(withId(R.id.txt_username)).check(isAbove(withId(R.id.txt_password)));
        onView(withId(R.id.txt_password)).check(isAbove(withId(R.id.signIn_button)));
        onView(withId(R.id.txt_password)).check(isAbove(withId(R.id.button_signup)));
    }
}
