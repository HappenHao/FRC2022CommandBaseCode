
package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.sensors.Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.DriverConstant;

public class DriveSubsystem extends SubsystemBase {
 
  private CANSparkMax m_left_front = new CANSparkMax(DriverConstant.left_front, MotorType.kBrushless);
  private CANSparkMax m_left_back = new CANSparkMax(DriverConstant.left_back, MotorType.kBrushless);
  private CANSparkMax m_right_front = new CANSparkMax(DriverConstant.right_front, MotorType.kBrushless);
  private CANSparkMax m_right_back = new CANSparkMax(DriverConstant.right_back, MotorType.kBrushless);

  public DifferentialDrive m_driver = new DifferentialDrive(m_left_front, m_right_front);
  DifferentialDriveKinematics kinematics =new DifferentialDriveKinematics(0.5);
 
  Pigeon2 m_pigeon = new Pigeon2(DriverConstant.pigeno_ID, "rio");



  @Override
  public void periodic() {
    SmartDashboard.putNumber("Driver_LeftSpeed", m_left_front.getEncoder().getVelocity());
    SmartDashboard.putNumber("Driver_RightSpeed", m_right_front.getEncoder().getVelocity());
    SmartDashboard.putNumber("YAW", m_pigeon.getYaw());
  }

  public DriveSubsystem() {
    m_left_front.setInverted(true);

    m_left_front.setIdleMode(IdleMode.kBrake);
    m_left_back.setIdleMode(IdleMode.kBrake);
    m_right_front.setIdleMode(IdleMode.kBrake);
    m_right_back.setIdleMode(IdleMode.kBrake);

    m_left_back.follow(m_left_front);
    m_right_back.follow(m_right_front);
    
  }

  //
  public void TankDrive(double leftSpeed, double rightSpeed) {
    m_driver.tankDrive(leftSpeed, rightSpeed);
  }

  //
  public void ArcadeDrive(double YSpeed, double XSpeed) {
    m_driver.arcadeDrive(YSpeed, -XSpeed);
  }

  public void TestDrive(double leftSpeed, double rightSpeed) {
    m_left_front.set(-leftSpeed);
    m_right_front.set(rightSpeed);
  }
}
