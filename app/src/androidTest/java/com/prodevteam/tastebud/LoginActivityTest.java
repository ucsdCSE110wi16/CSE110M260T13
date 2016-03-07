package com.prodevteam.tastebud;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Nzechi Nwaokoro on 3/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule   public final ActivityRule<LoginActivity> login = new ActivityRule<>(LoginActivity.class);

    //login test uses the information below to log into an the app
    @Test
    public void launchLoginScreen(){
        onView(withId(R.id.email_field)).perform(replaceText("byzhong@ucsd.edu")).check(matches(withText("byzhong@ucsd.edu")));
        onView(withId(R.id.pass_field)).perform(replaceText("123456")).check(matches(isDisplayed()));
        onView(withId(R.id.signin_button)).perform(click());

        /// selecting a restaurant from a list of restaurants
        onView(withId(R.id.rest_selector)).perform(click());
     //   onView(withId(R.id.rest_selector)).check(matches(withText(containsString("ExampleRestaurant"))));
        onData(allOf(is(instanceOf(String.class)), is("64 Degrees"))).perform(click());

        // checking out the menu
        onView(withId(R.id.menu_button)).perform(click());
        onView(withId(R.id.menu_scrollview)).perform(swipeUp());
        onView(withId(R.id.back_button)).perform(click());


    }
}


