package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MecanumDrive {
    private static final double kMaxOutput = 0.5;

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
        speeds[kFrontLeftChannel] = strafe + drive + turn;
        speeds[kFrontRightChannel] = strafe + drive - turn;
        speeds[kRearLeftChannel] = -strafe + drive + turn;
        speeds[kRearRightChannel] = -strafe + drive - turn;

        speeds = magnitude(speeds) > 1 ? normalize(speeds) : speeds;

        for (int i = 0; i < speeds.length; ++i) {
            speeds[i] *= kMaxOutput;
        }

        this.frontLeft.set(speeds[kFrontLeftChannel]);
        this.frontRight.set(speeds[kFrontRightChannel]);
        this.rearLeft.set(speeds[kRearLeftChannel]);
        this.rearRight.set(speeds[kRearRightChannel]);
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
