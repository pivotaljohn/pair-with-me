package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class SessionUpdate extends SessionChange {
    private final Session mUpdatedSession;

    public SessionUpdate(Session updatedSession) {
        mUpdatedSession = updatedSession;
    }

    public Session getTarget() {
        return mUpdatedSession;
    }
}
