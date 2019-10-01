package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MecanumDrive {
    private static final double kSpeedMultiplier = 0.5;

    private static final int kFrontLeftChannel = 1;
    private static final int kRearLeftChannel = 4;
    private static final int kFrontRightChannel = 3;
    private static final int kRearRightChannel = 2;

    private CANSparkMax frontLeft;
    private CANSparkMax rearLeft;
    private CANSparkMax frontRight;
    private CANSparkMax rearRight;

    public MecanumDrive() {
        this.frontLeft = new CANSparkMax(kFrontLeftChannel, MotorType.kBrushless);
        this.frontRight = new CANSparkMax(kFrontRightChannel, MotorType.kBrushless);
        this.rearLeft = new CANSparkMax(kRearLeftChannel, MotorType.kBrushless);
        this.rearRight = new CANSparkMax(kRearRightChannel, MotorType.kBrushless);

        this.frontLeft.setInverted(true);
        this.rearLeft.setInverted(true);
    }

    public void updateSpeed(double strafe, double drive, double turn) {
        double[] speeds = new double[4];
        speeds[0] = strafe + drive + turn;
        speeds[1] = strafe + drive - turn;
        speeds[2] = -strafe + drive + turn;
        speeds[3] = -strafe + drive - turn;

        if (magnitude(speeds) > 1) {
            speeds = normalize(speeds);
        }

        for (int i = 0; i < speeds.length; ++i) {
            speeds[i] *= kSpeedMultiplier;
        }

        this.frontLeft.set(speeds[0]);
        this.frontRight.set(speeds[1]);
        this.rearLeft.set(speeds[2]);
        this.rearRight.set(speeds[3]);
    }

    private double magnitude(final double[] vector) {
        double[] squares = new double[vector.length];
        for (int i = 0; i < vector.length; ++i) {
            squares[i] = vector[i] * vector[i];
        }
        double sum = 0;
        for (double square : squares) {
            sum += square;
        }
        return Math.sqrt(sum);
    }

    private double[] normalize(double[] vector) {
        double[] normalized = new double[vector.length];
        final double mag = magnitude(vector);
        for (int i = 0; i < vector.length; ++i) {
            normalized[i] = vector[i] / mag;
        }
        return normalized;
    }
}
