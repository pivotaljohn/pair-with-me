package io.pivotal.pairwithme.viewschedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.pivotal.pairwithme.R;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {
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
