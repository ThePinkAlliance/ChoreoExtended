package com.ThePinkAlliance.ChoreoExtended.actions;

public class InstantAction extends Action {
  private Runnable runnable;

  public InstantAction(Runnable runnable, String name) {
    super(name);

    this.runnable = runnable;
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
