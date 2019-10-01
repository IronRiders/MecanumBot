package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;

public class Controller extends GenericHID {
    private static double kDeadzone = 0.1;

    public double[] axes;
    public boolean[] buttons;

    public Controller(final int port) {
        super(port);

        this.axes = new double[this.getAxisCount()];
        this.buttons = new boolean[this.getButtonCount()];

        this.update();
    }

    public final void update() {
        for (int i = 0; i < this.axes.length; ++i) {
            double raw = this.getRawAxis(i);
            double clamped = raw < -1 ? -1 : raw > 1 ? 1 : raw;
            if (clamped > kDeadzone) {
                this.axes[i] = (clamped - kDeadzone) / (1 - kDeadzone);
            }
            else if (clamped < -kDeadzone) {
                this.axes[i] = (clamped + kDeadzone) / (1 - kDeadzone);
            }
            else {
                this.axes[i] = 0;
            }
        }

        for (int i = 0; i < this.buttons.length; ++i) {
            this.buttons[i] = this.getRawButton(i + 1);
        }
    }

    @Override
    public double getX(Hand hand) {
        this.update();
        return this.axes[0];
    }

    @Override
    public double getY(Hand hand) {
        this.update();
        return this.axes[1];
    }
}
