// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DeliverSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

  
  IntakeSubsystem m_Intake;
  DeliverSubsystem m_deliver;
  
  private final Supplier<Double> m_IntakeFounction, m_OuttakeFounction;
  
  public IntakeCommand( IntakeSubsystem p_intake, DeliverSubsystem p_deliver,Supplier<Double> p_IntakeFounction, Supplier<Double> p_OuttakeFounction) {
    m_IntakeFounction = p_IntakeFounction;
    m_OuttakeFounction = p_OuttakeFounction;
    m_Intake = p_intake;
    m_deliver = p_deliver;

    addRequirements(p_intake);
    addRequirements(p_deliver);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double realTimeIntake = m_IntakeFounction.get();
    double realTimeOuttake = m_OuttakeFounction.get();

    m_Intake.rotate(realTimeIntake, realTimeOuttake);
    m_deliver.deliverrun(realTimeIntake, realTimeOuttake);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
