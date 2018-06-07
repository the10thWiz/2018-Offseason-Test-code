package org.usfirst.frc.team1732.robot.commands.teleop.manip;

import static org.usfirst.frc.team1732.robot.Robot.manipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OuttakeCube extends Command {

    public OuttakeCube() {
    	super("Outtake Cube");
		requires(manipulator);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		manipulator.outtake();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
