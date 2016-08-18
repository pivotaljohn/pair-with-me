package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import org.joda.time.DateTime;

import io.pivotal.pairwithme.domain.PairingSession;
import io.pivotal.pairwithme.domain.PairingSessionChange;
import io.pivotal.pairwithme.viewschedule.ui.model.Session;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class SessionChanges {
    private final Observable<PairingSessionChange> mPairingSessionChanges;
    private PublishSubject<SessionChange> subject;

    public SessionChanges(Observable<PairingSessionChange> pairingSessionChanges) {
        subject = PublishSubject.create();
        mPairingSessionChanges = pairingSessionChanges;
        mPairingSessionChanges.subscribe(new Action1<PairingSessionChange>() {
            @Override
            public void call(PairingSessionChange pairingSessionChange) {
                PairingSession pairingSession = pairingSessionChange.getTarget();
                subject.onNext(new SessionInsert(new Session(pairingSession.getId(), pairingSession.getFirstName() + " " + pairingSession.getLastName(), pairingSession.getStartTime(), pairingSession.getDescription())));
            }
        });
    }

    public Observable<SessionChange> asObservable() {
        return subject;
    }
}
