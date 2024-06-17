package com.ThePinkAlliance.ChoreoExtended;

public class EventCommandData {
  private String type;
  private String name;

  public EventCommandData() {
  }

  public EventCommandData(String type, String name) {
    this.name = name;
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public String getName() {
    return this.name;
  }
}
