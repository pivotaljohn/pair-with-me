package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Session implements SessionListItem {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("h:mma");

    private final String mId;
    private final String mName;
    private final String mTime;
    private final DateTime mDateTime;
    private final String mDescription;

    public Session(String id, String name, DateTime dateTime, String description) {
        mId = id;
        mName = name;
        mTime = DATE_FORMAT.print(dateTime).replace("AM", "a").replace("PM","p");
        mDateTime = dateTime;
        mDescription = description;
    }

    public String getId() {
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
