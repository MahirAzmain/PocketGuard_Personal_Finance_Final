package com.example.pocketguard.UITest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.example.pocketguard.Map.Parental_Control;
import com.example.pocketguard.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

@LargeTest
public class Parental_ControlTest {

    @Rule
    public ActivityScenarioRule<Parental_Control> activityRule =
            new ActivityScenarioRule<>(Parental_Control.class);

    @Test
    public void testGetLocationAndBalance() {
        // Fill the child email and security code EditTexts
        Espresso.onView(withId(R.id.child_mail))
                .perform(ViewActions.replaceText("choyan@gmail.com"));
        //Here The values are checked Statically so, before running
        // the test make sure that the information are correctly given.
        Espresso.onView(withId(R.id.child_security))
                .perform(ViewActions.replaceText("20101"));

        // Close the keyboard (if it's open)
        Espresso.closeSoftKeyboard();

        // Click on the "Get Location And Balance" button
        Espresso.onView(withId(R.id.get_location)).perform(ViewActions.click());

        // Wait until the TextViews become visible
        waitUntilVisible(withId(R.id.child_lat));
        waitUntilVisible(withId(R.id.child_long));
        waitUntilVisible(withId(R.id.total_balance));
        waitUntilVisible(withId(R.id.total_income));
        waitUntilVisible(withId(R.id.total_expense));

        // Verify that the location and balance details are displayed
        Espresso.onView(withId(R.id.child_lat)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.child_long)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.total_balance)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.total_income)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.total_expense)).check(matches(isDisplayed()));

        // Additionally, you can verify the text content
        Espresso.onView(withId(R.id.child_lat))
                .check(matches(withText("Latitude\n22.8977789")));
        Espresso.onView(withId(R.id.child_long))
                .check(matches(withText("longitude\n89.5049115")));
        Espresso.onView(withId(R.id.total_balance))
                .check(matches(withText("Total Balance : 20")));
        Espresso.onView(withId(R.id.total_income))
                .check(matches(withText("Total Income : 20")));
        Espresso.onView(withId(R.id.total_expense))
                .check(matches(withText("Total Expense : 0")));
    }

    private void waitUntilVisible(Matcher<View> viewMatcher) {
        int retries = 10; // Number of retries
        while (retries > 0) {
            try {
                Espresso.onView(viewMatcher).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                return;
            } catch (AssertionError e) {
                retries--;
                try {
                    Thread.sleep(500); // wait for 500ms before retrying
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        // If after retries the view is still not visible, fail the test
        Espresso.onView(viewMatcher).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
