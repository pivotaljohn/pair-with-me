package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class Delete extends Change<Session> {
    private final long mSessionId;

    public Delete(long sessionId) {
        super();
        mSessionId = sessionId;
    }

    @Override
    public Session getTarget() {
        return null;
    }

    public long getSessionId() {
        return mSessionId;
    }
}
