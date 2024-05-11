package com.ThePinkAlliance.ChoreoExtended;

public class IndividualEventCommand {
    private final String type;
    private final String name;

    public IndividualEventCommand(String type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
