package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    private NetworkTable table;
    private NetworkTableEntry hasTarget, tx, ty, ta;
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

    

    public double distanceToTarget() {
        return (TARGET_HIGHT - CAMERA_HIGHT) / (Math.tan(toRadians(getTy() + CAMERA_ANGLE)));
    }

    //This method is because there are diffrent target hights that we might want to use.
    private double determineObjectDist(double cameraHeight, double targetHight){
        return (targetHight - cameraHeight) / (Math.tan(toRadians(CAMERA_ANGLE + getTy())));
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

 // Returns the reduced row-echelon form of a matrix.
private double[][] Gaussian(double[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    double[][] echelon = new double[n][];
    for(int i = 0; i < matrix.length; i++) {
      echelon[i] = matrix[i].clone();
    }

    while (!isEchelon(echelon)) {
      for (int i = 0; i < Math.min(n, m); i++) {
        double pivot = matrix[i][i];
        if (pivot != 0) {
          if (pivot != 1) {
            for (int j = 0; j < m; j++) { 
                echelon[i][j] /= pivot; 
            }
          }
          for (int rowNum = 0; rowNum < n; rowNum++) {
            for (int j = 0; j < m; j++) { 
                echelon[rowNum][j] -= echelon[rowNum][i] * echelon[i][j];
            }
          }
        }
      }
    }
    return echelon;
  }

  // Tests whether a matrix is in reduced-row-echelon form
  private boolean isEchelon(double[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    double[][] matrixTranspose = transpose(matrix);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        double val = matrix[i][j];
        if (Math.abs(val) > 0) {
          int non_zero_count = 0;
          for (int k = 0; k < n; k++) {
            if (matrixTranspose[j][k] != 0) { non_zero_count += 1; }
          }
          if (non_zero_count != 1){ return false; }
        }
      }
    }
    return true;
  }

    // Returns the transpose of a matrix.
    private double[][] transpose(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] matrixTranspose = new double[m][n];
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < m; j++) {
            matrixTranspose[m][n] = matrix[n][m];
          }
        }
        return matrixTranspose;
      }




    // public double[] driveToTarget() {
    //         double driveInstructions[] = { 0.0, 0.0, 0.0 };
    //         if (getHasTarget()) {
    //             driveInstructions[1] = distanceToTarget() * FORWARD_SPEED;
    //             driveInstructions[2] = getTx() * TURN_SPEED;
    //             for (int i = 0; i < driveInstructions.length; i++) {
    //                 if (Math.abs(driveInstructions[i]) < MIN_SPEED) {
    //                     driveInstructions[i] = 0;
    //                 } else if (Math.abs(driveInstructions[i]) > MAX_SPEED) {
    //                     driveInstructions[i] = MAX_SPEED * isNegative(driveInstructions[i]);
    //                 }
    //             }
    //         }

    //         return driveInstructions;

    //     }
}