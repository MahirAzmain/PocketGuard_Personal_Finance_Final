package com.example.pocketguard.UITest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.example.pocketguard.R;
import com.example.pocketguard.GoalSet.GoalRecyclerView;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class GoalSetTest {

    @Rule
    public ActivityScenarioRule<GoalRecyclerView> activityRule =
            new ActivityScenarioRule<>(GoalRecyclerView.class);

    @Test
    public void testRecyclerViewItemClick() {
        // Wait for RecyclerView to be displayed
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_income))
                .check(matches(isDisplayed()));

        // Click on the first item in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_income))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // Check if the dialog is displayed
        Espresso.onView(withId(R.id.feedback_in_review_dialog)).check(matches(isDisplayed()));

        // Enter amount in the EditText field
        Espresso.onView(withId(R.id.feedback_in_review_dialog))
                .perform(ViewActions.replaceText("500")); // Replace existing text with your desired amount

        // Close the keyboard (if it's open)
        Espresso.closeSoftKeyboard();

        // Click on the submit button
        Espresso.onView(withId(R.id.sending_feedback_btn_review_dialog)).perform(ViewActions.click());

        // Verify that the limit displayed in the salary box updates with the entered amount
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_income))
                .perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(withText("500")).check(matches(isDisplayed())); // Replace "500" with your expected amount
    }
}
