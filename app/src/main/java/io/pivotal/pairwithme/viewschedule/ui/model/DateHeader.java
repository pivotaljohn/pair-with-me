package io.pivotal.pairwithme.viewschedule.ui.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateHeader implements SessionListItem {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("MMMM d, yyyy");
    private String date;

    public DateHeader(DateTime date) {
        this.date = DATE_FORMAT.print(date);
    }

    public DateHeader(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
