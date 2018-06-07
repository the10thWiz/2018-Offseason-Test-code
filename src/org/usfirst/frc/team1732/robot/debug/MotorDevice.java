package org.usfirst.frc.team1732.robot.debug;

import org.usfirst.frc.team1732.robot.subsystems.encoders.EncoderBase;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class MotorDevice extends Device {
	private BaseMotorController motor;
	private EncoderBase encoder;
	
	private boolean isWorking = true;
	private boolean encoderZero = true;
	
	public MotorDevice(Debugging parent, BaseMotorController motor, EncoderBase encoder) {
		super(motor.getClass().getSimpleName() + " Id:" + motor.getDeviceID(),
				motor.getDeviceID(), parent);
		this.motor = motor;
		this.encoder = encoder;
		if (encoder == null) {
			isWorking = false;
		}
	}
	
	@Override
	public double getVoltage() {
		return motor.getBusVoltage();
	}
	
	@Override
	public double getTempature() {
		return motor.getTemperature();
	}
	
	@Override
	public boolean isWorking() {
		return isWorking;
	}
	
	@Override
	public void periodic() {
		super.periodic();
		if(isWorking) {
			if (encoder == null) {
				if (getVoltage() != 0 && getCurrent() != 0) {
					isWorking = true;
				}
			} else {
				if (encoderZero) {
					if (encoder.getPulses() != 0) {
						encoderZero = false;
					} else if (getVoltage() != 0 || getCurrent() != 0) {
						isWorking = false;
						sendError(new DeviceError("Encoder Error", "The Encoder is not reading correctly", 2, false));
					}
				}
			}
			if(motor.getLastError() != ErrorCode.OK) {
				isWorking = false;
				sendError(new DeviceError("Controller Error", motor.getLastError().toString(), 4, true));
			}
		}
	}
	
	@Override
	public String getOtherData() {
		return motor.getClass().getSimpleName() + " Mode:" + motor.getControlMode().toString();
	}
	
}
