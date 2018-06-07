package org.usfirst.frc.team1732.robot.subsystems.encoders;

public abstract class EncoderBase {

	public EncoderReader makeReader() {
		return new EncoderReader(this);
	}

	/**
	 * @return current position in units determined by setDistancePerPulse
	 */
	public abstract double getPosition();

	/**
	 * @return current rate in units per 100ms
	 */
	public abstract double getRate();

	public abstract double getPulses();

	/**
	 * @param distancePerPulse
	 *            the distance per sensor unit
	 */
	public abstract void setDistancePerPulse(double distancePerPulse);

}