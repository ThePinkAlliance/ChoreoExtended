package com.ThePinkAlliance.ChoreoExtended.actions;

public abstract class Action {
  private boolean isComplete;
  private String name;

  public Action(String name) {
    this.isComplete = false;
    this.name = name;
  }

  public abstract void run();

  public abstract void cleanup();

  public String getName() {
    return this.name;
  }

  public void setComplete(boolean complete) {
    this.isComplete = complete;
  }

  public boolean isComplete() {
    return this.isComplete;
  }
}
