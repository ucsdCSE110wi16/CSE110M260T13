package com.prodevteam.tastebud;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
/**
 * Created by Super on 3/7/16.
 */
 @RunWith(AndroidJUnit4.class)
public class ViewRecommedationTest {
    @Rule
    public final ActivityRule<PostLoginActivity> login = new ActivityRule<>(PostLoginActivity.class);

    @Test
    public void myRecommedations(){
        onView(withId(R.id.rec_button)).perform(click());
        onView(withId(R.id.menu_scrollview)).perform(swipeUp());
        onView(withId(R.id.menu_scrollview)).perform(swipeUp());
        onView(withId(R.id.menu_scrollview)).perform(swipeDown());
        onView(withId(R.id.menu_scrollview)).perform(swipeDown());


    }
}
