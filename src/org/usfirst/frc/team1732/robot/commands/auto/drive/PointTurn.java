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
public class PointTurn extends Command {

	/*
	 * To do list
	 * Debug DriveToAngleWithRadius (UNFINISHED)
	 */

	// Variable Declaration - PID
	public static double P = 0.056;
	public static double I = 0.00003;
	public static double D = 0.20;

	// Variable Declaration - Misc
	public double angle;
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

	public PointTurn(double angle) {
		requires(driveTrain);
		this.angle = angle;
	}

	protected void initialize() {
		//PID
		angleController.setSetpoint(angle);
		angleController.setAbsoluteTolerance(TOLERANCE);
		angleController.enable();
		
		//Hardware Reading
		Robot.ahrs.zeroYaw();
		
		driveTrain.drive(0, 0);
	}

	protected void execute() {
		//Debugging Prints
		SmartDashboard.putNumber("Angle: ", Robot.navx.getHeading());
		SmartDashboard.putNumber("Unrestricted Angle: ", Robot.ahrs.getAngle());
		SmartDashboard.putNumber("Output: ", angleController.get());
		
		//Motor Control
		driveTrain.drive(angleController.get(), -angleController.get());
		stop = whenCloseEnough();
	}

	protected boolean isFinished() {
		return stop;
	}

	protected void end() {
		angleController.disable();
		driveTrain.drive(0, 0);
	}

	protected boolean whenCloseEnough() {
		boolean isClose = Math.abs(Robot.navx.getHeading() - angle) <= TOLERANCE;
		return isClose;
	}
}
