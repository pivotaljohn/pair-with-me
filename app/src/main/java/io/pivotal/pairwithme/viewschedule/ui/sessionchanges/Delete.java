package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class Delete extends Change<Session> {
    public Delete(Session sessionToDelete) {
        super();
    }

    @Override
    public Session getTarget() {
        return null;
    }
}
