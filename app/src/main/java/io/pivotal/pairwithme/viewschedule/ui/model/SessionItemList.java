package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.Interval;

import java.util.LinkedList;

class SessionItemList extends LinkedList<SessionListItem> {
    int indexOfItem(SearchCriteria searchCriteria) {
        int currentIndex = 0;
        for (SessionListItem currentItem : this) {
            if (searchCriteria.isMatch(currentItem)) {
                break;
            }
            currentIndex++;
        }
        if(currentIndex == this.size()) {
            currentIndex = -1;
        }
        return currentIndex;
    }

    boolean containsAny(SearchCriteria searchCriteria) {
        return indexOfItem(searchCriteria) != -1;
    }

    interface SearchCriteria {
        boolean isMatch(SessionListItem currentItem);
    }

    static class OnSameDayAs implements SearchCriteria {
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
}
