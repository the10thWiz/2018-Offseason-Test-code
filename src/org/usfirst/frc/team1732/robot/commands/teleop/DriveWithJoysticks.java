package org.usfirst.frc.team1732.robot.commands.teleop;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.m_oi;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoysticks extends Command {
	public DriveWithJoysticks() {
		super("Drive With Joysticks");
		requires(driveTrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		driveTrain.driveWithJoysticks(m_oi.getLeftSpeed(), m_oi.getRightSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}
}
