package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionChange;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionDelete;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionInsert;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionUpdate;
import rx.Observable;
import rx.subjects.PublishSubject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionListTest {

    @Test
    public void whenSessionInserted_addsItToTheList() {
        List<SessionChange> sessionChanges = new LinkedList<>();
        sessionChanges.add(new SessionInsert(new Session("1", "John Doe", DateTime.parse("2016-01-01T13:01:00Z"), "JavaScript")));
        Observable<SessionChange> fakeSessionViewModelChanges = Observable.from(sessionChanges);
        SessionList subject = new SessionList(fakeSessionViewModelChanges);

        assertThat(subject.getItemCount(), equalTo(2));
        assertThat(((Session) subject.getItem(1)).getId(), equalTo("1"));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("John Doe"));
        assertThat(((Session) subject.getItem(1)).getDateTime(), equalTo(DateTime.parse("2016-01-01T13:01:00Z")));
        assertThat(((Session) subject.getItem(1)).getDescription(), equalTo("JavaScript"));
    }

    @Test
    public void whenSessionInserted_andSessionOccursOnANewDate_alsoAddsANewDateHeader() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-01-01T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Freddy First", DateTime.parse("2016-01-01T01:00:00Z"), "First session."));
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-01-03T00:00:00Z")));
        nonEmptyList.add(new Session("3", "Last Larry", DateTime.parse("2016-01-03T01:00:00Z"), "Last session"));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionInsert(new Session("2", "Middle Mary", DateTime.parse("2016-01-02T01:00:00Z"), "Middle session.")));

        assertThat(subject.getItemCount(), equalTo(6));
        assertThat(subject.getItem(2), instanceOf(DateHeader.class));
        assertThat(subject.getItem(3), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(2)).getDate(), equalTo("January 2, 2016"));
        assertThat(((Session) subject.getItem(3)).getId(), equalTo("2"));
    }

    @Test
    public void whenSessionInserted_andSessionOccursOnAnExistingDate_putsSessionUnderExistingDateHeader() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-02T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Karen", DateTime.parse("2016-02-02T10:00:00Z"), "First session on this day."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionInsert(new Session("2", "Kevin", DateTime.parse("2016-02-02T11:00:00Z"), "Second session on this day.")));

        assertThat(subject.getItemCount(), equalTo(3));
        assertThat(subject.getItem(0), instanceOf(DateHeader.class));
        assertThat(subject.getItem(1), instanceOf(Session.class));
        assertThat(subject.getItem(2), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(0)).getDate(), equalTo("February 2, 2016"));
        assertThat(((Session) subject.getItem(1)).getId(), equalTo("1"));
        assertThat(((Session) subject.getItem(2)).getId(), equalTo("2"));
    }

    @Test
    public void whenSessionDeleted_removesItFromTheList() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-02T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Karen", DateTime.parse("2016-02-02T10:00:00Z"), "1st session on the 2nd."));
        nonEmptyList.add(new Session("2", "Kevin", DateTime.parse("2016-02-02T10:00:00Z"), "2nd session on the 2rd."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionDelete("2"));

        assertThat(subject.getItemCount(), equalTo(2));
        assertThat(((Session) subject.getItem(1)).getId(), equalTo("1"));
    }

    @Test
    public void whenSessionDeleted_whenSessionIsNotInTheList_doesNothing() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-02T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Karen", DateTime.parse("2016-02-02T10:00:00Z"), "1st session on the 2nd."));
        nonEmptyList.add(new Session("2", "Kevin", DateTime.parse("2016-02-02T10:00:00Z"), "2nd session on the 2rd."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionDelete("99"));

        assertThat(subject.getItemCount(), equalTo(3));
        assertThat(subject.getItem(0), instanceOf(DateHeader.class));
        assertThat(subject.getItem(1), instanceOf(Session.class));
        assertThat(subject.getItem(2), instanceOf(Session.class));
        assertThat(((Session) subject.getItem(1)).getId(), equalTo("1"));
        assertThat(((Session) subject.getItem(2)).getId(), equalTo("2"));
    }

    @Test
    public void whenSessionDeleted_whenLastOneForThatDate_removesTheDateHeaderToo() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-02T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Karen", DateTime.parse("2016-02-02T10:00:00Z"), "Only session on the 2nd."));
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-03T00:00:00Z")));
        nonEmptyList.add(new Session("2", "Kevin", DateTime.parse("2016-02-03T10:00:00Z"), "Only session on the 3rd."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionDelete("2"));

        assertThat(subject.getItemCount(), equalTo(2));
        assertThat(subject.getItem(0), instanceOf(DateHeader.class));
        assertThat(subject.getItem(1), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(0)).getDate(), equalTo("February 2, 2016"));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("Karen"));
    }

    @Test
    public void whenSessionUpdated_updatesTheSameSessionInTheList() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-02T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Karen", DateTime.parse("2001-01-01T01:01:01Z"), "Original Session."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionUpdate(
                new Session("1", "Kevin", DateTime.parse("2002-02-02T02:02:02Z"), "Updated Session.")));

        assertThat(((Session) subject.getItem(1)).getId(), equalTo("1"));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("Kevin"));
        assertThat(((Session) subject.getItem(1)).getDateTime(), equalTo(DateTime.parse("2002-02-02T02:02:02Z")));
        assertThat(((Session) subject.getItem(1)).getDescription(), equalTo("Updated Session."));
    }

    @Test
    public void whenSessionUpdated_andSessionIsNotInTheList_addsItToTheList() {
        PublishSubject<SessionChange> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        SessionItemList nonEmptyList = new SessionItemList();
        nonEmptyList.add(new DateHeader(DateTime.parse("2016-02-02T00:00:00Z")));
        nonEmptyList.add(new Session("1", "Early Eddie", DateTime.parse("2016-01-02T00:00:00Z"), "Getting the worm"));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new SessionUpdate(new Session("2", "Late Larry", DateTime.parse("2016-12-31T13:01:00Z"), "Waking Up")));

        assertThat(subject.getItemCount(), equalTo(4));
        assertThat(subject.getItem(2), instanceOf(DateHeader.class));
        assertThat(subject.getItem(3), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(2)).getDate(), equalTo("December 31, 2016"));
        assertThat(((Session) subject.getItem(3)).getId(), equalTo("2"));
    }
}