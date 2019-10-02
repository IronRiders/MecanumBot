package frc.robot;

import frc.robot.Controller;

public class Main {
    public static final void main(String[] args) {
        Controller controller;
        for (int i = 0; i < 5; ++i) {
            controller = new Controller(i);
            controller.update();
            System.out.println(controller.getAxes().length);
            System.out.println(controller.getButtons().length);
        }
    }
}
