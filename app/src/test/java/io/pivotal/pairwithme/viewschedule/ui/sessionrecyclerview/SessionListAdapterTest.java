package io.pivotal.pairwithme.viewschedule.ui.sessionrecyclerview;

import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;

import io.pivotal.pairwithme.viewschedule.ui.model.DateHeader;
import io.pivotal.pairwithme.viewschedule.ui.model.Schedule;
import io.pivotal.pairwithme.viewschedule.ui.model.ScheduleItem;
import io.pivotal.pairwithme.viewschedule.ui.model.Session;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionListAdapterTest {

    private SessionListAdapter subject;
    private Schedule mSchedule;
    private ViewHolderCreator mViewHolderCreator;

    @Before
    public void setUp() {
        mSchedule = mock(Schedule.class);
        mViewHolderCreator = mock(ViewHolderCreator.class);
        DateHeaderViewHolder dateHeaderViewHolder = mock(DateHeaderViewHolder.class);
        SessionViewHolder sessionViewHolder = mock(SessionViewHolder.class);
        when(mViewHolderCreator.createDateHeader(any(ViewGroup.class))).thenReturn(dateHeaderViewHolder);
        when(mViewHolderCreator.createSession(any(ViewGroup.class))).thenReturn(sessionViewHolder);

        subject = new SessionListAdapter(mSchedule, mViewHolderCreator);
    }

    @Test
    public void getItemCount_returnsSizeOfSessionList() {
        when(mSchedule.getItemCount()).thenReturn(0);
        assertThat(subject.getItemCount(), equalTo(0));

        when(mSchedule.getItemCount()).thenReturn(5);
        assertThat(subject.getItemCount(), equalTo(5));
    }

    @Test
    public void getItemViewType_whenItemInSessionListIsDateHeaderViewModel_returnsDATE_HEADER_TYPE() {
        DateHeader dateHeader = mock(DateHeader.class);
        when(mSchedule.getItem(4)).thenReturn(dateHeader);

        assertThat(subject.getItemViewType(4), equalTo(SessionListAdapter.DATE_HEADER_TYPE));
    }

    @Test
    public void getItemViewType_whenItemInSessionListIsSessionViewModel_returnsSESSION_TYPE() {
        Session session = mock(Session.class);
        when(mSchedule.getItem(4)).thenReturn(session);

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
        ScheduleItem scheduleItem = mock(ScheduleItem.class, "ScheduleItem at Position 5");
        when(mSchedule.getItem(5)).thenReturn(scheduleItem);
        ViewHolder viewHolder = mock(ViewHolder.class, "ViewHolder being bound");

        subject.onBindViewHolder(viewHolder, 5);

        verify(viewHolder).setViewModel(scheduleItem);
    }

}
