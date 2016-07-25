package io.pivotal.pairwithme.viewschedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.pivotal.pairwithme.R;

public class ScheduleFragment extends Fragment {
    public static final String TAG = ScheduleFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: " + "inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");

        View root = inflater.inflate(R.layout.schedule, container, false);

        Log.d(TAG, "onCreateView() returned: " + root);
        return root;
    }
}
