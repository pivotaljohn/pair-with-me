package io.pivotal.pairwithme.viewschedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.pivotal.pairwithme.R;

public class ScheduleFragment extends Fragment {
    public static final String TAG = ScheduleFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: " + "inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");

        View rootView = inflater.inflate(R.layout.schedule, container, false);
        RecyclerView sessionListView = (RecyclerView) rootView.findViewById(R.id.pairing_sessions_list);

        RecyclerView.LayoutManager sessionListLayoutManager = new LinearLayoutManager(getActivity());
        sessionListView.setLayoutManager(sessionListLayoutManager);

        RecyclerView.Adapter sessionListAdapter = new SessionListAdapter();
        sessionListView.setAdapter(sessionListAdapter);

        Log.d(TAG, "onCreateView() returned: " + rootView);
        return rootView;
    }
}
