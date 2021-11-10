package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;

public class Controller extends GenericHID {
    private static double kDeadzone = 0.1;

    public Controller(final int port) {
        super(port);
    }

    public boolean getButtonPressed(final int i) {
        return super.getRawButtonPressed(i);
    }

    public final double getAxis(final int i) {
        double raw = super.getRawAxis(i);
        double clamped = raw < -1 ? -1 : raw > 1 ? 1 : raw;
        if (clamped > Controller.kDeadzone) {
            return (clamped - Controller.kDeadzone) / (1 - Controller.kDeadzone);
        } else if (clamped < -Controller.kDeadzone) {
            return (clamped + Controller.kDeadzone) / (1 - Controller.kDeadzone);
        } else {
            return 0;
        }
    }

    public final boolean getButton(final int i) {
        return super.getRawButton(i);
    }

    @Override
    @Deprecated
    public double getX(Hand hand) {
        return 0;
    }

    @Override
    @Deprecated
    public double getY(Hand hand) {
        return 0;
    }
}
