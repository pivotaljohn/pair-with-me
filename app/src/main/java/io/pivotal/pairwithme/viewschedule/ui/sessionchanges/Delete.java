package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class Delete extends Change<Session> {
    public Delete(long sessionId) {
        super();
    }

    @Override
    public Session getTarget() {
        return null;
    }
}
