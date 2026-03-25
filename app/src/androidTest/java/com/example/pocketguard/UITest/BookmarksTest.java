package com.example.pocketguard.UITest;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import com.example.pocketguard.Bookmarks.Sqlite_StudentListActivity;
import com.example.pocketguard.R;

@RunWith(AndroidJUnit4.class)
public class BookmarksTest {

    @Rule
    public ActivityTestRule<Sqlite_StudentListActivity> activityTestRule =
            new ActivityTestRule<>(Sqlite_StudentListActivity.class);

    @Test
    public void testFloatingButtonVisibility() {
        // Check if the floating action button is visible
        onView(withId(R.id.bottom_nav_home_bookmark)).check(matches(isDisplayed()));
    }
}
