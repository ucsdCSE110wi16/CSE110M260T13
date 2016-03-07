/*package com.prodevteam.tastebud;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;



 // Created by Nzechi on 3/6/16.

@RunWith(AndroidJUnit4.class)
public class AccountSettingScreenTest {

    @Rule
    public final ActivityRule<AccountSettingsScreen> settingsScreen = new ActivityRule<>(AccountSettingsScreen.class);

    @Test
    public void openSettingsMenu(){
       onView(withId(R.id.restrictions_field)).perform(replaceText("Pork"));
       onView(withId(R.id.restrictions_field)).check(matches(withText("Pork")));
    }
}

*/