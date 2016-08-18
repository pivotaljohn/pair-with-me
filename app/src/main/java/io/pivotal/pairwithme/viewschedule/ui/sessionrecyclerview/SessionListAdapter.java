package io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.pivotal.pairwithme.viewschedule.ui.model.DateHeader;
import io.pivotal.pairwithme.viewschedule.ui.model.SessionList;

public class SessionListAdapter extends RecyclerView.Adapter<ViewHolder> {

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
        return mSessionList.getItemCount();
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
        if (mSessionList.getItem(position) instanceof DateHeader) {
            return DATE_HEADER_TYPE;
        } else {
            return SESSION_TYPE;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setViewModel(mSessionList.getItem(position));
    }

}
