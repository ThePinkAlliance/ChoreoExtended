package com.ThePinkAlliance.ChoreoExtended.actions;

public class InstantAction extends Action {
    private Runnable runnable;
    private double startTime;

    public InstantAction(Runnable runnable, double startTime) {
        this.runnable = runnable;
        this.startTime = startTime;
    }

    @Override
    public double getStartTime() {
        return startTime;
    }

    @Override
    public double getEndTime() {
        return startTime;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void run() {
        if (!this.isComplete()) {
            runnable.run();

            setComplete(true);
        }
    }
}
