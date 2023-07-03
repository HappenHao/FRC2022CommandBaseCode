
package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class ArcadeDriverCommand extends CommandBase {

  DriveSubsystem m_drive;
  private final Supplier<Double> m_SpeedLFounction, m_SpeedRFounction;
  
  public ArcadeDriverCommand(DriveSubsystem p_drive, Supplier<Double> p_SpeedLFounction,Supplier<Double> p_SpeedRFounction) {
    m_SpeedLFounction = p_SpeedLFounction;
    m_SpeedRFounction = p_SpeedRFounction;
    m_drive = p_drive;
    addRequirements(p_drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double realTimeSpeedL = m_SpeedLFounction.get();
    double realTimeSpeedR = m_SpeedRFounction.get();

    m_drive.ArcadeDrive(realTimeSpeedL,realTimeSpeedR);
    // m_drive.TankDrive(realTimeSpeedL,realTimeSpeedR);
    
    
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
