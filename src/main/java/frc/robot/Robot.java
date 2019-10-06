package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Controller;
import frc.robot.MecanumDrive;

public class Robot extends TimedRobot {
    private Controller controller;
    private MecanumDrive mecanumDrive;

    @Override
    public void robotInit() {
        controller = new Controller(0);
        mecanumDrive = new MecanumDrive();
    }

    @Override
    public void teleopPeriodic() {
        mecanumDrive.updateSpeed(controller.getAxis(0), controller.getAxis(1), controller.getAxis(2));
    }
}
