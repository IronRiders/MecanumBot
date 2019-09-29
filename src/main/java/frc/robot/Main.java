package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.MecanumRobot;

public class Main {
    public static final void main(String[] args) {
        RobotBase.startRobot(MecanumRobot::new);
    }
}
