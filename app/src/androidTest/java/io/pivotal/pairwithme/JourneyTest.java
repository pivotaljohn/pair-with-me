package io.pivotal.pairwithme;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import io.pivotal.pairwithme.viewschedule.ViewScheduleActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JourneyTest {
    private static final String TAG = JourneyTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<ViewScheduleActivity> initialActivity =
            new ActivityTestRule<>(ViewScheduleActivity.class, true, false);

    private void unlockDevice() {
        final AppCompatActivity activity = initialActivity.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    public Thread getThreadByName(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) return t;
        }
        return null;
    }

    @Test
    public void KathyAndKevinFindATimeToPair() {

        Thread firebaseDatabaseWorkerWatchdog = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.State lastKnownState = Thread.State.TERMINATED;
                while (true) {
                    Thread firebaseDatabaseWorkerThread = getThreadByName("FirebaseDatabaseWorker");
                    if(firebaseDatabaseWorkerThread != null) {
                        Thread.State currentState = firebaseDatabaseWorkerThread.getState();
                        if (!currentState.equals(lastKnownState)) {
                            lastKnownState = currentState;
                            Log.i(TAG, "run: FirebaseDatabaseWorker.state = " + firebaseDatabaseWorkerThread.getState().toString());
                        }
                    }
                }
            }
        }, "FirebaseDatabaseWorkerWatchdog");
        firebaseDatabaseWorkerWatchdog.setDaemon(true);
        firebaseDatabaseWorkerWatchdog.start();

        initialActivity.launchActivity(new Intent());
        unlockDevice();

        onView(withId(R.id.view_schedule_screen))
                .check(matches(isDisplayed()));

        onView(withId(R.id.pairing_sessions_list))
                .check(matches(isDisplayed()))
                .check(matches(withChild(isDisplayed())));
    }
}