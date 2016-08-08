package io.pivotal.pairwithme.viewschedule.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolder<VM extends ViewModel> extends RecyclerView.ViewHolder {
    public abstract void setViewModel(VM viewModel);

    public ViewHolder(View itemView) {
        super(itemView);
    }
}
