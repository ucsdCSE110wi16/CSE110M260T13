package com.prodevteam.tastebud;

/**
 * Created by Nzechi on 3/7/16.
 */

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MakeAPurchaceTest {
    @Rule   public final ActivityRule<PostLoginActivity> login = new ActivityRule<>(PostLoginActivity.class);

    @Test
    public void makeOrder(){
        /// selecting a restaurant from a list of restaurants
        onView(withId(R.id.rest_selector)).perform(click());
        //   onView(withId(R.id.rest_selector)).check(matches(withText(containsString("ExampleRestaurant"))));
        onData(allOf(is(instanceOf(String.class)), is("ExampleRestaurant"))).perform(click());

        // checking out the menu
        onView(withId(R.id.menu_button)).perform(click());

       // onData(allOf(is(instanceOf(String.class)), is("BBQ Ribs"))).check(matches(withText(containsString("BBQ Ribs"))));
        onView(withId(R.id.order_button)).perform(click());
        onView(withId(R.id.order_finished_button)).perform(click());
        onView(withId(R.id.signout_button)).perform(click());
    }
}
