package com.ThePinkAlliance.ChoreoExtended;

import java.util.List;

public class EventCommandData {
    private List<IndividualEventCommand> commands;
    private String name;

    public EventCommandData() {
    }

    public String getName() {
        return this.name;
    }

    public List<IndividualEventCommand> getCommands() {
        return this.commands;
    }
}
