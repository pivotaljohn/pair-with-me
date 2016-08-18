package io.pivotal.pairwithme.domain;

import org.joda.time.DateTime;

public class PairingSession {
    private final String mId;
    private final String mFirstName;
    private final String mLastName;
    private final DateTime mStartTime;
    private final String mDescription;

    public PairingSession(String id, String firstName, String lastName, DateTime startTime, String description) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mStartTime = startTime;
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public DateTime getStartTime() {
        return mStartTime;
    }

    public String getDescription() {
        return mDescription;
    }
}
