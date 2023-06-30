package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  /************************************************************************************/
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

  }
  
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /***********************************************************************************
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    m_robotContainer.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
  }

  /*******************************************************************************************
   * This autonomous runs the autonomous command selected by your
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    //
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  /*******************************************************************************************
  
   */
  @Override
  public void teleopInit() {
    //
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    //
    m_robotContainer.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    m_robotContainer.teleopPeriodic();
  }

  /****************************************************************************************
   * 
   */
  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
    
  }
}
