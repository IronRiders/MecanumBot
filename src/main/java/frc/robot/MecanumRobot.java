package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Controller;
import frc.robot.MecanumDrive;

public class MecanumRobot extends TimedRobot {
    private final MecanumDrive mecanumDrive = new MecanumDrive();
    private final Controller controller = new Controller(5);

    @Override
    public void teleopPeriodic() {
        this.controller.update();
        this.mecanumDrive.updateSpeed(controller);
    }
}
