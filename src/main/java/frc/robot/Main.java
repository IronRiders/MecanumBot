package frc.robot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import frc.robot.Controller;
import frc.robot.MecanumDrive;

public class Main {
    public static final void main(String[] args) {
        Controller controller = new Controller(0);
        MecanumDrive mecanumDrive = new MecanumDrive();

        Runnable update = new Runnable() {
            public void run() {
                controller.update();
        
                final double x = controller.getAxes()[0];
                final double y = controller.getAxes()[1];
                final double w = controller.getAxes()[3];
                
                mecanumDrive.updateSpeed(x, y, w);
            }
        };
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(update, 0, 20, TimeUnit.MILLISECONDS);
    }
}
