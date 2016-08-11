package io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview;

import android.view.View;
import android.widget.TextView;

import io.pivotal.pairwithme.R;
import io.pivotal.pairwithme.viewschedule.ui.model.DateHeader;

public class DateHeaderViewHolder extends ViewHolder<DateHeader> {
    private TextView dateTextView;

    public DateHeaderViewHolder(final View itemView) {
        super(itemView);
        dateTextView = (TextView) itemView.findViewById(R.id.date_text);
    }

    public void setViewModel(DateHeader model) {
        dateTextView.setText(model.getDate());
    }
}
