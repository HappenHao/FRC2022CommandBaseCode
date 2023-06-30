// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterSetVelocityCommand extends CommandBase {

  ShooterSubsystem m_shooter;  
  double m_velocity;  //

  public ShooterSetVelocityCommand(ShooterSubsystem p_shooter,double p_velocity) {
    addRequirements(p_shooter);
    m_shooter = p_shooter;
    m_velocity=p_velocity;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    if (m_velocity>500){
    m_shooter.setVelocity_RPM(m_velocity);
    }
    else{
      m_shooter.stopShoot();
    }
  }

  // Called once the command ends or is interrupted.
 
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopShoot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
