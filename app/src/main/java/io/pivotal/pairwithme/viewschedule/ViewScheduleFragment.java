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

import io.pivotal.pairwithme.R;
import io.pivotal.pairwithme.domain.PairingSessionChange;
import io.pivotal.pairwithme.domain.PairingSessionChanges;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionChanges;
import io.pivotal.pairwithme.viewschedule.ui.model.SessionList;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.SessionListAdapter;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.ViewHolderCreator;

public class ViewScheduleFragment extends Fragment {
    public static final String TAG = ViewScheduleFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: " + "inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");

        View rootView = inflater.inflate(R.layout.schedule, container, false);
        RecyclerView sessionListView = (RecyclerView) rootView.findViewById(R.id.pairing_sessions_list);

        RecyclerView.LayoutManager sessionListLayoutManager = new LinearLayoutManager(getActivity());
        sessionListView.setLayoutManager(sessionListLayoutManager);

        PairingSessionChanges pairingSessionChanges = new PairingSessionChanges();
        SessionChanges sessionChanges = new SessionChanges(pairingSessionChanges.asObservable());
        final SessionList sessionList = new SessionList(sessionChanges.asObservable());

        pairingSessionChanges.publish();

        RecyclerView.Adapter sessionListAdapter = new SessionListAdapter(sessionList, new ViewHolderCreator());
        sessionListView.setAdapter(sessionListAdapter);

        Log.d(TAG, "onCreateView() returned: " + rootView);
        return rootView;
    }
}
