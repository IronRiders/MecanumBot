package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MecanumDrive {

    // Anish's PID Stuff
    private static final int deviceID = 1;
    private CANSparkMax m_motor;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;

    private static final double kSpeedMultiplier = 0.3;

    private static final int kFrontLeftChannel = 1;
    private static final int kRearLeftChannel = 4;
    private static final int kFrontRightChannel = 3;
    private static final int kRearRightChannel = 2;
    private boolean inverted;

    private CANSparkMax[] motors;

    public MecanumDrive() {
        this.motors = new CANSparkMax[4];
        this.motors[0] = new CANSparkMax(MecanumDrive.kFrontLeftChannel, MotorType.kBrushless);
        this.motors[1] = new CANSparkMax(MecanumDrive.kFrontRightChannel, MotorType.kBrushless);
        this.motors[2] = new CANSparkMax(MecanumDrive.kRearLeftChannel, MotorType.kBrushless);
        this.motors[3] = new CANSparkMax(MecanumDrive.kRearRightChannel, MotorType.kBrushless);

        this.motors[0].setInverted(true);
        this.motors[1].setInverted(false);
        this.motors[2].setInverted(true);
        this.motors[3].setInverted(false);
        inverted = false;
    }

    public void invertDrive() {
        inverted = !inverted;
    }

    public void updateSpeed(double strafe, double drive, double turn) {
        double[] speeds = new double[4];
        if (inverted) {
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
            this.motors[i].set(speeds[i] * MecanumDrive.kSpeedMultiplier);
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
            normalized[i] = vector[i] / /* magnitude(vector) */ max;
        }
        return normalized;
    }

    /**
     * REV Smart Motion Guide
     * 
     * The SPARK MAX includes a new control mode, REV Smart Motion which is used to
     * control the position of the motor, and includes a max velocity and max
     * acceleration parameter to ensure the motor moves in a smooth and predictable
     * way. This is done by generating a motion profile on the fly in SPARK MAX and
     * controlling the velocity of the motor to follow this profile.
     * 
     * Since REV Smart Motion uses the velocity to track a profile, there are only
     * two steps required to configure this mode: 1) Tune a velocity PID loop for
     * the mechanism 2) Configure the smart motion parameters
     * 
     * Tuning the Velocity PID Loop
     * 
     * The most important part of tuning any closed loop control such as the
     * velocity PID, is to graph the inputs and outputs to understand exactly what
     * is happening. For tuning the Velocity PID loop, at a minimum we recommend
     * graphing:
     *
     * 1) The velocity of the mechanism (‘Process variable’) 2) The commanded
     * velocity value (‘Setpoint’) 3) The applied output
     *
     * This example will use ShuffleBoard to graph the above parameters. Make sure
     * to load the shuffleboard.json file in the root of this directory to get the
     * full effect of the GUI layout.
     */
    @Override
    public void robotInit() {
        // initialize motor
        m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

        /**
         * The RestoreFactoryDefaults method can be used to reset the configuration
         * parameters in the SPARK MAX to their factory default state. If no argument is
         * passed, these parameters will not persist between power cycles
         */
        m_motor.restoreFactoryDefaults();

        // initialze PID controller and encoder objects
        m_pidController = m_motor.getPIDController();
        m_encoder = m_motor.getEncoder();

        // PID coefficients
        kP = 5e-5;
        kI = 1e-6;
        kD = 0;
        kIz = 0;
        kFF = 0.000156;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        // Smart Motion Coefficients
        maxVel = 2000; // rpm
        maxAcc = 1500;

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        /**
         * Smart Motion coefficients are set on a CANPIDController object
         * 
         * - setSmartMotionMaxVelocity() will limit the velocity in RPM of the pid
         * controller in Smart Motion mode - setSmartMotionMinOutputVelocity() will put
         * a lower bound in RPM of the pid controller in Smart Motion mode -
         * setSmartMotionMaxAccel() will limit the acceleration in RPM^2 of the pid
         * controller in Smart Motion mode - setSmartMotionAllowedClosedLoopError() will
         * set the max allowed error for the pid controller in Smart Motion mode
         */
        int smartMotionSlot = 0;
        m_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        // display Smart Motion coefficients
        SmartDashboard.putNumber("Max Velocity", maxVel);
        SmartDashboard.putNumber("Min Velocity", minVel);
        SmartDashboard.putNumber("Max Acceleration", maxAcc);
        SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
        SmartDashboard.putNumber("Set Position", 0);
        SmartDashboard.putNumber("Set Velocity", 0);

        // button to toggle between velocity and smart motion modes
        SmartDashboard.putBoolean("Mode", true);
    }

    @Override
    public void teleopPeriodic() {
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        double maxV = SmartDashboard.getNumber("Max Velocity", 0);
        double minV = SmartDashboard.getNumber("Min Velocity", 0);
        double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
        double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if ((p != kP)) {
            m_pidController.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            m_pidController.setI(i);
            kI = i;
        }
        if ((d != kD)) {
            m_pidController.setD(d);
            kD = d;
        }
        if ((iz != kIz)) {
            m_pidController.setIZone(iz);
            kIz = iz;
        }
        if ((ff != kFF)) {
            m_pidController.setFF(ff);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            m_pidController.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;
        }
        if ((maxV != maxVel)) {
            m_pidController.setSmartMotionMaxVelocity(maxV, 0);
            maxVel = maxV;
        }
        if ((minV != minVel)) {
            m_pidController.setSmartMotionMinOutputVelocity(minV, 0);
            minVel = minV;
        }
        if ((maxA != maxAcc)) {
            m_pidController.setSmartMotionMaxAccel(maxA, 0);
            maxAcc = maxA;
        }
        if ((allE != allowedErr)) {
            m_pidController.setSmartMotionAllowedClosedLoopError(allE, 0);
            allowedErr = allE;
        }

        double setPoint, processVariable;
        boolean mode = SmartDashboard.getBoolean("Mode", false);
        if (mode) {
            setPoint = SmartDashboard.getNumber("Set Velocity", 0);
            m_pidController.setReference(setPoint, ControlType.kVelocity);
            processVariable = m_encoder.getVelocity();
        } else {
            setPoint = SmartDashboard.getNumber("Set Position", 0);
            /**
             * As with other PID modes, Smart Motion is set by calling the setReference
             * method on an existing pid object and setting the control type to kSmartMotion
             */
            m_pidController.setReference(setPoint, ControlType.kSmartMotion);
            processVariable = m_encoder.getPosition();
        }
        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("Process Variable", processVariable);
        SmartDashboard.putNumber("Output", m_motor.getAppliedOutput());
    }
}
