package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Change;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Delete;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Insert;
import rx.Observable;
import rx.subjects.PublishSubject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionListTest {

    @Test
    public void whenSessionInserted_addsItToTheList() {
        List<Change<Session>> changes = new LinkedList<>();
        changes.add(new Insert(new Session("John Doe", DateTime.parse("2016-01-01T13:01:00Z"), "JavaScript")));
        Observable<Change<Session>> fakeSessionViewModelChanges = Observable.from(changes);
        SessionList subject = new SessionList(fakeSessionViewModelChanges);

        assertThat(subject.getSessionCount(), equalTo(2));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("John Doe"));
    }

    @Test
    public void givenListIsNotEmpty_whenSessionInsertedOccursOnANewDate_alsoAddsANewDateHeader() {
        PublishSubject<Change<Session>> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        List<SessionListItem> nonEmptyList = new LinkedList<>();
        nonEmptyList.add(new DateHeader("January 2, 2016"));
        nonEmptyList.add(new Session("Early Eddie", DateTime.parse("2016-01-02T00:00:00Z"), "Getting the worm"));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new Insert(new Session("Late Larry", DateTime.parse("2016-12-31T13:01:00Z"), "Waking Up")));

        assertThat(subject.getSessionCount(), equalTo(4));
        assertThat(subject.getItem(0), instanceOf(DateHeader.class));
        assertThat(subject.getItem(1), instanceOf(Session.class));
        assertThat(subject.getItem(2), instanceOf(DateHeader.class));
        assertThat(subject.getItem(3), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(0)).getDate(), equalTo("January 2, 2016"));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("Early Eddie"));
        assertThat(((DateHeader) subject.getItem(2)).getDate(), equalTo("December 31, 2016"));
        assertThat(((Session) subject.getItem(3)).getName(), equalTo("Late Larry"));
    }

    @Test
    public void givenListIsNotEmpty_whenSessionInsertedOccursOnAnExistingDate_placesSessionUnderneathExistingDateHeader() {
        PublishSubject<Change<Session>> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        List<SessionListItem> nonEmptyList = new LinkedList<>();
        nonEmptyList.add(new DateHeader("February 2, 2016"));
        nonEmptyList.add(new Session("Karen", DateTime.parse("2016-02-02T10:00:00Z"), "First session on this day."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new Insert(new Session("Kevin", DateTime.parse("2016-02-02T11:00:00Z"), "Second session on this day.")));

        assertThat(subject.getSessionCount(), equalTo(3));
        assertThat(subject.getItem(0), instanceOf(DateHeader.class));
        assertThat(subject.getItem(1), instanceOf(Session.class));
        assertThat(subject.getItem(2), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(0)).getDate(), equalTo("February 2, 2016"));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("Karen"));
        assertThat(((Session) subject.getItem(2)).getName(), equalTo("Kevin"));
    }

    @Test
    @Ignore
    public void onSessionDeleted_whenLastOneForThatDate_removesTheDateHeaderToo() {
        PublishSubject<Change<Session>> fakeSessionViewModelChanges = PublishSubject.create();
        SessionList subject = new SessionList(fakeSessionViewModelChanges);
        List<SessionListItem> nonEmptyList = new LinkedList<>();
        nonEmptyList.add(new DateHeader("February 2, 2016"));
        nonEmptyList.add(new Session("Karen", DateTime.parse("2016-02-02T10:00:00Z"), "Only session on the 2nd."));
        nonEmptyList.add(new DateHeader("February 3, 2016"));
        nonEmptyList.add(new Session("Kevin", DateTime.parse("2016-02-03T10:00:00Z"), "Only session on the 3rd."));
        subject.new TestHarness().setList(nonEmptyList);

        fakeSessionViewModelChanges.onNext(new Delete(new Session("Kevin", DateTime.parse("2016-02-02T11:00:00Z"), "Second session on this day.")));

        assertThat(subject.getSessionCount(), equalTo(3));
        assertThat(subject.getItem(0), instanceOf(DateHeader.class));
        assertThat(subject.getItem(1), instanceOf(Session.class));
        assertThat(subject.getItem(2), instanceOf(Session.class));
        assertThat(((DateHeader) subject.getItem(0)).getDate(), equalTo("February 2, 2016"));
        assertThat(((Session) subject.getItem(1)).getName(), equalTo("Karen"));
        assertThat(((Session) subject.getItem(2)).getName(), equalTo("Kevin"));

    }
}