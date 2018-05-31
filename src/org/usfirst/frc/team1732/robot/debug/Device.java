package org.usfirst.frc.team1732.robot.debug;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.DriverStation;

public class Device {
	private static final double SPIKE_MIN_AMPS = 10;
	
	protected final int channel;
	protected final Debugging parent;
	protected final String name;
	
	protected double base_current = 0.0;
	protected int spike_value = 0;
	
	private Consumer<DeviceError> onError;
	
	public Device(int channel, Debugging parent) {
		this.channel = channel;
		this.parent = parent;
		this.name = "Unknown Device";
	}
	
	protected Device(String name, int channel, Debugging parent) {
		this.channel = channel;
		this.parent = parent;
		this.name = name;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public String getName() {
		return name;
	}
	
	public double getCurrent() {
		return parent.getCurrent(channel);
	}
	
	public double getVoltage() {
		return 0;
	}
	
	public double getTempature() {
		return 0;
	}
	
	public boolean isWorking() {
		return false;
	}
	
	public String getOtherData() {
		return "";
	}
	
	public void periodic() {
		if (DriverStation.getInstance().isDisabled()) {
			base_current = getCurrent();
			spike_value = 0;
		} else {
			// a method to check the current value, and keep track of spikes
			// not the best mehtod, and somewhat pointless
			// if(getCurrent() > base_current + SPIKE_MIN_AMPS) {
			// spike_value += 5;
			// }
			// spike_value--;
			// if(spike_value < 0) {
			// spike_value = 0;
			// }
		}
	}
	
	public boolean isRunning() {
		return getCurrent() > base_current;
	}
	
	protected void sendError(DeviceError e) {
		parent.addTask(new Task<DeviceError>(onError, e));
	}
	
}
