// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.subsystems.modules.WPI_SwerveModule;

public class SwerveSubsystem extends SubsystemBase {

  public SwerveModule frontLeftModule;
  public SwerveModule frontRightModule;
  public SwerveModule backLeftModule;
  public SwerveModule backRightModule;

  private SwerveDriveKinematics kinematics;
  private SwerveDrivePoseEstimator estimator;

  private Pigeon2 gyro;
  private Field2d field2d;
  private DataLog log;
  private DoubleLogEntry xLogEntry;
  private DoubleLogEntry yLogEntry;
  private SwerveModule[] modules;
  private SwerveModulePosition[] lastModulePositionsMeters;
  private Rotation2d lastGyroYaw;

  private double lastEpoch = 0;
  private double lastAngularPos = 0;

  /**
   * Creates a Swerve subsystem with the added kinematics.
   * 
   * @param kinematics
   */
  public SwerveSubsystem(SwerveDriveKinematics kinematics) {
    DataLogManager.start();

    log = DataLogManager.getLog();

    xLogEntry = new DoubleLogEntry(log, "/dt/xPos");
    yLogEntry = new DoubleLogEntry(log, "/dt/yPos");

    this.gyro = new Pigeon2(0, "base");
    this.field2d = new Field2d();

    this.frontRightModule = new WPI_SwerveModule(DriveConstants.kFrontRightTurningMotorPort,
        DriveConstants.kFrontRightDriveMotorPort, DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
        DriveConstants.kFrontRightDriveEncoderReversed, DriveConstants.kFrontRightTurningReversed,
        DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad, ModuleConstants.kFrontRightSteerGains, "base");

    this.frontLeftModule = new WPI_SwerveModule(DriveConstants.kFrontLeftTurningMotorPort,
        DriveConstants.kFrontLeftDriveMotorPort, DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
        DriveConstants.kFrontLeftDriveEncoderReversed, DriveConstants.kFrontLeftTurningReversed,
        DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad, ModuleConstants.kFrontLeftSteerGains, "base");

    this.backRightModule = new WPI_SwerveModule(DriveConstants.kBackRightTurningMotorPort,
        DriveConstants.kBackRightDriveMotorPort, DriveConstants.kBackRightDriveAbsoluteEncoderPort,
        DriveConstants.kBackRightDriveEncoderReversed, DriveConstants.kBackRightTurningReversed,
        DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad, ModuleConstants.kBackRightSteerGains, "base");

    this.backLeftModule = new WPI_SwerveModule(DriveConstants.kBackLeftTurningMotorPort,
        DriveConstants.kBackLeftDriveMotorPort, DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
        DriveConstants.kBackLeftDriveEncoderReversed, DriveConstants.kBackLeftTurningReversed,
        DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad, ModuleConstants.kBackLeftSteerGains, "base");

    this.kinematics = kinematics;

    this.estimator = new SwerveDrivePoseEstimator(
        kinematics, getRotation(), new SwerveModulePosition[] { frontRightModule.getPosition(),
            frontLeftModule.getPosition(), backRightModule.getPosition(),
            backLeftModule
                .getPosition() },
        new Pose2d(0, 0, new Rotation2d()), VecBuilder.fill(0.0, 0.0, 0.0),
        VecBuilder.fill(0.9, 0.9, 0.9));
    this.modules = new SwerveModule[] { frontRightModule, frontLeftModule, backRightModule, backLeftModule };
    this.lastModulePositionsMeters = getPositions();

    SmartDashboard.putData("Field", field2d);

    calibrateGyro();
  }

  public SwerveModulePosition[] getPositions() {
    return new SwerveModulePosition[] {
        frontRightModule.getPosition(),
        frontLeftModule.getPosition(),
        backRightModule.getPosition(),
        backLeftModule.getPosition()
    };
  }

  public SwerveModuleState[] getStates() {
    return new SwerveModuleState[] {
        frontRightModule.getState(),
        frontLeftModule.getState(),
        backRightModule.getState(),
        backLeftModule.getState()
    };
  }

