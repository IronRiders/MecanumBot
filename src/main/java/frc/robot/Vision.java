package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    private NetworkTable table;
    private NetworkTableEntry hasTarget;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private final double TURN_SPEED = .1;
    private final double FORWARD_SPEED = .2;
    private final double MAX_SPEED = .2;
    private final double MIN_SPEED = .05;
    private final double TARGET_HIGHT = .447;
    //private final double WANTED_AREA = 1;
    private final double CAMERA_HIGHT = .1;
    private final double CAMERA_ANGLE = 0;

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        hasTarget = table.getEntry("tv"); // whether or not there is valid targets
        tx = table.getEntry("tx"); // displacement on the X axis from 27 to -27
        ty = table.getEntry("ty"); // displacement on the y axis from camera
        ta = table.getEntry("ta"); // Target Area or % of the image
    }

    public void printCoords() {
        System.out.println("tx: " + getTx() + "\tty: " + getTy() + "\tDistance " + distanceToTarget());
    }

    public double[] driveToTarget() {
        double driveInstructions[] = { 0.0, 0.0, 0.0 };
        if (getHasTarget()) {
            driveInstructions[1] = distanceToTarget() * FORWARD_SPEED;
            driveInstructions[2] = getTx() * TURN_SPEED;
            for (int i = 0; i < driveInstructions.length; i++) {
                if (Math.abs(driveInstructions[i]) < MIN_SPEED) {
                    driveInstructions[i] = 0;
                } else if (Math.abs(driveInstructions[i]) > MAX_SPEED) {
                    driveInstructions[i] = MAX_SPEED * isNegative(driveInstructions[i]);
                }
            }
        }

        return driveInstructions;

    }

    public double distanceToTarget() {
        return (TARGET_HIGHT - CAMERA_HIGHT) / (Math.tan(toRadians(getTy() + CAMERA_ANGLE)));
    }

    public double toRadians(double x) {
        return Math.PI * x / 180.0;
    }

    public double getTx() {
        return tx.getDouble(0);
    }

    public double getTy() {
        return ty.getDouble(0);
    }

    public double getTa() {
        return ta.getDouble(-1);
    }

    public boolean getHasTarget() {
        return hasTarget.getDouble(0) == 1;
    }

    private double isNegative(double num){
        if(num < 0){
            return -1;
        } else {
            return 1;
        }
    }

}