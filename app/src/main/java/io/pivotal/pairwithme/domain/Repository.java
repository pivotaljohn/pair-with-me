package io.pivotal.pairwithme.domain;

import rx.Observable;

public interface Repository {
    Observable<PairingSessionChange> asObservable();
}
