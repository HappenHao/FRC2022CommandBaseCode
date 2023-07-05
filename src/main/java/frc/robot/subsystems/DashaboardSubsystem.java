

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DashaboardSubsystem extends SubsystemBase {

  PowerDistribution m_PDP = new PowerDistribution(0, ModuleType.kCTRE);
  //PowerDistribution examplePD = new PowerDistribution(1, ModuleType.kRev);
  

  public DashaboardSubsystem() {
    // CameraServer.startAutomaticCapture();
    // NewTab.add("Pi",3.14);
    
  }

  @Override
  public void periodic() {
    double Voltage = m_PDP.getVoltage();
    double[] Current = new double[]
    { m_PDP.getCurrent(0),
      m_PDP.getCurrent(1),
      m_PDP.getCurrent(2),
      m_PDP.getCurrent(3),
      m_PDP.getCurrent(4),
      m_PDP.getCurrent(5),
      m_PDP.getCurrent(6),
      m_PDP.getCurrent(7),
      m_PDP.getCurrent(8),
      m_PDP.getCurrent(9),
      m_PDP.getCurrent(10),
      m_PDP.getCurrent(11),
      m_PDP.getCurrent(12),
      m_PDP.getCurrent(13),
      m_PDP.getCurrent(14),
      m_PDP.getCurrent(15)
    };
    // abc.setDouble(Voltage);
    SmartDashboard.putNumber("PDP Voltage", Voltage);
    SmartDashboard.putNumberArray("PDP Current",Current);

  }

}
