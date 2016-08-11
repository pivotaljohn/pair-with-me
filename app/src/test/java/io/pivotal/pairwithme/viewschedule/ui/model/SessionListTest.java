package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Change;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Insert;
import io.pivotal.pairwithme.viewschedule.ui.model.SessionList;
import io.pivotal.pairwithme.viewschedule.ui.model.Session;
import rx.Observable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionListTest {

    @Test
    public void whenSessionViewModelInserted_includesThatViewModelInList() {
        List<Change<Session>> changes = new LinkedList<>();
        changes.add(new Insert(new Session("John Doe", DateTime.parse("2016-01-01T13:01:00Z"), "JavaScript")));
        Observable<Change<Session>> fakeSessionViewModelChanges = Observable.from(changes);
        SessionList subject = new SessionList(fakeSessionViewModelChanges);

        assertThat(subject.getSessionCount(), equalTo(2));
        assertThat(((Session) subject.getSession(1)).getName(), equalTo("John Doe"));
    }

    @Test
    public void givenExistSessionViewModelsOccurOnAnotherDate_whenSessionViewModelInserted_includesNewDateHeader() {
        List<Change<Session>> changes = new LinkedList<>();
        changes.add(new Insert(new Session("John Doe", DateTime.parse("2016-01-01T13:01:00Z"), "JavaScript")));
        Observable<Change<Session>> fakeSessionViewModelChanges = Observable.from(changes);
        SessionList subject = new SessionList(fakeSessionViewModelChanges);

        assertThat(subject.getSessionCount(), equalTo(2));
        assertThat(((Session) subject.getSession(1)).getName(), equalTo("John Doe"));
    }

}