package io.pivotal.pairwithme.viewschedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.pivotal.pairwithme.R;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {

    private static final int DATE_HEADER_TYPE = 0;
    private static final int SESSION_TYPE = 1;

    private ViewModel[] viewModels = {
            new DateHeaderViewModel("July 26, 2016"),
            new SessionViewModel("Jim Beam", "3pm-6pm", "Application that uses user data to find the best happy hours around"),
            new SessionViewModel("Kathy Buford", "12pm-1pm", "Learn Django stack by creating a heroku-deployed hello world app"),
            new DateHeaderViewModel("July 27, 2016"),
            new SessionViewModel("Tony Abbot", "10pm-1am", "Roo tracker written in Java")
    };

    @Override
    public int getItemCount() {
        return viewModels.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if(viewType == DATE_HEADER_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.date_header, parent, false);
            return new DateHeaderViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.session, parent, false);
            return new SessionViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (this.viewModels[position].getClass() == DateHeaderViewModel.class) {
            return DATE_HEADER_TYPE;
        } else {
            return SESSION_TYPE;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setViewModel(viewModels[position]);
    }

    public abstract class ViewHolder <VM extends ViewModel> extends RecyclerView.ViewHolder {
        protected abstract void setViewModel(VM viewModel);

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

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
}
