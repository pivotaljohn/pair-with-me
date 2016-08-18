package io.pivotal.pairwithme.domain;

import org.joda.time.DateTime;

import rx.Observable;
import rx.subjects.PublishSubject;

public class PairingSessionChanges {
    private PublishSubject<PairingSessionChange> subject;

    public PairingSessionChanges() {
        subject = PublishSubject.create();
    }

    public Observable<PairingSessionChange> asObservable() {
        return subject;
    }

    public void publish() {
        subject.onNext(new PairingSessionInsert(
                new PairingSession("1", "Tommy", "Orr", DateTime.parse("2016-02-01T13:01:00Z"), "JavaScript")));
    }


}
