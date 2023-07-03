
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.Contorller;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {

  /******************* SUBSYSTEM INIT **************************/
  final DriveSubsystem m_drive = new DriveSubsystem();//
  private final ShooterSubsystem m_shooter = new ShooterSubsystem(); //
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final PneumaticSubsystem m_pneumatic = new PneumaticSubsystem(); //
  private final DeliverSubsystem m_deliver = new DeliverSubsystem();
  private final DashaboardSubsystem m_Dashaboard = new DashaboardSubsystem();

  /*********************CONTRALLER BUTTON INIT************************** */
  private Joystick m_Joystick = new Joystick(Contorller.joystickID);
  private XboxController m_Xbox = new XboxController(Contorller.xboxID);

  private final JoystickButton UpButtonY = new JoystickButton(m_Xbox, 4);
  private final JoystickButton DownButtonA = new JoystickButton(m_Xbox, 1);
  private final JoystickButton launchButton = new JoystickButton(m_Joystick, 1);

  private POVButton m_rotate_colock_button = new POVButton(m_Xbox, 90);
  private POVButton m_rotate_uncolock_button = new POVButton(m_Xbox, 270);
  private POVButton m_elevation_clock_button = new POVButton(m_Xbox, 0);
  private POVButton m_elevation_unclock_button = new POVButton(m_Xbox, 180);

  /***************************************************************************************/
  public RobotContainer() {
    configureButtonBindings();

    m_drive.setDefaultCommand(new ArcadeDriverCommand(m_drive,
        () -> m_Xbox.getLeftY(),
        () -> m_Xbox.getRightX()));
    m_intake.setDefaultCommand(new IntakeCommand(m_intake, m_deliver,
        () -> m_Xbox.getLeftTriggerAxis(),
        () -> m_Xbox.getRightTriggerAxis()));
    m_shooter.setDefaultCommand(new ShooterCommand(m_shooter,
        () -> m_Joystick.getRawAxis(3),
        () -> m_Joystick.getRawButtonPressed(Contorller.JoystickVersionButtin)));

    SmartDashboard.putNumber("Xbox_Axis_L2", m_Xbox.getRightTriggerAxis());
    SmartDashboard.putNumber("Joystick_Axis_Trigle", m_Joystick.getRawAxis(3));
    
  }

  private void configureButtonBindings() {
    UpButtonY   .whenPressed(new InstantCommand(m_pneumatic::InakeUp, m_pneumatic));
    DownButtonA .whenPressed(new InstantCommand(m_pneumatic::intakeDown, m_pneumatic));
    launchButton.whenPressed(new InstantCommand(m_shooter::launch, m_shooter) )
                .whenReleased(new InstantCommand(m_shooter::stopLaunch));
    // new JoystickButton(m_Joystick,1).whenPressed(new InstantCommand(m_shooter::launch, m_shooter) )
    //                                 .whenReleased(new InstantCommand(m_shooter::stopLaunch));

    m_rotate_colock_button    .whileHeld(new InstantCommand(m_shooter::rotate_clock, m_shooter))
                              .whenReleased(new InstantCommand(m_shooter::rotate_stop, m_shooter));
    m_rotate_uncolock_button  .whileHeld(new InstantCommand(m_shooter::rotate_unclock, m_shooter))
                              .whenReleased(new InstantCommand(m_shooter::rotate_stop, m_shooter));
    m_elevation_clock_button  .whileHeld(new InstantCommand(m_shooter::elevation_clock, m_shooter))
                              .whenReleased(new InstantCommand(m_shooter::elevation_stop, m_shooter));
    m_elevation_unclock_button.whileHeld(new InstantCommand(m_shooter::elevation_unclock, m_shooter))
                              .whenReleased(new InstantCommand(m_shooter::elevation_stop, m_shooter));

    
  }

  // ***************************************************************** */
  public void teleopInit() {

  }

  public void teleopPeriodic() {

  }

  public void disabledInit() {
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
