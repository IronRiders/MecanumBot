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
    private final double TURN_SPEED = .03;
    private final double FORWARD_SPEED = .2;
    private final double TARGET_HIGHT = .447;
    private final double CAMERA_HIGHT = .1;

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        hasTarget = table.getEntry("tv"); // whether or not there is valid targets
        tx = table.getEntry("tx"); // displacement on the X axis from 27 to -27
        ty = table.getEntry("ty"); // displacement on the y axis from camera
        ta = table.getEntry("ta"); // Target Area or % of the image
    }

    public void printCoords(){
        System.out.println("tx: " + getTx() + "\tty: " + getTy() + "\tDistance " + distanceToTarget());
    }
    public double[] turnToTarget(VisionTrajectory tradj) {
        double driveInstructions[] = { 0, 0, 0 };
        if (!getHasTarget()) {
            return driveInstructions;
        }
        double turnSpeed = tradj.turnSpeed(getTx());
        if (getTx() < 0) {
            turnSpeed = -turnSpeed;
        }
        return driveInstructions;

    }

    public double[] driveToTarget(){
        double driveInstructions[] = { 0.0, 0.0, 0.0};
        if(getHasTarget() && distanceToTarget() > 1.2){
            driveInstructions[1] = .1;
        }
        return driveInstructions;
        
    }

    public double distanceToTarget() {
        double distance = 0;
        distance = (TARGET_HIGHT - CAMERA_HIGHT) / (Math.tan(toRadians(getTy())));

        return distance;
    }

    public double toRadians(double x){
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

}