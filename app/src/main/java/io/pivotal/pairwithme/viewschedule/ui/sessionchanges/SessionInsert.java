package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class SessionInsert extends SessionChange {

    private final Session mTarget;

    public SessionInsert(final Session target) {
        mTarget = target;
    }

    @Override
    public Session getTarget() {
        return mTarget;
    }
}
