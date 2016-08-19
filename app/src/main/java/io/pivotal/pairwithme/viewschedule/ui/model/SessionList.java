package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionChange;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionDelete;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionInsert;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionUpdate;
import rx.Observable;
import rx.functions.Action1;

public class SessionList {
    private List<SessionListItem> theList;

    public SessionList(final Observable<SessionChange> sessionChanges) {
        theList = new ArrayList<>();
        sessionChanges.subscribe(new SessionChangeSubscriber());
    }

    public int getItemCount() {
        return theList.size();
    }

    public SessionListItem getItem(final int position) {
        return theList.get(position);
    }

    private class SessionChangeSubscriber implements Action1<SessionChange> {

        @Override
        public void call(final SessionChange sessionChange) {
            if (sessionChange instanceof SessionInsert) {
                insertChange(sessionChange);
            } else if (sessionChange instanceof SessionDelete) {
                applyDelete((SessionDelete) sessionChange);
            } else if (sessionChange instanceof SessionUpdate) {
                applyUpdate((SessionUpdate) sessionChange);
            }
        }
    }

    private void applyUpdate(SessionUpdate sessionUpdate) {
        ListIterator<SessionListItem> items = theList.listIterator();
        boolean sessionFound = false;
        while (items.hasNext()) {
            SessionListItem currentItem = items.next();
            if (currentItem instanceof DateHeader) {
                currentItem = items.next();
            }
            Session currentSession = (Session) currentItem;
            if (currentSession.getId().equals(sessionUpdate.getTarget().getId())) {
                items.set(sessionUpdate.getTarget());
                sessionFound = true;
                break;
            }
        }
        if(!sessionFound) {
            insertChange(new SessionInsert(sessionUpdate.getTarget()));
        }
    }

    private void applyDelete(SessionDelete deletion) {
        ListIterator<SessionListItem> items = theList.listIterator();
        int sessionToDelete = -1;
        while (items.hasNext()) {
            SessionListItem currentItem = items.next();
            if (currentItem instanceof DateHeader) {
                currentItem = items.next();
            }
            Session currentSession = (Session) currentItem;
            if (currentSession.getId().equals(deletion.getSessionId())) {
                sessionToDelete = items.previousIndex();
                break;
            }
        }
        if (sessionToDelete != -1) {
            theList.remove(sessionToDelete);

            if (theList.get(sessionToDelete - 1) instanceof DateHeader) {
                if (theList.size() <= sessionToDelete ||
                        (theList.size() > sessionToDelete && theList.get(sessionToDelete) instanceof DateHeader)) {
                    theList.remove(sessionToDelete - 1);
                }
            }
        }
    }

    private void insertChange(SessionChange sessionChange) {
        Session newSession = sessionChange.getTarget();
        boolean isOnlySessionForDate = true;
        Interval newSessionDay = new Interval(newSession.getDateTime().withTimeAtStartOfDay(),
                newSession.getDateTime().plusDays(1).withTimeAtStartOfDay());

        int currentIndex = -1;
        ListIterator<SessionListItem> items = theList.listIterator();
        while (items.hasNext()) {
            currentIndex = items.nextIndex();
            SessionListItem currentItem = items.next();
            if (newSessionDay.contains(currentItem.getDateTime())) {
                isOnlySessionForDate = false;
            }
            if (currentItem.getDateTime().isAfter(newSession.getDateTime())) {
                break;
            }
        }
        if(!items.hasNext()) {
            currentIndex++;
        }

        if (isOnlySessionForDate) {
            theList.add(currentIndex++, new DateHeader(sessionChange.getTarget().getDateTime()));
        }
        theList.add(currentIndex, sessionChange.getTarget());
    }

    class TestHarness {
        public void setList(List<SessionListItem> initialList) {
            theList = initialList;
        }
    }
}
