package com.ThePinkAlliance.ChoreoExtended;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Supplier;

import com.ThePinkAlliance.ChoreoExtended.actions.Action;
import edu.wpi.first.wpilibj.Timer;

/**
 * The Goal of the command scheduler is to execute commands along side normal
 * robot code. One Biggest issues that is library will solve is inconsistant
 * command execution during auto routines.
 * 
 * For example: When robot reaches 1m shoot at 4secs. We want the command exec
 * function to run at 4secs not init. For conststant execution we might need a
 * arbitrary exec duration for each command init function to ensure constistant
 * execution.
 * 
 * This would be along side some logic predicting clock times using a moving
 * average or something like that.
 */
public class EventScheduler {
    private PriorityQueue<Action> commandQueue;
    private List<EventMarker> markers;
    private List<Action> actions;

    public EventScheduler() {
        this.commandQueue = new PriorityQueue<>();
    }

    public void loadEvents(List<EventMarker> eventMarkers, List<Action> actions) {
        Collections.sort(eventMarkers, Comparator.comparingDouble(e -> e.getTimestamp()));

        // Check the order of events
        for (var t : eventMarkers) {
            System.out.println(t.getTimestamp());
        }

        this.actions = actions;
        this.markers = eventMarkers;
    }

    private void addElements(Action action) {
        this.commandQueue.add(action);
    }

    public void run(Supplier<Double> getTimestamp) {
        if (actions == null || markers == null) {
            throw new Error("Please call EventScheduler.loadEvents");
        }

        Action nextAction = commandQueue.peek();

        if (nextAction == null) {
            return;
        }

        double currentTime = getTimestamp.get();
        double activationTime = nextAction.getStartTime();
        boolean activate = (activationTime - currentTime) < 0.05; // seconds

        if (activate && !nextAction.isComplete()) {
            nextAction.run();
        } else if (nextAction.isComplete()) {
            nextAction.cleanup();
            commandQueue.poll();
        }
    }
}
