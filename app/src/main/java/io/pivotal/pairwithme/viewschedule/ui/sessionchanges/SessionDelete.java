package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class SessionDelete extends SessionChange {
    private final String mSessionId;

    public SessionDelete(String sessionId) {
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
