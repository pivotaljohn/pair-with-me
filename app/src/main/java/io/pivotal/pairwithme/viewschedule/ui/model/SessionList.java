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

            /*
               Concerns:
               [2] — whether to insert a DateHeader too.
               [3] — iterating over the list of current items
               [4] — the fact that the list is a heterogeneous list of sessions and headers
               [5] — determining where to perform the insert.
               [6] — doing the insert
            */
            if (sessionChange instanceof Insert) {
                Session newSession = sessionChange.getTarget();
                boolean isOnlySessionForDate = true;                                   // [2]
                Interval newSessionDay = new Interval(newSession.getDateTime().withTimeAtStartOfDay(),
                        newSession.getDateTime().plusDays(1).withTimeAtStartOfDay());  // [2]

                // Find position where to insert the new session...
                ListIterator<SessionListItem> items = theList.listIterator(); // [3]
                while (items.hasNext()) {                                     // [3]
                    SessionListItem currentItem = items.next();
                    if (currentItem instanceof DateHeader) {
                        currentItem = items.next();
                    }
                    Session currentSession = (Session) currentItem;        // [3,4]
                    if (newSessionDay.contains(currentSession.getDateTime())) {  // [2]
                        isOnlySessionForDate = false;
                    }
                    if (currentSession.getDateTime().isAfter(newSession.getDateTime())) { // [3, 5]
                        break;
                    }
                }
                int positionForNewDate = items.nextIndex(); // [5]

                if (isOnlySessionForDate) {  // [2]
                    theList.add(positionForNewDate++, new DateHeader(sessionChange.getTarget().getDateTime()));  // [4,5,6]
                }
                theList.add(positionForNewDate, sessionChange.getTarget()); // [6]
            }
        }

    }
}
