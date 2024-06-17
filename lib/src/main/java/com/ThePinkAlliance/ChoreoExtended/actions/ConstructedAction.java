package com.ThePinkAlliance.ChoreoExtended.actions;

public class ConstructedAction {
  private double timestamp;
  private Action action;

  public ConstructedAction(String name, double timestamp, Action action) {
    this.timestamp = timestamp;
    this.action = action;
  }

  public double getTimestamp() {
    return this.timestamp;
  }

  public Action getAction() {
    return this.action;
  }
}
