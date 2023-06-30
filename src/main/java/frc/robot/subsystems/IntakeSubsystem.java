
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeliverConstant;

public class IntakeSubsystem extends SubsystemBase {

  private CANSparkMax m_intake = new CANSparkMax(DeliverConstant.intake, MotorType.kBrushless);

  public IntakeSubsystem() {
    m_intake.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    
  }

  public void stopMotor() {
    m_intake.stopMotor();
  }

  public void rotate(double clockValue, double unclockValue) {
    SmartDashboard.putNumber("IntakeSpeed_RPM", m_intake.getEncoder().getVelocity());

    if (clockValue > 0.1 && unclockValue < 0.1)
      m_intake.set(-clockValue);
    else if (clockValue < 0.1 && unclockValue > 0.1)
      m_intake.set(unclockValue);
    else
      m_intake.stopMotor();
  }

}
