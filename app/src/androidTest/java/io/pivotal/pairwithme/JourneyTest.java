package io.pivotal.pairwithme;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    FirebaseIdlingResource idlingResource;

    @Before
    public void registerIntentServiceIdlingResource() {
        idlingResource = new FirebaseIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    private static class FirebaseIdlingResource implements IdlingResource {
        private volatile boolean idle = false; // while starting up, report, "busy."
        private long lastTransition = System.currentTimeMillis();
        private long debounceTolerance = 2000;
        private ResourceCallback mCallback;

        public FirebaseIdlingResource() {
            Thread watchdog = new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread firebaseDatabaseWorkerThread = getFirebaseWorkerThread();
                    Thread.State lastKnownState = firebaseDatabaseWorkerThread.getState();
                    //noinspection InfiniteLoopStatement -- is daemon thread.
                    while (true) {
                        Thread.yield();
                        long now = System.currentTimeMillis();
                        Thread.State currentState = firebaseDatabaseWorkerThread.getState();
                        if(currentState.equals(lastKnownState) ) {
                            if(transitionIsStable(now)) {
                                Log.d(TAG, String.format("run: transitions has stablized (now = %d; lastTransition = %d; debounceTolerance = %d)", now, lastTransition, debounceTolerance));
                                idle = !currentState.equals(Thread.State.RUNNABLE);
                                Log.d(TAG, "run: current state = " + currentState.toString() + "; idle = " + idle);
                                if(idle) {
                                    Log.d(TAG, "run: onTransitionToIdle() signaled.");
                                    mCallback.onTransitionToIdle();
                                    lastTransition = Long.MAX_VALUE - debounceTolerance;
                                }
                            }
                        } else {
                            Log.d(TAG, "run: " + lastKnownState.toString() + " ==> " + currentState.toString());
                            lastKnownState = currentState;
                            lastTransition = System.currentTimeMillis();
                        }
                    }
                }

                private boolean transitionIsStable(long now) {
                    return now > lastTransition + debounceTolerance;
                }
            }, "FirebaseDatabaseWorkerWatchdog");
            watchdog.setDaemon(true);
            watchdog.start();
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            mCallback = callback;
        }

        @Override
        public boolean isIdleNow() {
            if (idle && mCallback != null) {
                mCallback.onTransitionToIdle();
            }
            return idle;
        }

        @Override
        public String getName() {
            return "FirebaseDatabaseWorker";
        }

        public Thread getFirebaseWorkerThread() {
            Thread workerThread = null;
            while(workerThread == null) {
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    if (t.getName().equals("FirebaseDatabaseWorker")) {
                        workerThread = t;
                        break;
                    }
                }
            }
            return workerThread;
        }
    }

    @Test
    public void KathyAndKevinFindATimeToPair() {
        initialActivity.launchActivity(new Intent());
        unlockDevice();

        onView(withId(R.id.view_schedule_screen))
                .check(matches(isDisplayed()));

        onView(withId(R.id.pairing_sessions_list))
                .check(matches(isDisplayed()))
                .check(matches(withChild(isDisplayed())));
    }
}