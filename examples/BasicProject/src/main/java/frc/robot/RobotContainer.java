// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import com.ThePinkAlliance.ChoreoExtended.ChoreoExtended;
import com.ThePinkAlliance.ChoreoExtended.ChoreoTrajectory;
import com.ThePinkAlliance.ChoreoExtended.EventScheduler;
import com.ThePinkAlliance.ChoreoExtended.actions.InstantAction;
import com.ThePinkAlliance.ChoreoExtended.actions.LoopingAction;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer {
  SwerveSubsystem swerveSubsystem;
  EventScheduler scheduler;

  public RobotContainer() {
    this.scheduler = new EventScheduler();
    this.swerveSubsystem = new SwerveSubsystem(Constants.DriveConstants.kDriveKinematics);

    configureBindings();
  }

  private void configureBindings() {
  }

  public Command getAutonomousCommand() {
    ChoreoTrajectory traj = ChoreoExtended.getTrajectory("OneEvent");

    var action_one = new InstantAction(() -> {
      System.out.println("Hello!!");
    }, "test");

    var looping_action = new LoopingAction(() -> {
      System.out.println("Looping Action!!");
    }, 1, "action-one");

    scheduler.loadEvents(traj.getEvents(), List.of(action_one));

    return ChoreoExtended.choreoSwerveCommand(traj, swerveSubsystem::getCurrentPose,
        ChoreoExtended.choreoSwerveController(new PIDController(0, 0, 0), new PIDController(0, 0, 0),
            new PIDController(0, 0, 0)),
        swerveSubsystem::setStates, () -> false, scheduler::run, swerveSubsystem);
  }
}
