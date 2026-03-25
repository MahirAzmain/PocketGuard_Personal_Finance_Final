package com.example.pocketguard.UITest;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.pocketguard.Account.Login;
import com.example.pocketguard.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void testSuccessfulLogin() {
        // Replace with a valid email and password present in your Firebase database
        String validEmail = "choyan@gmail.com";
        String validPassword = "12345";

        // Enter email
        onView(withId(R.id.email_login_page_et))
                .perform(replaceText(validEmail));

        // Enter password
        onView(withId(R.id.pass_login_page_et))
                .perform(replaceText(validPassword));

        // Click login button
        onView(withId(R.id.login))
                .perform(click());

        // Check if successfull toast message is displayed
        onView(withText("Login Successful ✅"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulLogin() {
        // Replace with an invalid email and password
        String invalidEmail = "choyan@gmail.com";
        String invalidPassword = "123456";

        // Enter email
        onView(withId(R.id.email_login_page_et))
                .perform(replaceText(invalidEmail));

        // Enter password
        onView(withId(R.id.pass_login_page_et))
                .perform(replaceText(invalidPassword));

        // Click login button
        onView(withId(R.id.login))
                .perform(click());

        // Check if error toast message is displayed
        onView(withText("Wrong Email or Password !!"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
