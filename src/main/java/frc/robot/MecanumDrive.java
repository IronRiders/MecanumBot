package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MecanumDrive {
    private static final double kSpeedMultiplier = 0.5;

    private static final int kFrontLeftChannel = 1;
    private static final int kRearLeftChannel = 4;
    private static final int kFrontRightChannel = 3;
    private static final int kRearRightChannel = 2;

    private CANSparkMax[] motors;

    public MecanumDrive() {
        motors[0] = new CANSparkMax(kFrontLeftChannel, MotorType.kBrushless);
        motors[1] = new CANSparkMax(kFrontRightChannel, MotorType.kBrushless);
        motors[2] = new CANSparkMax(kRearLeftChannel, MotorType.kBrushless);
        motors[3] = new CANSparkMax(kRearRightChannel, MotorType.kBrushless);

        motors[0].setInverted(true);
        motors[2].setInverted(true);
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

        for (int i = 0; i < 4; ++i) {
            motors[i].set(speeds[i] * kSpeedMultiplier);
        }
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
        return Math.pow(sum, 1 / vector.length);
    }

    private double[] normalize(final double[] vector) {
        double[] normalized = new double[vector.length];
        for (int i = 0; i < vector.length; ++i) {
            normalized[i] = vector[i] / magnitude(vector);
        }
        return normalized;
    }
}
