/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.MecanumDriveCustom;
//import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends TimedRobot {
  private static final int kJoystickChannel = 0;

  private final MecanumDriveTrain mecanum = new MecanumDriveTrain();
  private final MecanumDriveCustom m_robotDrive = mecanum.getRobotDrive();
  private final LambdaJoystick joystick1 = new LambdaJoystick(0, mecanum::updateSpeed);
  private final LambdaJoystick joystick2 = new LambdaJoystick(1, mecanum::updateSpeed);   //TODO filler

  @Override
  public void robotInit() {
     joystick1.addButton(10, mecanum::invertDrive);
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    joystick1.listen();
    joystick2.listen();
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }
}
