package io.pivotal.pairwithme.viewschedule.ui;

import android.view.View;
import android.widget.TextView;

import io.pivotal.pairwithme.R;

public class DateHeaderViewHolder extends ViewHolder<DateHeaderViewModel> {
    private TextView dateTextView;

    public DateHeaderViewHolder(final View itemView) {
        super(itemView);
        dateTextView = (TextView) itemView.findViewById(R.id.date_text);
    }

    public void setViewModel(DateHeaderViewModel model) {
        dateTextView.setText(model.getDate());
    }
}