  public StatusSignal<Double> getAccelX() {
    return gyro.getAccelerationX();
  }

  public StatusSignal<Double> verticalAccel() {
    return gyro.getAccelerationY();
  }

  public Rotation2d getRotation2d() {
    return gyro.getRotation2d();
  }

  public double getHeading() {
    return Math.IEEEremainder(gyro.getAngle() * -1, 360);
  }

  public double getYaw() {
    return gyro.getYaw().getValueAsDouble();
  }

  public Rotation2d getRotation() {
    return Rotation2d.fromDegrees(getHeading());
  }

  public void calibrateGyro() {
  }

  public void setGyro(double angle) {
    this.gyro.setYaw(angle);
  }

  public Field2d getField2d() {
    return field2d;
  }

  public void resetGyro() {
    this.gyro.setYaw(0);
  }

  private Twist2d scaleTwist2d(Twist2d twist2d, double factor) {
    return new Twist2d(twist2d.dx * factor, twist2d.dy * factor, twist2d.dtheta * factor);
  }

  public void setStates(ChassisSpeeds speeds) {
    // Looper is how far into the future are we looking
    double looper = .01;

    speeds.omegaRadiansPerSecond = speeds.omegaRadiansPerSecond * -1;
    speeds.vxMetersPerSecond = speeds.vxMetersPerSecond * -1;
    speeds.vyMetersPerSecond = speeds.vyMetersPerSecond * -1;

    /*
     * Check the angular drift with this solution & if I doesn't work explore the
     * possiblity of steer error in swerve pods.
     * 
     * NOTE: This can be broken down to smoothen robot driving. Like removing the
     * looper and etc.
     */
    double gyro_update_rate = gyro.getRate();
    Pose2d currentPose = getCurrentPose();
    Pose2d desired = new Pose2d(currentPose.getX() + (speeds.vxMetersPerSecond *
        looper),
        currentPose.getY() + (speeds.vyMetersPerSecond * looper),
        currentPose.getRotation().plus(Rotation2d.fromRadians(speeds.omegaRadiansPerSecond)));

    Twist2d twist_vel = scaleTwist2d(currentPose.log(desired), 1);
    ChassisSpeeds updated_speeds = new ChassisSpeeds(twist_vel.dx / looper,
        twist_vel.dy / looper,
        twist_vel.dtheta);

    SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

    frontRightModule.setDesiredState(states[3]);
    frontLeftModule.setDesiredState(states[2]);
    backRightModule.setDesiredState(states[1]);
    backLeftModule.setDesiredState(states[0]);

    field2d.setRobotPose(getCurrentPose());
  }

  public void setSpeedModules(double speed) {
    SwerveModuleState state = new SwerveModuleState(speed, new Rotation2d());

    this.frontRightModule.setDesiredState(state);
    this.frontLeftModule.setDesiredState(state);
    this.backRightModule.setDesiredState(state);
    this.backLeftModule.setDesiredState(state);
  }

  public void resetPose(Pose2d pose2d) {
    estimator.resetPosition(getRotation(), getPositions(), pose2d);
  }

  public ChassisSpeeds getSpeeds() {
    return kinematics.toChassisSpeeds(getStates());
  }

  public Pose2d getCurrentPose() {
    return estimator.getEstimatedPosition();
  }

  public Pose2d getDifferentPose() {
    return new Pose2d(getCurrentPose().getX(), getCurrentPose().getY(), getRotation2d());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (lastEpoch != 0) {
      double currentAngularPos = gyro.getAngle();
      lastAngularPos = currentAngularPos;
    }

    Pose2d pose = getCurrentPose();

    if (pose.getX() != 0 && pose.getY() != 0) {
      xLogEntry.append(getCurrentPose().getX());
      yLogEntry.append(getCurrentPose().getY());
    }

    field2d.setRobotPose(getCurrentPose());
    estimator.update(getRotation(), getPositions());

    lastEpoch = Timer.getFPGATimestamp();
  }
}
