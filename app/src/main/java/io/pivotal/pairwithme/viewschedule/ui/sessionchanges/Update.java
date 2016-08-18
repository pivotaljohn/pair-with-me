package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class Update extends Change<Session> {
    private final Session mUpdatedSession;

    public Update(Session updatedSession) {
        mUpdatedSession = updatedSession;
    }

    public Session getTarget() {
        return mUpdatedSession;
    }
}
