package io.pivotal.pairwithme.viewschedule.ui.model;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Change;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Insert;
import rx.Observable;
import rx.functions.Action1;

public class SessionList {
    private List<SessionListItem> theList;

    public SessionList(final Observable<Change<Session>> sessionViewModelChanges) {
        theList = new ArrayList<>();
        sessionViewModelChanges.subscribe(new Action1<Change<Session>>() {
            @Override
            public void call(final Change<Session> sessionViewModelChange) {
                if(sessionViewModelChange instanceof Insert) {
                    theList.add(new DateHeader("January 1, 2016"));
                    theList.add(sessionViewModelChange.getTarget());
                }
            }
        });
    }

    public int getSessionCount() {
        return theList.size();
    }

    public SessionListItem getSession(final int position) {
        return theList.get(position);
    }
}
