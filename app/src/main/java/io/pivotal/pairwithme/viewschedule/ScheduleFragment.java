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

    public static class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {
        private String[] scheduleDates = {
                "Python: 3pm-6pm on July 26, 2016",
                "Ruby: 12pm-1pm on July 27, 2016",
                "Python: 9am-12pm on July 28, 2016",
                "Java: 12pm-3pm on July 29, 2016"
        };

        @Override
        public int getItemCount() {
            return scheduleDates.length;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.session, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.setDetail(scheduleDates[position]);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView sessionDetailTextView;

            public ViewHolder(final View itemView) {
                super(itemView);
                sessionDetailTextView = (TextView) itemView.findViewById(R.id.session_detail);
            }

            public void setDetail(String detail) {
                sessionDetailTextView.setText(detail);
            }
        }
    }
}
