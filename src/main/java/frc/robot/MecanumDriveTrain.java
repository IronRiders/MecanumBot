package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive; 


public class MecanumDriveTrain {
    private static final int kFrontLeftChannel = 2;
    private static final int kRearLeftChannel = 3;
    private static final int kFrontRightChannel = 1;
    private static final int kRearRightChannel = 0;

    private CANSparkMax frontLeft;
    private CANSparkMax rearLeft;
    private CANSparkMax frontRight;
    private CANSparkMax rearRight;

    private MecanumDrive m_robotDrive;

    public MecanumDriveTrain() {
        frontLeft = new CANSparkMax(kFrontLeftChannel, MotorType.kBrushless); 
        rearLeft = new CANSparkMax(kRearLeftChannel, MotorType.kBrushless); 
        frontRight = new CANSparkMax(kFrontRightChannel, MotorType.kBrushless); 
        rearRight = new CANSparkMax(kRearRightChannel, MotorType.kBrushless); 

        // Invert the left side motors.
        // You may need to change or remove this to match your robot.
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);

        m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
    }

    public MecanumDrive getRobotDrive() {return m_robotDrive;}
}