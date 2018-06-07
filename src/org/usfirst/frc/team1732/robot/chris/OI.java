/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1732.robot.chris;

import org.usfirst.frc.team1732.robot.commands.Toggle;
import org.usfirst.frc.team1732.robot.commands.teleop.manip.IntakeCube;
import org.usfirst.frc.team1732.robot.commands.teleop.manip.ManipRest;
import org.usfirst.frc.team1732.robot.commands.teleop.manip.ManipulatorBetaControl;
import org.usfirst.frc.team1732.robot.commands.teleop.manip.OuttakeCube;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// Joystick Declaration (Switches ports for override)
	private Joystick left = new Joystick(RobotMap.LEFT_JOYSTICK_USB);
	private Joystick right = new Joystick(RobotMap.RIGHT_JOYSTICK_USB);

	// Button Declaration
	// Intake
	private final Button intake = new JoystickButton(right, 3);
	private final Button outtake = new JoystickButton(right, 2);
	private final Button betaIntake = new JoystickButton(right, 1);
	
	// Toggle
	private final Button toggle = new JoystickButton(left, 2);

	public OI() {
		// Manipulator
		intake.whenPressed(new IntakeCube());
		intake.whenReleased(new ManipRest());
		outtake.whenPressed(new OuttakeCube());
		outtake.whenReleased(new ManipRest());
		betaIntake.whenPressed(new ManipulatorBetaControl());
		betaIntake.whenReleased(new ManipRest());
		
		//Toggle
		toggle.whenPressed(new Toggle());
	}

	public double getLeftSpeed() {
		// return -controller.getRawAxis(1);// for use with game controller
		return -left.getRawAxis(RobotMap.JOYSTICK_Y_AXIS);
		// return -buttons.getRawAxis(1);
	}

	public double getRightSpeed() {
		// return -controller.getRawAxis(3);// for use with game controller
		return -right.getRawAxis(RobotMap.JOYSTICK_Y_AXIS);
		// return -buttons.getRawAxis(3);
	}

	public double getIntakeOutput() {
		return left.getRawAxis(RobotMap.JOYSTICK_Z_AXIS);
	}
	
	public void reverseJoysticks(){
		left = new Joystick(RobotMap.RIGHT_JOYSTICK_USB);
		right = new Joystick(RobotMap.LEFT_JOYSTICK_USB);
	}
	
	public void returnJoysticks(){
		left = new Joystick(RobotMap.LEFT_JOYSTICK_USB);
		right = new Joystick(RobotMap.RIGHT_JOYSTICK_USB);
	}

}
