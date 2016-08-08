package io.pivotal.pairwithme.viewschedule.ui;

import android.view.View;
import android.widget.TextView;

import io.pivotal.pairwithme.R;

public class SessionViewHolder extends ViewHolder<SessionViewModel> {
    private TextView sessionTimeTextView;
    private TextView pairNameTextView;
    private TextView sessionDetailTextView;

    public SessionViewHolder(final View itemView) {
        super(itemView);
        sessionDetailTextView = (TextView) itemView.findViewById(R.id.session_detail);
        sessionTimeTextView = (TextView) itemView.findViewById(R.id.session_time);
        pairNameTextView = (TextView) itemView.findViewById(R.id.pair_name);
    }

    public void setViewModel(SessionViewModel model) {
        sessionTimeTextView.setText(model.getTime());
        pairNameTextView.setText(model.getName());
        sessionDetailTextView.setText(model.getDescription());
    }
}
