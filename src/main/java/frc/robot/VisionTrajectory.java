package frc.robot;

public class VisionTrajectory {
    private final double scaler = .2;
    private final double MIN_VALUE = 0;

    private boolean isNegative;
    private double quarterTurn;
    private double totalAngleDistance;

    private double m;

    public VisionTrajectory(Vision camera) {
        if (camera.getTx() < 0) {
            isNegative = true;
        }
        quarterTurn = Math.abs(camera.getTx() / 4);
        totalAngleDistance = Math.abs(camera.getTx());
        // double m = -1 / quarterTurn;

    }

    public double turnSpeed(double currentAngle) {
        return f(currentAngle) * scaler + MIN_VALUE;
    }

    // equation: https://www.desmos.com/calculator/vi9ptl8sfz
    private double f(double x) {
        return -(4 / Math.pow(totalAngleDistance, 2)) * Math.pow((x - (totalAngleDistance / 2)), 2) + 1;
    }

    // private double integrate(double a, double b, int N) {
    // double h = (b - a) / N; // step size
    // double sum = 0.5 * (f(a) + f(b)); // area
    // for (int i = 1; i < N; i++) {
    // double x = a + h * i;
    // sum = sum + f(x);
    // }

    // return sum * h;
    // }
}
