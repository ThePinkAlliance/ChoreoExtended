package com.ThePinkAlliance.ChoreoExtended.actions;

public abstract class Action {
    private boolean isComplete;

    public Action() {
        this.isComplete = false;
    }

    public abstract double getStartTime();

    public abstract double getEndTime();

    public abstract void run();

    public abstract void cleanup();

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    public boolean isComplete() {
        return this.isComplete;
    }
}
