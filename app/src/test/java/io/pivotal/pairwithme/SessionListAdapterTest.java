package io.pivotal.pairwithme;

import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;

import io.pivotal.pairwithme.viewschedule.ui.DateHeaderViewModel;
import io.pivotal.pairwithme.viewschedule.ui.SessionList;
import io.pivotal.pairwithme.viewschedule.ui.SessionListAdapter;
import io.pivotal.pairwithme.viewschedule.ui.SessionListAdapter.DateHeaderViewHolder;
import io.pivotal.pairwithme.viewschedule.ui.SessionListAdapter.SessionViewHolder;
import io.pivotal.pairwithme.viewschedule.ui.SessionViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionListAdapterTest {

    private SessionListAdapter subject;
    private SessionList mSessionList;
    private SessionListAdapter.ViewHolderCreator mViewHolderCreator;

    @Before
    public void setUp() {
        mSessionList = mock(SessionList.class);
        mViewHolderCreator = mock(SessionListAdapter.ViewHolderCreator.class);
        DateHeaderViewHolder dateHeaderViewHolder = mock(DateHeaderViewHolder.class);
        SessionViewHolder sessionViewHolder = mock(SessionViewHolder.class);
        when(mViewHolderCreator.createDateHeader(any(ViewGroup.class))).thenReturn(dateHeaderViewHolder);
        when(mViewHolderCreator.createSession(any(ViewGroup.class))).thenReturn(sessionViewHolder);

        subject = new SessionListAdapter(mSessionList, mViewHolderCreator);
    }

    @Test
    public void getItemCount_returnsSizeOfSessionList() {
        when(mSessionList.getSessionCount()).thenReturn(5);
        assertThat(subject.getItemCount(), equalTo(5));
    }

    @Test
    public void getItemViewType_whenItemInSessionListIsDateHeaderViewModel_returnsDATE_HEADER_TYPE() {
        DateHeaderViewModel dateHeader = mock(DateHeaderViewModel.class);
        when(mSessionList.getSession(4)).thenReturn(dateHeader);

        assertThat(subject.getItemViewType(4), equalTo(SessionListAdapter.DATE_HEADER_TYPE));
    }

    @Test
    public void getItemViewType_whenItemInSessionListIsSessionViewModel_returnsSESSION_TYPE() {
        SessionViewModel session = mock(SessionViewModel.class);
        when(mSessionList.getSession(7)).thenReturn(session);

        final int actualItemViewType = subject.getItemViewType(7);

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
}
