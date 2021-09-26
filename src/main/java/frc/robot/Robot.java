package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Controller;
import frc.robot.MecanumDrive;

public class Robot extends TimedRobot {
    private Controller controller;
    private MecanumDrive mecanumDrive;
    private Vision vision;
    private VisionTrajectory tradj;
    private boolean tradjCreated;

    @Override
    public void robotInit() {
        controller = new Controller(0);
        mecanumDrive = new MecanumDrive();
        vision = new Vision();
        tradjCreated = false;
    }

    @Override
    public void teleopPeriodic() {
        if (controller.getButtonPressed(1)){
            mecanumDrive.invertDrive();
        }
        if (controller.getButton(2)) {
            if(!tradjCreated){
                vision.updateData();
                tradj = new VisionTrajectory(vision);
                tradjCreated = true;
            }
            double speeds[] = vision.turnToTarget(tradj);
            mecanumDrive.updateSpeed(speeds[0], speeds[1], speeds[2]);
        } else{
            tradjCreated = false;
            mecanumDrive.updateSpeed(controller.getAxis(0), controller.getAxis(1), controller.getAxis(2));
        }

    }
}
