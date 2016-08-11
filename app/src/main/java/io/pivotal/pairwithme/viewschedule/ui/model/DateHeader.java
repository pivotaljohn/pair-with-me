package io.pivotal.pairwithme.viewschedule.ui.model;

public class DateHeader implements SessionListItem {
    private String date;

    public DateHeader(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
