/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1732.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Joysticks
	public static final int JOYSTICK_Y_AXIS = 1;
	public static final int JOYSTICK_Z_AXIS = 2;

	public static final int LEFT_JOYSTICK_USB = 0;
	public static final int RIGHT_JOYSTICK_USB = 1;

	// Drivetrain
	public static final int LEFT_MASTER_MOTOR_DEVICE_NUMBER = 0;
	public static final int LEFT_1_MOTOR_DEVICE_NUMBER = 1;
	public static final int LEFT_2_MOTOR_DEVICE_NUMBER = 2;

	public static final int RIGHT_MASTER_MOTOR_DEVICE_NUMBER = 15;
	public static final int RIGHT_1_MOTOR_DEVICE_NUMBER = 14;
	public static final int RIGHT_2_MOTOR_DEVICE_NUMBER = 13;
	
	// Manipulator
	public static final int LEFT_MANIP_MOTOR_NUMBER = 10;
	public static final int RIGHT_MANIP_MOTOR_NUMBER = 11;
}
