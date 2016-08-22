package io.pivotal.pairwithme.viewschedule.ui.model;

public class ScheduleChange {
    public enum Type {
        INSERTED,
        CHANGED,
        MOVED,
        REMOVED
    }

    private final Type mType;
    private final int mPosition;

    public ScheduleChange(Type type, int position) {
        mType = type;
        mPosition = position;
    }

    public Type getType() {
        return mType;
    }

    public int getPosition() {
        return mPosition;
    }
}
