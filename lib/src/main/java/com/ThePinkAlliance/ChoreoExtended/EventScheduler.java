package com.ThePinkAlliance.ChoreoExtended;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Deque;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.ThePinkAlliance.ChoreoExtended.actions.Action;
import com.ThePinkAlliance.ChoreoExtended.actions.ConstructedAction;

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
  private Deque<ConstructedAction> commandQueue;
  private HashMap<String, Double> timestamps;
  private HashMap<String, EventCommandData> markerMap;
  private List<EventMarker> markers;
  private List<Action> actions;

  public EventScheduler() {
    this.commandQueue = new ArrayDeque<>();
    this.timestamps = new HashMap<>();
    this.markerMap = new HashMap<>();
  }

  private ConstructedAction toConstructedAction(Action action, double timestamp) {
    return new ConstructedAction(action.getName(), timestamp, action);
  }

  public void loadEvents(List<EventMarker> eventMarkers, List<Action> actions) {
    Collections.sort(eventMarkers, Comparator.comparingDouble(e -> e.getTimestamp()));

    for (EventMarker marker : eventMarkers) {
      EventCommandData[] data = marker.getData();

      for (EventCommandData d : data) {
        markerMap.put(d.getName(), d);
        timestamps.put(d.getName(), marker.getTimestamp());
      }
    }

    for (Action action : actions) {
      String name = action.getName();
      double timestamp = timestamps.get(name);

      if (markerMap.containsKey(name) && timestamps.containsKey(name)) {
        ConstructedAction constructedAction = toConstructedAction(action, timestamp);

        commandQueue.add(constructedAction);
      }
    }

    this.actions = actions;
    this.markers = eventMarkers;
  }

  /**
   * This executes the queued actions in ascending order according to timestamp.
   * 
   * @param getTimestamp
   */
  public void run(DoubleSupplier getTimestamp) {
    if (actions == null || markers == null) {
      throw new Error("Please call EventScheduler.loadEvents");
    }

    ConstructedAction nextConstructedAction = commandQueue.peek();

    if (nextConstructedAction == null) {
      return;
    }

    Action nextAction = nextConstructedAction.getAction();

    double currentTime = getTimestamp.getAsDouble();
    double activationTime = nextConstructedAction.getTimestamp();
    boolean activate = (activationTime - currentTime) < 0.0125; // seconds

    System.out.println(nextConstructedAction.getTimestamp() - currentTime);

    if (activate && !nextAction.isComplete()) {
      nextAction.run();
    } else if (nextAction.isComplete()) {
      nextAction.cleanup();
      commandQueue.poll();
    }
  }
}
