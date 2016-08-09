package io.pivotal.pairwithme.viewschedule.ui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class SessionViewModel implements ViewModel {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("MMM d @ HH:mm");

    private final String mName;
    private final String mTime;
    private final DateTime mDateTime;
    private final String mDescription;

    public SessionViewModel(String name, DateTime dateTime, String description) {
        mName = name;
        mTime = DATE_FORMAT.print(dateTime);
        mDateTime = dateTime;
        mDescription = description;
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
