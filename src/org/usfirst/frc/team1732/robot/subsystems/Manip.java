/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1732.robot.subsystems;

import java.util.function.Consumer;

import org.usfirst.frc.team1732.robot.debug.Console;
import org.usfirst.frc.team1732.robot.debug.Dashboard;
import org.usfirst.frc.team1732.robot.debug.Debugging;
import org.usfirst.frc.team1732.robot.debug.Device;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Manip extends Subsystem implements Consumer<Boolean> {
	public final VictorSPX victorA;
	public final VictorSPX victorB;
	
	private final Device da;
	public Manip() {
		victorA = new VictorSPX(10);
		victorB = new VictorSPX(11);
		Console.enableGraphData();
		da = Debugging.getDevice(victorA.getDeviceID());
		disable();
		Dashboard.add("Current", da::getCurrent);
	}

	public void enable() {
		victorA.set(ControlMode.PercentOutput, 1);
		victorB.set(ControlMode.PercentOutput, 1);
	}
	public void disable() {
		victorA.set(ControlMode.PercentOutput, 0);
		victorB.set(ControlMode.PercentOutput, 0);
	}
	
	public void initDefaultCommand() {
	}
	@Override
	public void periodic() {
//		Console.graph(Debugging.debug.getCurrent());
//		Console.graph(da.getCurrent());
	}

	@Override
	public void accept(Boolean t) {
		if(t) {
			enable();
		}else {
			disable();
		}
	}
}
