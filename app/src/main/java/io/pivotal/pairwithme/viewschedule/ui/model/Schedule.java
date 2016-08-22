package io.pivotal.pairwithme.viewschedule.ui.model;

import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionChange;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionDelete;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionInsert;
import io.pivotal.pairwithme.viewschedule.ui.sessionchanges.SessionUpdate;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import static io.pivotal.pairwithme.viewschedule.ui.model.ScheduleItemList.*;

public class Schedule {
    private ScheduleItemList theList;
    private PublishSubject<ScheduleChange> subject;

    public Schedule(final Observable<SessionChange> sessionChanges) {
        subject = PublishSubject.create();
        theList = new ScheduleItemList();
        sessionChanges.subscribe(new SessionChangeSubscriber());
    }

    public int getItemCount() {
        return theList.size();
    }

    public ScheduleItem getItem(final int position) {
        return theList.get(position);
    }

    public Observable<ScheduleChange> observeChangesByPosition() {
        return subject;
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

        private void applyUpdate(final Session updatedSession) {
            int updateIndex = theList.indexOfItem(new SearchCriteria() {
                public boolean isMatch(ScheduleItem currentItem) {
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
            int sessionToDelete = theList.indexOfItem(new SearchCriteria() {
                public boolean isMatch(ScheduleItem currentItem) {
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
            int insertionIndex = theList.indexOfItem(new SearchCriteria() {
                @Override
                public boolean isMatch(ScheduleItem currentItem) {
                    return currentItem.getDateTime().isAfter(newSession.getDateTime());
                }
            });
            if(insertionIndex == -1) {
                insertionIndex = theList.size();
            }

            if (!theList.containsAny(new OnSameDayAs(newSession))) {
                theList.add(insertionIndex, new DateHeader(newSession.getDateTime()));
                subject.onNext(new ScheduleChange(ScheduleChange.Type.INSERTED, insertionIndex));
                insertionIndex++;
            }
            theList.add(insertionIndex, newSession);
            subject.onNext(new ScheduleChange(ScheduleChange.Type.INSERTED, insertionIndex));
        }
    }


    class TestHarness {
        public void setList(ScheduleItemList initialList) {
            theList = initialList;
        }
    }
}
