package com.ThePinkAlliance.ChoreoExtended;

public class EventMarker {
    private final double timestamp;
    private final EventCommandData[] command;

    public EventMarker(double timestamp, EventCommandData[] command) {
        this.timestamp = timestamp;
        this.command = command;
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    public EventCommandData[] getData() {
        return this.command;
    }
}