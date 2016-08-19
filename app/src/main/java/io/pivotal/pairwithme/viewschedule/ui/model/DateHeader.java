package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateHeader implements SessionListItem {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("MMMM d, yyyy");
    private final DateTime mDateTime;
    private final String date;

    public DateHeader(DateTime date) {
        this.date = DATE_FORMAT.print(date);
        mDateTime = date.withTimeAtStartOfDay();
    }

    public String getDate() {
        return date;
    }

    @Override
    public DateTime getDateTime() {
        return mDateTime;
    }
}
