package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import org.joda.time.DateTime;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;
import rx.Observable;
import rx.subjects.PublishSubject;

public class SessionChanges {
    private PublishSubject<Change<Session>> subject;

    public SessionChanges() {
        subject = PublishSubject.create();
    }

    public Observable<Change<Session>> asObservable() {
        return subject;
    }

    public void publish() {
        subject.onNext(new Insert(new Session("John Doe", DateTime.parse("2016-02-01T13:01:00Z"), "JavaScript")));
    }

}
