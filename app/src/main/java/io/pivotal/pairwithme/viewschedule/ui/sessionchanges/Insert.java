package io.pivotal.pairwithme.viewschedule.ui.sessionchanges;

import io.pivotal.pairwithme.viewschedule.ui.model.Session;

public class Insert extends Change<Session> {

    private final Session mTarget;

    public Insert(final Session target) {
        mTarget = target;
    }

    @Override
    public Session getTarget() {
        return mTarget;
    }
}
