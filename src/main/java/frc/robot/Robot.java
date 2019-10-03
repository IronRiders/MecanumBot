package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Controller;

public class Robot extends TimedRobot {
    private Controller controller;

    @Override
    public void robotInit() {
        controller = new Controller(0);
    }

    @Override
    public void teleopPeriodic() {
        controller.update();
        System.out.println();
        System.out.println(controller.getName());
        final double[] axes = controller.getAxes();
        System.out.println("axes.length: " + axes.length);
        for (int i = 0; i < axes.length; ++i) {
            System.out.println("axes[" + i + "]: " + axes[i]);
        }
        final boolean[] buttons = controller.getButtons();
        System.out.println("buttons.length: " + buttons.length);
        for (int i = 0; i < buttons.length; ++i) {
            System.out.println("buttons[" + i + "]: " + buttons[i]);
        }
        System.out.println();
    }
}
