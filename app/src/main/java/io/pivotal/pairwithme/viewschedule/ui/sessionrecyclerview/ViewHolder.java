package io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.pivotal.pairwithme.viewschedule.ui.model.ScheduleItem;

public abstract class ViewHolder<VM extends ScheduleItem> extends RecyclerView.ViewHolder {
    public abstract void setViewModel(VM viewModel);

    public ViewHolder(View itemView) {
        super(itemView);
    }
}
