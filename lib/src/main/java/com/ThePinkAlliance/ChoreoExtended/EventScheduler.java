package com.ThePinkAlliance.ChoreoExtended;

import java.util.PriorityQueue;
import edu.wpi.first.wpilibj2.command.Command;

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
    PriorityQueue<Command> commandQueue = new PriorityQueue<>();

    public EventScheduler() {

    }

    private void addElements() {

    }

    public void run() {

    }
}
