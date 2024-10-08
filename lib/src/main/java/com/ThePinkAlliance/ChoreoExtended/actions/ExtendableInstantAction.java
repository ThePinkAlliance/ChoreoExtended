package com.ThePinkAlliance.ChoreoExtended.actions;

public abstract class ExtendableInstantAction extends Action {
  public ExtendableInstantAction(String name) {
    super(name);
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
    exit();
  }

  @Override
  public void run() {
    if (!this.isComplete()) {
      execute();

      this.setComplete(true);
    }
  }

}
