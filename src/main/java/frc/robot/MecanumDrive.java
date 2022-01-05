package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MecanumDrive {
    //private static final double kSpeedMultiplier = 0.3;

    private static final int kFrontLeftChannel = 1;
    private static final int kRearLeftChannel = 4;
    private static final int kFrontRightChannel = 3;
    private static final int kRearRightChannel = 2;
    private boolean inverted;

    private CANSparkMax[] motors;

    public MecanumDrive() {
        SmartDashboard.putNumber("kSpeed", .1);
        this.motors = new CANSparkMax[4];
        this.motors[0] = new CANSparkMax(MecanumDrive.kFrontLeftChannel, MotorType.kBrushless);
        this.motors[1] = new CANSparkMax(MecanumDrive.kFrontRightChannel, MotorType.kBrushless);
        this.motors[2] = new CANSparkMax(MecanumDrive.kRearLeftChannel, MotorType.kBrushless);
        this.motors[3] = new CANSparkMax(MecanumDrive.kRearRightChannel, MotorType.kBrushless);

        this.motors[0].setInverted(true);
        this.motors[1].setInverted(false);
        this.motors[2].setInverted(true);
        this.motors[3].setInverted(false);
        inverted = true;
    }

    

    public void invertDrive(){
        inverted = !inverted;
    }

    public void updateSpeed(double strafe, double drive, double turn) {
        double[] speeds = new double[4];
        if(inverted){
            speeds[0] = 0 + strafe - drive + turn;
            speeds[1] = 0 - strafe - drive - turn;
            speeds[2] = 0 - strafe - drive + turn;
            speeds[3] = 0 + strafe - drive - turn;
        } else {
            speeds[0] = 0 - strafe + drive + turn;
            speeds[1] = 0 + strafe + drive - turn;
            speeds[2] = 0 + strafe + drive + turn;
            speeds[3] = 0 - strafe + drive - turn;
        }

        if (magnitude(speeds) > 1) {
            speeds = normalize(speeds);
        }
        
        for (int i = 0; i < 4; ++i) {
            this.motors[i].set(speeds[i] * SmartDashboard.getNumber("kSpeed", .1));
        }
        
    }
        
        
    

    private double magnitude(final double[] vector) {
        double[] squares = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            squares[i] = vector[i] * vector[i];
        }
        double sum = 0;
        for (double square : squares) {
            sum += square;
        }
        return Math.pow(sum, 1 / vector.length);
    }

    private double[] normalize(final double[] vector) {
        double max = 1.0;
        for (int i = 0; i < vector.length; ++i) {
            if (vector[i] > max) {
                max = vector[i];
            }
        }
        if (max <= 1) {
            return vector;
        }
        double[] normalized = new double[vector.length];
        for (int i = 0; i < vector.length; ++i) {
            normalized[i] = vector[i] / /*magnitude(vector)*/ max;
        }
        return normalized;
    }
}
