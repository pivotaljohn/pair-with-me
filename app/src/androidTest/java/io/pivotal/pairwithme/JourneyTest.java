package io.pivotal.pairwithme;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.pivotal.pairwithme.viewschedule.ViewScheduleActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JourneyTest {
    @Rule
    public ActivityTestRule<ViewScheduleActivity> initialActivity =
            new ActivityTestRule<>(ViewScheduleActivity.class, true, false);


    @Test
    public void KathyAndKevinFindATimeToPair() {
        initialActivity.launchActivity(new Intent());

        onView(withId(R.id.view_schedule_screen)).check(matches(isDisplayed()));
        onView(withId(R.id.pairing_sessions_list)).check(matches(isDisplayed()));
    }
}