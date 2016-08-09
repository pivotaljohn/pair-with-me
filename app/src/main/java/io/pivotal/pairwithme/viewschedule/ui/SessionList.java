package io.pivotal.pairwithme.viewschedule.ui;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.pairwithme.viewschedule.adapters.Change;
import io.pivotal.pairwithme.viewschedule.adapters.Insert;
import rx.Observable;
import rx.functions.Action1;

public class SessionList {
    private List<ViewModel> theList;

    public SessionList(final Observable<Change<SessionViewModel>> sessionViewModelChanges) {
        theList = new ArrayList<>();
        sessionViewModelChanges.subscribe(new Action1<Change<SessionViewModel>>() {
            @Override
            public void call(final Change<SessionViewModel> sessionViewModelChange) {
                if(sessionViewModelChange instanceof Insert) {
                    theList.add(sessionViewModelChange.getTarget());
                }
            }
        });
    }

    public int getSessionCount() {
        return theList.size();
    }

    public ViewModel getSession(final int position) {
        return theList.get(position);
    }
}
