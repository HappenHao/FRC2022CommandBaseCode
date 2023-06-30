
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeliverConstant;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class DeliverSubsystem extends SubsystemBase {

  private CANSparkMax m_deliver_front = new CANSparkMax(DeliverConstant.front, MotorType.kBrushed);
  private CANSparkMax m_deliver_back = new CANSparkMax(DeliverConstant.back, MotorType.kBrushed);

  
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
  private final Color kRedTarget = new Color(0.561, 0.232, 0.114);

  public DeliverSubsystem() {
    m_deliver_front.setIdleMode(IdleMode.kBrake);
    m_deliver_back.setIdleMode(IdleMode.kBrake);
    m_deliver_front.setInverted(true);
    m_deliver_back.follow(m_deliver_front);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
  }

  @Override
  public void periodic() {
    Color detectedColor = m_colorSensor.getColor();
    double IR = m_colorSensor.getIR();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    
    String colorString;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else {
      colorString = "Unknown";
    }
    SmartDashboard.putString("Detected Color", colorString);
  }

  public void deliverrun(double clockwise, double unclockwise) {
    if (clockwise > 0.1 && unclockwise < 0.1) {
      m_deliver_front.set(clockwise);
    } else if (clockwise < 0.1 && unclockwise > 0.1) {
      m_deliver_front.set(-unclockwise);
    } else {
      m_deliver_front.stopMotor();
    }
  }

  public void set_deliver() {
    m_deliver_front.set(1);
  }

  public void stop_deliver() {
    m_deliver_front.stopMotor();
  }

}
