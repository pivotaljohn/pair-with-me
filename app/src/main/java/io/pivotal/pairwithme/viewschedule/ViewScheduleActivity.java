package io.pivotal.pairwithme.viewschedule;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.pivotal.pairwithme.R;

public class ViewScheduleActivity extends AppCompatActivity {
    public static final String TAG = ViewScheduleActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        ScheduleFragment scheduleFragment = (ScheduleFragment) fragmentManager.findFragmentByTag(ScheduleFragment.TAG);

        if(scheduleFragment == null) {
            scheduleFragment = new ScheduleFragment();
            final FragmentTransaction tx = fragmentManager.beginTransaction();
            tx.add(R.id.view_schedule_screen, scheduleFragment, ScheduleFragment.TAG);
            tx.commit();
        }

        Log.d(TAG, "onCreate() returned");
    }
}
