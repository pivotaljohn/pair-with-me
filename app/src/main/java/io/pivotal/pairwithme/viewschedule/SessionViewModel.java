package io.pivotal.pairwithme.viewschedule;

public class SessionViewModel implements ViewModel {
    private String name;
    private String time;
    private String description;

    public SessionViewModel(String name, String time, String description) {
        this.name = name;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
