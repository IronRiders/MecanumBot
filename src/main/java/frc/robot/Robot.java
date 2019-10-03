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
        controller.update();
        double[] axes = controller.getAxes();
        mecanumDrive.updateSpeed(axes[0], axes[1], axes[2]);
    }
}
