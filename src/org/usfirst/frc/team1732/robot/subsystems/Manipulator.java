package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.teleop.manip.ManipRest;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Manipulator extends Subsystem {

	public static final String NAME = "Manipulator";

	public Manipulator() {
		super(NAME);
		configureMotors();
	}

	// Motors
	public final static VictorSPX leftIntake = new VictorSPX(RobotMap.LEFT_MANIP_MOTOR_NUMBER);
	public final static VictorSPX rightIntake = new VictorSPX(RobotMap.RIGHT_MANIP_MOTOR_NUMBER);
	
	private void configureMotors() {
		// Inversion
		leftIntake.setInverted(true);
		rightIntake.setInverted(false);

		//Set Brake Mode
		leftIntake.setNeutralMode(NeutralMode.Brake);
		rightIntake.setNeutralMode(NeutralMode.Brake);
		
		// Initialize at rest
		leftIntake.set(ControlMode.PercentOutput, 0);
		rightIntake.set(ControlMode.PercentOutput, 0);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ManipRest());
	}
	
	public void intake(){
    	leftIntake.set(ControlMode.PercentOutput, 1);
    	rightIntake.set(ControlMode.PercentOutput, 1);
	}
	
	public void outtake(){
    	leftIntake.set(ControlMode.PercentOutput, -1);
    	rightIntake.set(ControlMode.PercentOutput, -1);
	}
	
	public void rest(){
    	leftIntake.set(ControlMode.PercentOutput, 0);
    	rightIntake.set(ControlMode.PercentOutput, 0);
	}
	
	public void beta(double speed){
		leftIntake.set(ControlMode.PercentOutput, speed);
    	rightIntake.set(ControlMode.PercentOutput, speed);
	}
	
}
