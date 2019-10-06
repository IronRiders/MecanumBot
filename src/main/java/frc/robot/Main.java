package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.Robot;

public class Main {
    public static final void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }
}
