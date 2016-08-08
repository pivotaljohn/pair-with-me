package io.pivotal.pairwithme.viewschedule.ui;

import java.util.ArrayList;
import java.util.List;

public class SessionList {
    private List<ViewModel> theList;

    public SessionList() {
        theList = new ArrayList<>();
        theList.add(new DateHeaderViewModel("July 4, 2016"));
        theList.add(new SessionViewModel("Billy Bob", "Jul 4 @ 13:00", "JavaScript"));
    }

    public int getSessionCount() {
        return theList.size();
    }

    public ViewModel getSession(final int position) {
        return null;
    }
}
