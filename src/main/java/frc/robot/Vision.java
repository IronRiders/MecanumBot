package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    private final double TURN_SPEED = .03;
    private final double FORWARD_SPEED = .2;
    private final double TARGET_HIGHT = 3;
    private final double CAMERA_HIGHT = .5;
    private boolean hasTarget;

    // private boolean tv;
    private double tx;
    private double ty;
    private double ta;

    public Vision() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tv = table.getEntry("tv"); // weather or not there is valid targets
        NetworkTableEntry tx = table.getEntry("tx"); // displacement on the X axis from 27 to -27
        NetworkTableEntry ty = table.getEntry("ty"); // displacement on the y axis from camera
        NetworkTableEntry ta = table.getEntry("ta"); // Target Area or % of the image

        hasTarget = false;

    }

    public double[] turnToTarget(VisionTrajectory tradj) {
        double driveInstructions[] = { 0, 0, 0 };
        updateData();
        if (!hasTarget) {
            return driveInstructions;
        }
        double turnSpeed = tradj.turnSpeed(tx);
        if (tx < 0) {
            turnSpeed = -turnSpeed;
        }
        return driveInstructions;

    }

    // These methods are public because the updateData is called in robot before a
    // new tradjectory is made and
    // The get methods don't update the value because they will then give diffrent
    // values from the ones vision
    // is using if the robot is moving
    public void updateData() {
        hasTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getBoolean(false);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    }

    public double distanceToTarget() {
        double distance = 0;
        distance = (TARGET_HIGHT - CAMERA_HIGHT) / (Math.tan(tx + ty));

        return distance;
    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public double getTa() {
        return ta;
    }

    public boolean getTv() {
        return hasTarget;
    }

}