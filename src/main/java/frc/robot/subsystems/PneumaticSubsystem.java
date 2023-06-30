
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PneumaticConstant;
import frc.robot.Constants.PneumaticStatues;
import frc.robot.Constants.controlConstant;

public class PneumaticSubsystem extends SubsystemBase {
   private Compressor m_compression = new Compressor(controlConstant.PCM, PneumaticsModuleType.CTREPCM);
   private DoubleSolenoid m_intakeUD = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, PneumaticConstant.k_IntakeD,PneumaticConstant.k_IntakeU);
 
  public PneumaticSubsystem() {
    m_intakeUD.set(DoubleSolenoid.Value.kOff);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Comperssion_ON", !m_compression.getPressureSwitchValue() );

    if(!m_compression.getPressureSwitchValue())
    {
      m_compression.enableDigital();
    }
    else{
      m_compression.disable();
    }

  }

  public void InakeUp() {
    m_intakeUD.set(PneumaticStatues.kIntakeUp);
  }

  public void  intakeDown() {
    m_intakeUD.set(PneumaticStatues.kIntakeDown);
  }

}
