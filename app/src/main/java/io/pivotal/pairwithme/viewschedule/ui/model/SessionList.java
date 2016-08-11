package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Change;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Insert;
import rx.Observable;
import rx.functions.Action1;

public class SessionList {
    private List<SessionListItem> theList;

    public SessionList(final Observable<Change<Session>> sessionChanges) {
        theList = new ArrayList<>();
        sessionChanges.subscribe(new SessionChangeSubscriber());
    }

    public int getSessionCount() {
        return theList.size();
    }

    public SessionListItem getItem(final int position) {
        return theList.get(position);
    }

    class TestHarness {
        public void setList(List<SessionListItem> initialList) {
            theList = initialList;
        }
    }

    private class SessionChangeSubscriber implements Action1<Change<Session>> {
        @Override
        public void call(final Change<Session> sessionChange) {
            if (sessionChange instanceof Insert) {
                Session newSession = sessionChange.getTarget();
                boolean isOnlySessionForDate = true;
                Interval newSessionDay = new Interval(newSession.getDateTime().withTimeAtStartOfDay(),
                        newSession.getDateTime().plusDays(1).withTimeAtStartOfDay());
                ListIterator<SessionListItem> items = theList.listIterator();
                while(items.hasNext()) {
                    Session currentSession = skipToNextSession(items);
                    if(newSessionDay.contains(currentSession.getDateTime())) {
                        isOnlySessionForDate = false;
                    }
                    if(currentSession.getDateTime().isAfter(newSession.getDateTime())) {
                        break;
                    }
                }
                int positionForNewDate = items.nextIndex();
                if(isOnlySessionForDate) {
                    theList.add(positionForNewDate++, new DateHeader(sessionChange.getTarget().getDateTime()));
                }
                theList.add(positionForNewDate, sessionChange.getTarget());
            }
        }

        private Session skipToNextSession(ListIterator<SessionListItem> items) {
            SessionListItem currentItem = items.next();
            if(currentItem instanceof DateHeader) {
                currentItem = items.next();
            }
            return (Session) currentItem;
        }
    }
}
