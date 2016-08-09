package io.pivotal.pairwithme.viewschedule.adapters;

import io.pivotal.pairwithme.viewschedule.ui.SessionViewModel;

public class Insert extends Change<SessionViewModel> {

    private final SessionViewModel mTarget;

    public Insert(final SessionViewModel target) {
        mTarget = target;
    }

    @Override
    public SessionViewModel getTarget() {
        return mTarget;
    }
}
