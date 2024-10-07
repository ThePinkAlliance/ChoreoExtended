package frc.robot.actions;

import com.ThePinkAlliance.ChoreoExtended.actions.ExtendableLoopingAction;

public class CustomAction extends ExtendableLoopingAction {

  public CustomAction() {
    // Connected to "custom-action" with duration of 1 second.
    super("custom-action", 1);
  }

  @Override
  public void execute() {
    // Execute motors and read sensors.
  }

  @Override
  public void exit() {
    // Stop motors and cleanup
  }

}
