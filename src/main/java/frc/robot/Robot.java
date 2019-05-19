/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends TimedRobot {
  private static final int kJoystickChannel = 0;

  private MecanumDrive m_robotDrive;
 // private Joystick joystick1;
  private LambdaJoystick joystickL;

  @Override
  public void robotInit() {
    MecanumDriveTrain mecanum = new MecanumDriveTrain();
    m_robotDrive = mecanum.getRobotDrive();
    
    joystickL = new LambdaJoystick(0, mecanum::updateSpeed);    
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.
   // m_robotDrive.driveCartesian(joystick1.getX(), joystick1.getY(), joystick1.getZ(), 0.0);
  
  }
}
