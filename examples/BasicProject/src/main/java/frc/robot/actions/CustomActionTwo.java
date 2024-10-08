package frc.robot.actions;

import com.ThePinkAlliance.ChoreoExtended.actions.ExtendableInstantAction;

public class CustomActionTwo extends ExtendableInstantAction {

  public CustomActionTwo() {
    super("Action-one");
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
