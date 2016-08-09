package io.pivotal.pairwithme.viewschedule.adapters;

import io.pivotal.pairwithme.viewschedule.ui.SessionViewModel;
import rx.Observable;
import rx.subjects.PublishSubject;

public class PairingSessionViewModelChanges {
    private PublishSubject<Change<SessionViewModel>> subject;

    public PairingSessionViewModelChanges() {
        subject = PublishSubject.create();
    }

    public Observable<Change<SessionViewModel>> asObservable() {
        return subject;
    }

    public void publish() {
        subject.onNext(new Insert(new SessionViewModel("John Doe", "Jan 1 @ 13:01", "JavaScript")));
    }

}
