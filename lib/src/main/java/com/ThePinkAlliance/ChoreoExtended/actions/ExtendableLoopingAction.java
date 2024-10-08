package com.ThePinkAlliance.ChoreoExtended.actions;

import edu.wpi.first.wpilibj.Timer;

public abstract class ExtendableLoopingAction extends Action {
  private double startTime;
  private double duration;

  public ExtendableLoopingAction(String name, double duration) {
    super(name);

    this.startTime = -1;
    this.duration = duration;
  }

  /**
   * This executes repeadly during the action duration.
   */
  public abstract void execute();

  /**
   * This is the cleanup function that will execute when the action is complete.
   */
  public abstract void exit();

  @Override
  public void cleanup() {
    this.startTime = -1;

    exit();
  }

  @Override
  public void run() {
    if (startTime == -1) {
      this.startTime = Timer.getFPGATimestamp();
    }

    double currentTime = Timer.getFPGATimestamp() - this.startTime;
    boolean expired = currentTime >= this.duration;

    if (!this.isComplete() && !expired) {
      execute();
    } else {
      setComplete(true);
    }
  }

}
