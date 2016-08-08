package io.pivotal.pairwithme.viewschedule.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.pivotal.pairwithme.R;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {

    public static final int DATE_HEADER_TYPE = 0;
    public static final int SESSION_TYPE = 1;
    private final SessionList mSessionList;
    private final ViewHolderCreator mViewHolderCreator;

    public SessionListAdapter(final SessionList sessionList, final ViewHolderCreator viewHolderCreator) {
        mSessionList = sessionList;
        mViewHolderCreator = viewHolderCreator;
    }

    @Override
    public int getItemCount() {
        return mSessionList.getSessionCount();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if(viewType == DATE_HEADER_TYPE) {
            return mViewHolderCreator.createDateHeader(parent);
        } else {
            return mViewHolderCreator.createSession(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSessionList.getSession(position) instanceof DateHeaderViewModel) {
            return DATE_HEADER_TYPE;
        } else {
            return SESSION_TYPE;
        }
    }

    public static class ViewHolderCreator {
        public ViewHolder createDateHeader(final ViewGroup parent) {
            return new DateHeaderViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.date_header, parent, false));
        }

        public ViewHolder createSession(final ViewGroup parent) {
            return new DateHeaderViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.date_header, parent, false));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final ViewHolder holder, final int position) {
    }

    public static abstract class ViewHolder <VM extends ViewModel> extends RecyclerView.ViewHolder {
        protected abstract void setViewModel(VM viewModel);

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class SessionViewHolder extends ViewHolder<SessionViewModel> {
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

    public static class DateHeaderViewHolder extends ViewHolder<DateHeaderViewModel> {
        private TextView dateTextView;

        public DateHeaderViewHolder(final View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text);
        }

        public void setViewModel(DateHeaderViewModel model) {
            dateTextView.setText(model.getDate());
        }
    }
}
