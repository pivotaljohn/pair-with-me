package io.pivotal.pairwithme;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.pivotal.pairwithme.viewoffers.ViewOffersActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JourneyTest {
    @Rule
    public ActivityTestRule<ViewOffersActivity> initialActivity =
            new ActivityTestRule<>(ViewOffersActivity.class, true, false);


    @Test
    public void programmerFindsAndAcceptsAPair() {
        initialActivity.launchActivity(new Intent());

        onView(withId(R.id.view_offers_screen));
    }
}