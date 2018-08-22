package org.usfirst.frc.team1732.robot.commands.auto;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.LinkedList;
import java.util.function.BiConsumer;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.pathing.PathPoint;
import org.usfirst.frc.team1732.robot.pathing.Point;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowPath extends Command {

	public static LinkedList<int[]> list = new LinkedList<>();
	static {
		list.add(new int[] { 1 });
		list.add(new int[] { 1, 1 });
		list.add(new int[] { 1, 2, 1 });
		list.add(new int[] { 1, 3, 3, 1 });
		list.add(new int[] { 1, 4, 6, 4, 1 });
		list.add(new int[] { 1, 5, 10, 10, 5, 1 });
		list.add(new int[] { 1, 6, 15, 20, 15, 6, 1 });
	}

	public static int[] getPascal(int level) {
		while (list.size() <= level) {
			int[] nextRow = new int[list.size() + 1];
			int[] prev = list.get(list.size() - 1);
			nextRow[0] = 1;
			for (int i = 1; i < prev.length; i++) {
				nextRow[i] = prev[i - 1] + prev[i];
			}
			nextRow[list.size()] = 1;
			list.add(nextRow);
		}
		return list.get(level - 1);
	}

	public static Point[] getDerivate(Point[] values) {
		Point[] out = new Point[values.length - 1];
		for (int i = 0; i < out.length; i++) {
			out[i] = new Point(out.length * (values[i + 1].getX() - values[i].getX()),
					out.length * (values[i + 1].getY() - values[i].getY()));
		}
		return out;
	}

	private Point[] bezier;
	private Point[] derivative;
	private double t = 0;

	public FollowPath(Point... bezier) {
		requires(driveTrain);
		this.bezier = bezier;
		this.derivative = getDerivate(bezier);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		// // Debugging Prints
		// SmartDashboard.putNumber("Angle: ", Robot.navx.getHeading());
		// SmartDashboard.putNumber("Unrestricted Angle: ", Robot.ahrs.getAngle());
		// SmartDashboard.putNumber("Output: ", angleController.get());
		//
		// // Motor Control
		// driveTrain.drive(angleController.get(), -angleController.get());
		// stop = whenCloseEnough();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	protected void calcArcVals(PathPoint p, BiConsumer<Double, Double> output) {
		double r = dist(p) / (2 * Math.sin(p.getAngle() / 2));
		double inner = calculateInnerSpeed(r);

	}

	private static double calculateInnerSpeed(double radius) {
		return Math.PI * Math.pow(Robot.driveTrain.getRobotRadius(), 2) / Math.PI
				* Math.pow(radius + Robot.driveTrain.getRobotRadius(), 2);
	}

	protected static double dist(PathPoint p) {
		return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
	}

	protected PathPoint getPointAt(double t) {
		return new PathPoint(getAtDeCasteljau(t), getTangentAtDeCasteljau(t));
	}

	protected int[] pascalLevel;

	public Point getAtDeCasteljau(double t) {
		return getAtDeCasteljau(bezier, t)[0];
	}

	public Point getAtSimple(double t) {
		return getAtSimple(bezier, pascalLevel, t);
	}

	public Point getTangentAtSimple(double t) {
		return getAtSimple(derivative, pascalLevel, t);
	}

	public Point getTangentAtDeCasteljau(double t) {
		return getAtDeCasteljau(derivative, t)[0];
	}

	private static Point[] getAtDeCasteljau(Point[] points, double t) {
		if (points.length == 1) {
			return points;
		} else {
			Point[] newPoints = new Point[points.length - 1];
			for (int i = 0; i < newPoints.length; i++) {
				newPoints[i] = new Point((1 - t) * points[i].getX() + t * points[i + 1].getX(),
						(1 - t) * points[i].getY() + t * points[i + 1].getY());
			}
			return getAtDeCasteljau(newPoints, t);
		}
	}

	private static Point getAtSimple(Point[] points, int[] pascal, double t) {
		// function Bezier(n,t,w[]):
		Point sum = new Point(0, 0);
		for (int k = 0; k < points.length; k++) {
			sum.setX(sum.getX() + points[k].getX() * pascal[k] * Math.pow(1 - t, points.length - k) * Math.pow(t, k));
			sum.setY(sum.getY() + points[k].getY() * pascal[k] * Math.pow(1 - t, points.length - k) * Math.pow(t, k));
		}
		return sum;
	}
}
