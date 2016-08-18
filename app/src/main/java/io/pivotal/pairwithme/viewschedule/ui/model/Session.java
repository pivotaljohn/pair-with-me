package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Session implements SessionListItem {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("MMM d @ HH:mm");

    private final long mId;
    private final String mName;
    private final String mTime;
    private final DateTime mDateTime;
    private final String mDescription;

    public Session(long id, String name, DateTime dateTime, String description) {
        mId = id;
        mName = name;
        mTime = DATE_FORMAT.print(dateTime);
        mDateTime = dateTime;
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getTime() {
        return mTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public DateTime getDateTime() {
        return mDateTime;
    }
}
