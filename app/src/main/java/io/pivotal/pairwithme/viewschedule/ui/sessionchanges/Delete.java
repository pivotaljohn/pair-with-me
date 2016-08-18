package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class Delete extends Change<Session> {
    private final String mSessionId;

    public Delete(String sessionId) {
        super();
        mSessionId = sessionId;
    }

    @Override
    public Session getTarget() {
        return null;
    }

    public String getSessionId() {
        return mSessionId;
    }
}
