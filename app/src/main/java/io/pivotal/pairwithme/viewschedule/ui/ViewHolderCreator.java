package io.pivotal.pairwithme.viewschedule.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.pivotal.pairwithme.R;

public class ViewHolderCreator {
    public ViewHolder createDateHeader(final ViewGroup parent) {
        return new DateHeaderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.date_header, parent, false));
    }

    public ViewHolder createSession(final ViewGroup parent) {
        return new SessionViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.session, parent, false));
    }
}
