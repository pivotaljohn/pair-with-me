package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import io.pivotal.pairwithme.domain.PairingSession;
import io.pivotal.pairwithme.domain.PairingSessionInsert;
import io.pivotal.pairwithme.viewschedule.ui.model.Session;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import io.pivotal.pairwithme.domain.PairingSessionChange;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class SessionChangesTest {

    SessionChange changePublished;

    @Before
    public void setUp() {
       changePublished = null;
    }

    @Test
    public void whenPairingSessionIsInserted_publishesASessionInsertion() {
        PublishSubject<PairingSessionChange> pairingSessionChanges = PublishSubject.create();
        SessionChanges sessionChanges = new SessionChanges(pairingSessionChanges);
        sessionChanges.asObservable().subscribe(new Action1<SessionChange>() {
            @Override
            public void call(SessionChange sessionChange) {
                changePublished = sessionChange;
            }
        });

        pairingSessionChanges.onNext(new PairingSessionInsert(
                new PairingSession("1", "John", "Ryan", DateTime.parse("2010-02-22T13:22:56Z"), "Pair w/ me pl0x")));

        assertThat(changePublished, notNullValue());
        assertThat(changePublished, instanceOf(SessionInsert.class));

        Session publishedSession = changePublished.getTarget();
        assertThat(publishedSession.getId(), equalTo("1"));
        assertThat(publishedSession.getName(), equalTo("John Ryan"));
        assertThat(publishedSession.getDescription(), equalTo("Pair w/ me pl0x"));
        assertThat(publishedSession.getDateTime(), equalTo(DateTime.parse("2010-02-22T13:22:56Z")));
        assertThat(publishedSession.getTime(), equalTo("1:22p"));
    }
}
