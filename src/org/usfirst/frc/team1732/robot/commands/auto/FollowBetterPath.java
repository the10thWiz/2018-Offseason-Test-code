package org.usfirst.frc.team1732.robot.commands.auto;

import java.util.function.BiConsumer;

import org.usfirst.frc.team1732.robot.Robot;

import com.pomes.pathing.Path;
import com.pomes.pathing.PathPoint;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowBetterPath extends Command {

	private Path path;
	private PathPoint cur;

	public FollowBetterPath(Path path) {
		this.path = path;
	}

	@Override
	protected void initialize() {
		cur = new PathPoint(0, null, null);
	}

	@Override
	protected void execute() {
		// cur = path.getNext(cur);
		// // Debugging Prints
		// SmartDashboard.putNumber("Angle: ", Robot.navx.getHeading());
		// SmartDashboard.putNumber("Unrestricted Angle: ", Robot.ahrs.getAngle());
		//
		// // Motor Control
		// driveTrain.drive(angleController.get(), -angleController.get());
		// stop = whenCloseEnough();

		calculateSpeed(cur, path.getNext(cur), Robot.driveTrain::drive);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrain.stop();
	}

	private static double calculateInnerSpeed(double radius) {
		return Math.PI * Math.pow(Robot.driveTrain.getRobotRadius(), 2) / Math.PI
				* Math.pow(radius + Robot.driveTrain.getRobotRadius(), 2);
	}

	private static PathPoint calculateSpeed(PathPoint cur, PathPoint next, BiConsumer<Double, Double> output) {
		double radius = (.5 * cur.dist(next)) / Math.sin(.5 * cur.angleTo(next));
		if (cur.angleTo(next) < 0) {
			output.accept(calculateInnerSpeed(radius), 1d);
		} else {
			output.accept(1d, calculateInnerSpeed(radius));
		}
		return next;
	}

}
