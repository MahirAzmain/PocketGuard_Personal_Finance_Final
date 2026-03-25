package com.example.pocketguard.UITest;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.view.View;

import com.example.pocketguard.HomeActivity;
import com.example.pocketguard.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {
    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void testSpecificView() {
        // Use more specific matchers to identify the correct view

        // Match the first instance of balance_tv with text "0"
        Matcher<View> firstBalanceTvMatcher = allOf(withId(R.id.balance_tv), withText("0"));
        onView(firstBalanceTvMatcher).check(matches(withText("0")));

        // Match the second instance of balance_tv with text "Click Here"
        Matcher<View> secondBalanceTvMatcher = allOf(withId(R.id.balance_tv), withText("Click Here"));
        onView(secondBalanceTvMatcher).check(matches(withText("Click Here")));
    }
}



