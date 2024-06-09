package com.ThePinkAlliance.ChoreoExtended.actions;

import edu.wpi.first.wpilibj.Timer;

public class LoopingAction extends Action {
    private Runnable runnable;
    private double startTime;
    private double endTime;

    public LoopingAction(Runnable runnable, double startTime, double duration) {
        this.runnable = runnable;
        this.startTime = startTime;
        this.endTime = startTime + duration;
    }

    @Override
    public double getStartTime() {
        return startTime;
    }

    @Override
    public double getEndTime() {
        return endTime;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void run() {
        double currentTime = Timer.getFPGATimestamp();
        boolean expired = currentTime >= endTime;

        if (!this.isComplete() && !expired) {
            runnable.run();
        } else {
            setComplete(true);
        }
    }
}
