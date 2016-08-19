package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

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
                applyInsert(sessionChange.getTarget());
            } else if (sessionChange instanceof SessionDelete) {
                applyDelete((SessionDelete) sessionChange);
            } else if (sessionChange instanceof SessionUpdate) {
                applyUpdate(sessionChange.getTarget());
            }
        }
    }

    private void applyUpdate(final Session updatedSession) {
        int updateIndex = indexOfItem(theList, new SearchCriteria() {
            public boolean isMatch(SessionListItem currentItem) {
                return currentItem instanceof Session && ((Session) currentItem).getId().equals(updatedSession.getId());
            }
        });
        if(updateIndex != -1) {
            theList.set(updateIndex, updatedSession);
        } else {
            applyInsert(updatedSession);
        }
    }

    private void applyDelete(final SessionDelete deletion) {
        int sessionToDelete = indexOfItem(theList, new SearchCriteria() {
            public boolean isMatch(SessionListItem currentItem) {
                return currentItem instanceof Session && ((Session) currentItem).getId().equals(deletion.getSessionId());
            }
        });

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

    private void applyInsert(final Session newSession) {
        int insertionIndex = indexOfItem(theList, new SearchCriteria() {
            @Override
            public boolean isMatch(SessionListItem currentItem) {
                return currentItem.getDateTime().isAfter(newSession.getDateTime());
            }
        });
        if(insertionIndex == -1) {
            insertionIndex = theList.size();
        }

        if (!containsAny(theList, new OnSameDayAs(newSession))) {
            theList.add(insertionIndex++, new DateHeader(newSession.getDateTime()));
        }
        theList.add(insertionIndex, newSession);
    }

    private interface SearchCriteria {
        boolean isMatch(SessionListItem currentItem);
    }

    private static class OnSameDayAs implements SearchCriteria {
        final Interval dayOfNewSession;
        private final Session mNewSession;

        public OnSameDayAs(Session newSession) {
            mNewSession = newSession;
            dayOfNewSession = new Interval(mNewSession.getDateTime().withTimeAtStartOfDay(),
                    mNewSession.getDateTime().plusDays(1).withTimeAtStartOfDay());
        }

        public boolean isMatch(SessionListItem currentItem) {
            return dayOfNewSession.contains(currentItem.getDateTime());
        }
    }

    private int indexOfItem(List<SessionListItem> list, SearchCriteria searchCriteria) {
        int currentIndex = 0;
        for (SessionListItem currentItem : list) {
            if (searchCriteria.isMatch(currentItem)) {
                break;
            }
            currentIndex++;
        }
        if(currentIndex == list.size()) {
            currentIndex = -1;
        }
        return currentIndex;
    }

    private boolean containsAny(List<SessionListItem> list, SearchCriteria searchCriteria) {
        return indexOfItem(list, searchCriteria) != -1;
    }

    class TestHarness {
        public void setList(List<SessionListItem> initialList) {
            theList = initialList;
        }
    }
}
