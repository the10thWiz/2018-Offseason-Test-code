package org.usfirst.frc.team1732.robot.commands.auto.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToAngleWithRadius extends Command {

	/*
	 * To do list Debug and Test DriveToAngleWithRadius Begin Arm and Elevator
	 * Code Figure out how to collect data from the DriverStation for colors
	 * Begin path and measurements: Switch Center ** Switch-P-Switch Center **
	 * Scale Near ** Scale Far ** Switch-P-Scale Center *** Scale-W-Switch
	 * Near-Near *** Scale-W-Switch Far-Far **** Switch-W-Scale Near-Far *****
	 * Switch-P-Scale Near-Far *****
	 */

	// Variable Declaration - PID
	public static double P = 0.026;
	public static double I = 0.00003;
	public static double D = 0.20;

	// Variable Declaration - Misc
	public double angle;
	public double radius;
	public boolean forwards;
	public double direction = 1.0;
	public double leftSpeed = 0.0;
	public double rightSpeed = 0.0;
	public boolean stop = false;
	public final double TOLERANCE = 1.0;

	private static PIDController angleController = new PIDController(P, I, D, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.ahrs.getAngle();
		}

	}, d -> {
	});

	public DriveToAngleWithRadius(double angle, double radius, boolean forwards) {
		requires(driveTrain);
		this.angle = angle;
		this.radius = radius;
		this.forwards = forwards;
	}

	protected void initialize() {
		// Hardware Reading
		Robot.ahrs.zeroYaw();

		// Initialize Clockwise/CounterClockwise
		if (angle < 0) {
			rightSpeed = -1.0;
			leftSpeed = -calculateInnerSpeed(radius);
		} else {
			leftSpeed = 1.0;
			rightSpeed = calculateInnerSpeed(radius);
		}

		// Initialize Forwards/Backwards
		if (!forwards) {
			angle = -angle;
		}

		// PID
		angleController.setSetpoint(angle);
		angleController.setAbsoluteTolerance(TOLERANCE);
		angleController.enable();

		driveTrain.drive(0, 0);
	}

	protected void execute() {
		// Debugging Prints
		SmartDashboard.putNumber("Angle: ", Robot.navx.getHeading());
		SmartDashboard.putNumber("Left Multiplier: ", leftSpeed);
		SmartDashboard.putNumber("Right Multiplier: ", rightSpeed);

		// Motor Control
		driveTrain.drive(angleController.get() * leftSpeed * direction, angleController.get() * rightSpeed * direction);
		stop = whenCloseEnough();
	}

	protected boolean isFinished() {
		return stop;
	}

	protected void end() {
		angleController.disable();
		driveTrain.drive(0, 0);
	}

	private static double calculateInnerSpeed(double radius) {
		// Only pass positive radii to avoid outerDistance being 0
		double innerCircleRadius = radius - Robot.driveTrain.getRobotRadius();
		double outerCircleRadius = radius + Robot.driveTrain.getRobotRadius();
		double innerDistance = Math.PI * Math.pow(innerCircleRadius, 2);
		double outerDistance = Math.PI * Math.pow(outerCircleRadius, 2);
		if (innerCircleRadius < 0) {
			innerDistance = -innerDistance;
		}
		if (outerCircleRadius < 0) {
			outerDistance = -outerDistance;
		}

		SmartDashboard.putNumber("innerDistance: ", innerDistance);
		SmartDashboard.putNumber("outerDistance: ", outerDistance);

		// Speed = InnerDistance / OuterDistance
		double innerSpeed = innerDistance / outerDistance;
		return innerSpeed;
	}

	protected boolean whenCloseEnough() {
		boolean isClose = Math.abs(Robot.navx.getHeading() - angle) <= TOLERANCE;
		return isClose;
	}
}
