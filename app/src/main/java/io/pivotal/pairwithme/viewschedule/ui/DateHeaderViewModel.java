package io.pivotal.pairwithme.viewschedule.ui;

public class DateHeaderViewModel implements ViewModel {
    private String date;

    public DateHeaderViewModel(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
