package io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview;

import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;

import io.pivotal.pairwithme.viewschedule.ui.model.DateHeader;
import io.pivotal.pairwithme.viewschedule.ui.model.SessionList;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.SessionListAdapter;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.DateHeaderViewHolder;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.SessionViewHolder;
import io.pivotal.pairwithme.viewschedule.ui.model.SessionListItem;
import io.pivotal.pairwithme.viewschedule.ui.model.Session;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.ViewHolder;
import io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview.ViewHolderCreator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionListAdapterTest {

    private SessionListAdapter subject;
    private SessionList mSessionList;
    private ViewHolderCreator mViewHolderCreator;

    @Before
    public void setUp() {
        mSessionList = mock(SessionList.class);
        mViewHolderCreator = mock(ViewHolderCreator.class);
        DateHeaderViewHolder dateHeaderViewHolder = mock(DateHeaderViewHolder.class);
        SessionViewHolder sessionViewHolder = mock(SessionViewHolder.class);
        when(mViewHolderCreator.createDateHeader(any(ViewGroup.class))).thenReturn(dateHeaderViewHolder);
        when(mViewHolderCreator.createSession(any(ViewGroup.class))).thenReturn(sessionViewHolder);

        subject = new SessionListAdapter(mSessionList, mViewHolderCreator);
    }

    @Test
    public void getItemCount_returnsSizeOfSessionList() {
        when(mSessionList.getSessionCount()).thenReturn(0);
        assertThat(subject.getItemCount(), equalTo(0));

        when(mSessionList.getSessionCount()).thenReturn(5);
        assertThat(subject.getItemCount(), equalTo(5));
    }

    @Test
    public void getItemViewType_whenItemInSessionListIsDateHeaderViewModel_returnsDATE_HEADER_TYPE() {
        DateHeader dateHeader = mock(DateHeader.class);
        when(mSessionList.getSession(4)).thenReturn(dateHeader);

        assertThat(subject.getItemViewType(4), equalTo(SessionListAdapter.DATE_HEADER_TYPE));
    }

    @Test
    public void getItemViewType_whenItemInSessionListIsSessionViewModel_returnsSESSION_TYPE() {
        Session session = mock(Session.class);
        when(mSessionList.getSession(4)).thenReturn(session);

        final int actualItemViewType = subject.getItemViewType(4);

        assertThat(actualItemViewType, equalTo(SessionListAdapter.SESSION_TYPE));
    }

    @Test
    public void onCreateViewHolder_whenAskedForDateHeaderViewHolder_makesIt() {
        assertThat(subject.onCreateViewHolder(mock(ViewGroup.class), SessionListAdapter.DATE_HEADER_TYPE),
                instanceOf(DateHeaderViewHolder.class));
    }

    @Test
    public void onCreateViewHolder_whenAskedForSessionViewHolder_makesIt() {
        assertThat(subject.onCreateViewHolder(mock(ViewGroup.class), SessionListAdapter.SESSION_TYPE),
                instanceOf(SessionViewHolder.class));
    }

    @Test
    public void onBindViewHolder_bindsSessionItemFromSpecifiedPositionToGivenViewHolder() {
        SessionListItem sessionListItem = mock(SessionListItem.class, "SessionListItem at Position 5");
        when(mSessionList.getSession(5)).thenReturn(sessionListItem);
        ViewHolder viewHolder = mock(ViewHolder.class, "ViewHolder being bound");

        subject.onBindViewHolder(viewHolder, 5);

        verify(viewHolder).setViewModel(sessionListItem);
    }

}
