package com.ThePinkAlliance.ChoreoExtended.actions;

import edu.wpi.first.wpilibj.Timer;

public class LoopingAction extends Action {
  private Runnable runnable;
  private double startTime;
  private double duration;

  public LoopingAction(String name, double duration, Runnable runnable) {
    super(name);

    this.runnable = runnable;
    this.duration = duration;

    this.startTime = -1;
  }

  @Override
  public void cleanup() {
    this.startTime = -1;
  }

  @Override
  public void run() {
    if (startTime == -1) {
      this.startTime = Timer.getFPGATimestamp();
    }

    double currentTime = Timer.getFPGATimestamp() - this.startTime;
    boolean expired = currentTime >= this.duration;

    if (!this.isComplete() && !expired) {
      runnable.run();
    } else {
      setComplete(true);
    }
  }
}
