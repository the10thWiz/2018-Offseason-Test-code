package org.usfirst.frc.team1732.robot.commands.auto;

import org.usfirst.frc.team1732.robot.commands.auto.drive.DriveWithEncoders;
import org.usfirst.frc.team1732.robot.commands.auto.drive.PointTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveTest extends CommandGroup {

    public DriveTest() {
    	addSequential(new DriveWithEncoders(120));
    	addSequential(new PointTurn(180));
    	addSequential(new DriveWithEncoders(80));
    }
}
