package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Change;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Delete;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Insert;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.Update;
import rx.Observable;
import rx.functions.Action1;

public class SessionList {
    private List<SessionListItem> theList;

    public SessionList(final Observable<Change<Session>> sessionChanges) {
        theList = new ArrayList<>();
        sessionChanges.subscribe(new SessionChangeSubscriber());
    }

    public int getItemCount() {
        return theList.size();
    }

    public SessionListItem getItem(final int position) {
        return theList.get(position);
    }

    private class SessionChangeSubscriber implements Action1<Change<Session>> {

        @Override
        public void call(final Change<Session> sessionChange) {
            if (sessionChange instanceof Insert) {
                insertChange(sessionChange);
            } else if (sessionChange instanceof Delete) {
                applyDelete((Delete) sessionChange);
            } else if (sessionChange instanceof Update) {
                applyUpdate((Update) sessionChange);
            }
        }
    }

    private void applyUpdate(Update update) {
        ListIterator<SessionListItem> items = theList.listIterator();
        boolean sessionFound = false;
        while (items.hasNext()) {
            SessionListItem currentItem = items.next();
            if (currentItem instanceof DateHeader) {
                currentItem = items.next();
            }
            Session currentSession = (Session) currentItem;
            if (currentSession.getId() == update.getTarget().getId()) {
                items.set(update.getTarget());
                sessionFound = true;
                break;
            }
        }
        if(!sessionFound) {
            insertChange(new Insert(update.getTarget()));
        }
    }

    private void applyDelete(Delete deletion) {
        ListIterator<SessionListItem> items = theList.listIterator();
        int sessionToDelete = -1;
        while (items.hasNext()) {
            SessionListItem currentItem = items.next();
            if (currentItem instanceof DateHeader) {
                currentItem = items.next();
            }
            Session currentSession = (Session) currentItem;
            if (currentSession.getId() == deletion.getSessionId()) {
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

    private void insertChange(Change<Session> sessionChange) {
        Session newSession = sessionChange.getTarget();
        boolean isOnlySessionForDate = true;
        Interval newSessionDay = new Interval(newSession.getDateTime().withTimeAtStartOfDay(),
                newSession.getDateTime().plusDays(1).withTimeAtStartOfDay());

        ListIterator<SessionListItem> items = theList.listIterator();
        while (items.hasNext()) {
            SessionListItem currentItem = items.next();
            if (currentItem instanceof DateHeader) {
                currentItem = items.next();
            }
            Session currentSession = (Session) currentItem;
            if (newSessionDay.contains(currentSession.getDateTime())) {
                isOnlySessionForDate = false;
            }
            if (currentSession.getDateTime().isAfter(newSession.getDateTime())) {
                break;
            }
        }
        int positionForNewDate = items.nextIndex();

        if (isOnlySessionForDate) {
            theList.add(positionForNewDate++, new DateHeader(sessionChange.getTarget().getDateTime()));
        }
        theList.add(positionForNewDate, sessionChange.getTarget());
    }

    class TestHarness {
        public void setList(List<SessionListItem> initialList) {
            theList = initialList;
        }
    }
}
