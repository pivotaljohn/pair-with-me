package io.pivotal.pairwithme.viewschedule.ui;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import io.pivotal.pairwithme.viewschedule.adapters.Change;
import io.pivotal.pairwithme.viewschedule.adapters.Insert;
import rx.Observable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionListTest {

    @Test
    public void whenSessionViewModelInserted_includesThatViewModelInList() {
        List<Change<SessionViewModel>> changes = new LinkedList<>();
        changes.add(new Insert(new SessionViewModel("John Doe", "Jan 1 @ 13:01", "JavaScript")));
        Observable<Change<SessionViewModel>> fakeSessionViewModelChanges = Observable.from(changes);
        SessionList subject = new SessionList(fakeSessionViewModelChanges);

        assertThat(subject.getSessionCount(), equalTo(1));
        assertThat(((SessionViewModel) subject.getSession(0)).getName(), equalTo("John Doe"));
    }


}