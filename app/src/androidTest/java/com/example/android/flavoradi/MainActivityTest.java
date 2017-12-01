package com.example.android.flavoradi;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Babak on 11/30/2017.
 */

public class MainActivityTest {

    Intent intent;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);


    @Test
    public void MainActivityTextLocationTest1() {
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.imageView2)).check(isBelow(withId(R.id.app_name)));
        onView(withId(R.id.textView)).check(isBelow(withId(R.id.imageView2)));
        onView(withId(R.id.start_button)).check(isBelow(withId(R.id.textView)));
    }

    @Test
    public void MainActivityTextLocationTest2() {
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.app_name)).check(isAbove(withId(R.id.imageView2)));
        onView(withId(R.id.imageView2)).check(isAbove(withId(R.id.textView)));
        onView(withId(R.id.textView)).check(isAbove(withId(R.id.start_button)));
    }

}
