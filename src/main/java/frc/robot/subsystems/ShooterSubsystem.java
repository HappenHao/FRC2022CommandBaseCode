// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {
  /***** MOTOR **************************/
  public WPI_TalonFX m_shooter_left_falcon = new WPI_TalonFX(ShooterConstant.left_shooter);
  public WPI_TalonFX m_shooter_right_falcon = new WPI_TalonFX(ShooterConstant.right_shooter);

  public CANSparkMax m_launch = new CANSparkMax(ShooterConstant.launch, MotorType.kBrushless);
  public CANSparkMax m_rotate = new CANSparkMax(ShooterConstant.rotate, MotorType.kBrushless);
  public CANSparkMax m_elevation = new CANSparkMax(ShooterConstant.elevation, MotorType.kBrushless);

  /** LIMIT SWITCH ***********************/
  DigitalInput switch_elevation = new DigitalInput(SensorConstant.pitch_digital);
  DigitalInput switch_rotation = new DigitalInput(SensorConstant.rotate_digital);
  DigitalInput switch_tunnel = new DigitalInput(SensorConstant.shootExit_digital);

  /*** Encoder ***************************/
  RelativeEncoder rotate = m_rotate.getEncoder();
  RelativeEncoder elevate = m_elevation.getEncoder();

  /*** PHOTON VISION ********************/
  PhotonCamera m_camera = new PhotonCamera(PhotonVisionConstant.Shooter_Cam);

  /**** PID CONTROLLER **********************/
  PIDController m_rotateVersionpid = new PIDController(PhotonVisionConstant.kGains_rotateVersion.kP,
      PhotonVisionConstant.kGains_rotateVersion.kI, PhotonVisionConstant.kGains_rotateVersion.kD);

  /**** SHUFFLEBOARD & NETWORKTABLE ***********/
  ShuffleboardTab ShooterTab = Shuffleboard.getTab("ShooterTab");

  public NetworkTableEntry ShooterSpeed = ShooterTab.add("Shooter Speed", 0).withWidget(BuiltInWidgets.kGraph)
  .withProperties(Map.of("min", 0, "max", 10000))   
  .withSize(3,3) 
  .getEntry();

  public NetworkTableEntry TargetSpeed = ShooterTab.add("TargetSpeed", 0).getEntry();
  public NetworkTableEntry RotateEncoder = ShooterTab.add("RotateEncoder", 0).getEntry();
  public NetworkTableEntry ElevateEncoder = ShooterTab.add("ElevateEncoder", 0).getEntry();

  /******************************************************************
   * ShooterSubsystem
   *******************************************************************/
  public ShooterSubsystem() {

    m_shooter_left_falcon.configFactoryDefault();
    m_shooter_right_falcon.configFactoryDefault();

    m_shooter_left_falcon.setNeutralMode(NeutralMode.Coast);
    m_shooter_right_falcon.setNeutralMode(NeutralMode.Coast);

    m_shooter_right_falcon.follow(m_shooter_left_falcon);
    m_shooter_right_falcon.setInverted(InvertType.InvertMotorOutput);

    m_launch.setIdleMode(IdleMode.kBrake);
    m_rotate.setIdleMode(IdleMode.kBrake);
    m_elevation.setIdleMode(IdleMode.kBrake);
    rotate.setPosition(ShooterConstant.rotate_init_angle);
    elevate.setPosition(ShooterConstant.elevation_init_angle);

    /***** SHOOTER_PID ********************************************/
    m_shooter_left_falcon.configNeutralDeadband(0.001);
    m_shooter_left_falcon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
        ShooterConstant.kPIDLoopIdx, ShooterConstant.kTimeoutMs);
    m_shooter_left_falcon.config_kF(ShooterConstant.kPIDLoopIdx, ShooterConstant.kGains_Velocit.kF,
        ShooterConstant.kTimeoutMs);
    m_shooter_left_falcon.config_kP(ShooterConstant.kPIDLoopIdx, ShooterConstant.kGains_Velocit.kP,
        ShooterConstant.kTimeoutMs);
    m_shooter_left_falcon.config_kI(ShooterConstant.kPIDLoopIdx, ShooterConstant.kGains_Velocit.kI,
        ShooterConstant.kTimeoutMs);
    m_shooter_left_falcon.config_kD(ShooterConstant.kPIDLoopIdx, ShooterConstant.kGains_Velocit.kD,
        ShooterConstant.kTimeoutMs);
  }

  /******************************************************************
   * periodic
   *******************************************************************/
  @Override
  public void periodic() {

    ShooterSpeed.setDouble(RawSensorUnittoRPM(m_shooter_left_falcon.getSelectedSensorVelocity()));
    ElevateEncoder.setDouble(elevate.getPosition());
    RotateEncoder.setDouble(rotate.getPosition());

    SmartDashboard.putBoolean("elevation_Limit", switch_elevation.get());
    SmartDashboard.putBoolean("Rotation_Limit", switch_rotation.get());
    SmartDashboard.putBoolean("Lounch_Switch", switch_tunnel.get());

    // m_shooter_left_falcon.getPIDConfigs(TalonFXPIDSetConfiguration, 0, 0);
    resteEncoder();
  }

  /******************************************************************
   * PHOTONVISION & SHOOTER ROTATE
   *******************************************************************/
  public void rotateVersion() {

    var result = m_camera.getLatestResult();
    if (result.hasTargets()) {
      PhotonTrackedTarget target = result.getBestTarget();

      SmartDashboard.putNumber("TargetYaw", target.getYaw());
      SmartDashboard.putNumber("TargetPitch", target.getPitch());

      m_rotate.set(-m_rotateVersionpid.calculate(target.getYaw(), 0));
    }
  }

  /******************************************************************
   * RESTE
   *******************************************************************/
  private void resteEncoder() {
    if (!switch_rotation.get()) {
      rotate.setPosition(0);
    }
    if (switch_elevation.get()) {
      elevate.setPosition(0);
    }
    if (rotate.getPosition() > ShooterConstant.rotate_left_limit
        | rotate.getPosition() < ShooterConstant.rotate_right_limit) {
      m_rotate.stopMotor();
    }
    if (elevate.getPosition() > ShooterConstant.elevation_limit | elevate.getPosition() < 0) {
      m_elevation.stopMotor();
    }
  }

  /******************************************************************
   * ROTATE
   *******************************************************************/
  public void rotate_clock() {
    m_rotate.set(0.3);
  }

  public void rotate_unclock() {
    m_rotate.set(-0.3);
  }

  public void rotate_stop() {
    m_rotate.stopMotor();
  }

  /******************************************************************
   * ELEVATION
   *******************************************************************/
  public void elevation_clock() {
    m_elevation.set(0.2);
  }

  public void elevation_unclock() {
    m_elevation.set(-0.2);
  }

  public void elevation_stop() {
    m_elevation.stopMotor();
  }

  /******************************************************************
   * LAUNCH
   *******************************************************************/
  public void launch() {
    m_launch.set(-1);
  }

  public void stopLaunch() {
    m_launch.stopMotor();
  }

  /******************************************************************
   * FLYWHEEL
   ********************************************************************/
  public void setPower(double power) {
    m_shooter_left_falcon.set(ControlMode.PercentOutput, power);
  }

  public void setVelocity_RPM(double velocity) {

    TargetSpeed.setDouble(velocity);
    if (velocity > 500) {
      m_shooter_left_falcon.set(ControlMode.Velocity, RPMtoRawSensorUnit(velocity));
    }
    else{
      stopShoot();
    }

  }

  public void stopShoot() {
    m_shooter_left_falcon.stopMotor();//
    // m_shooter_right_falcon.stopMotor();
  }

  /***************************************************
   * RPM(rotate per Second) - UPS(unit per 100 ms)
   ******************************************************/
  public double RPMtoRawSensorUnit(double velocity) {
    return velocity * 2048 / 600;
  }

  /****************** UPS - RPM *************************************/
  public double RawSensorUnittoRPM(double velocity) {
    return velocity / 2048 * 600;
  }

}
