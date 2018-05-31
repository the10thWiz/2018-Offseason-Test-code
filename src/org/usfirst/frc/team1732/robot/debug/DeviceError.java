package org.usfirst.frc.team1732.robot.debug;

public class DeviceError {
	public final String error;
	public final String message;
	public final int severity;
	public final boolean isFatal;
	
	public DeviceError(String error, String message, int severity, boolean isFatal) {
		this.error = error;
		this.message = message;
		this.severity = severity;
		this.isFatal = isFatal;
	}
	
}
