package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.m_oi;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Toggle extends InstantCommand {

	public boolean toggled = false;
	
    public Toggle() {
        super();
        requires(driveTrain);
    }

    // Called once when the command executes
    protected void initialize() {
    	if(!toggled){
    		m_oi.reverseJoysticks();
    		toggled = true;
    	}
    	else{
    		m_oi.returnJoysticks();
    		toggled = false;
    	}
    	driveTrain.toggleLimiter();
		SmartDashboard.putBoolean("Reverse Drive: ", toggled);
    }

}
