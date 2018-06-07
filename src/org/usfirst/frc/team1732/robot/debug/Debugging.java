package org.usfirst.frc.team1732.robot.debug;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.usfirst.frc.team1732.robot.subsystems.encoders.EncoderBase;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class Debugging implements Sendable {
	public static final Dashboard dash = new Dashboard();
	public static final Debugging debug = new Debugging();
	
	protected static void runDebuggingTasks() {
		debug.update();
		dash.update();
	}
	
	protected static void runErrorTasks() {
		debug.handleError();
	}
	
	private Queue<Task<DeviceError>> tasks;
	private Consumer<DeviceError> onError;
	
	private PowerDistributionPanel panel;
	private double[] current;
	private double[] voltage;
	private double[] tempature;
	private boolean[] isWorking;
	private String[] names;
	private String[] data;
	
	private Device[] devices;
	
	public Debugging() {
		tasks = new ConcurrentLinkedQueue<>();
		
		panel = new PowerDistributionPanel();
		devices = new Device[SensorBase.kPDPChannels];
		
		for (int i = 0; i < devices.length; i++) {
			devices[i] = new Device(i, this);
		}
		
		current = new double[devices.length];
		voltage = new double[devices.length];
		tempature = new double[devices.length];
		isWorking = new boolean[devices.length];
		Dashboard.add("PDP", this);
//		Dashboard.add("PDP - actual", panel);

		Notifier notifier = new Notifier(Debugging::runDebuggingTasks);
		int loopTimeMs = 100;
		notifier.startPeriodic(loopTimeMs / 1000.0);
	}
	
	/**
	 * Gets the current of the various PDP devices, as an array of doubles, indexed
	 * by channel.
	 * 
	 * @return The currents, in amps
	 */
	public double[] getCurrent() {
		for (int i = 0; i < devices.length; i++) {
			current[i] = devices[i].getCurrent();
		}
		return current;
	}
	
	/**
	 * Gets the voltage of the various PDP devices, as an array of doubles, indexed
	 * by channel.
	 * 
	 * @return The voltage, in volts
	 */
	public double[] getVoltage() {
		for (int i = 0; i < devices.length; i++) {
			voltage[i] = devices[i].getVoltage();
		}
		return voltage;
	}
	
	/**
	 * Gets the temperature of the various PDP devices, as an array of doubles,
	 * indexed by channel.
	 * 
	 * @return The temperature, in Celsius
	 */
	public double[] getTemperature() {
		for (int i = 0; i < devices.length; i++) {
			tempature[i] = devices[i].getTempature();
		}
		return tempature;
	}
	
	/**
	 * Gets the Names of the various PDP devices, as an array of Strings, indexed by
	 * channel.
	 * 
	 * @return The names
	 */
	public String[] getNames() {
		for (int i = 0; i < devices.length; i++) {
			names[i] = devices[i].getName();
		}
		return names;
	}
	
	/**
	 * Gets the other data of the various PDP devices, as an array of Strings,
	 * indexed by channel.
	 * 
	 * @return The data
	 */
	public String[] getData() {
		for (int i = 0; i < devices.length; i++) {
			data[i] = devices[i].getOtherData();
		}
		return data;
	}
	
	/**
	 * Gets whether each device is working, as an array of booleans, indexed by
	 * channel
	 * 
	 * @return Whether they are working
	 */
	public boolean[] getWorking() {
		for (int i = 0; i < devices.length; i++) {
			isWorking[i] = devices[i].isWorking();
		}
		return isWorking;
	}
	
	protected double getCurrent(int channel) {
		return panel.getCurrent(channel);
	}
	
	/**
	 * Gets the voltage of the PDP
	 * 
	 * @return The voltage, in Volts
	 */
	public double getTotalVoltage() {
		return panel.getVoltage();
	}
	
	/**
	 * Gets the temperature of the PDP
	 * 
	 * @return The temperature in Celsius
	 */
	public double getTotalTemperature() {
		return panel.getTemperature();
	}
	
	/**
	 * Gets the current of the PDP
	 * 
	 * @return The current in amps
	 */
	public double getTotalCurrent() {
		return panel.getTotalCurrent();
	}
	
	/**
	 * Gets the energy being used by the PDP
	 * 
	 * @return The total energy, in Joules
	 */
	public double getTotalEnergy() {
		return panel.getTotalEnergy();
	}
	
	/**
	 * Gets the power being used by the PDP
	 * 
	 * @return The total power, in Watts
	 */
	public double getTotalPower() {
		return panel.getTotalPower();
	}
	
	private String name = "Debugging";
	private String subsystem = "void";
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getSubsystem() {
		return subsystem;
	}
	
	@Override
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.addDoubleProperty("current", this::getTotalCurrent, this::throwError);
		builder.addDoubleProperty("voltage", this::getTotalVoltage, this::throwError);
		builder.addDoubleProperty("tempature", this::getTotalTemperature, this::throwError);
		builder.addDoubleProperty("energy", this::getTotalEnergy, this::throwError);
		builder.addDoubleProperty("power", this::getTotalPower, this::throwError);
		
		builder.addDoubleArrayProperty("currentVals", this::getCurrent, this::throwError);
		builder.addDoubleArrayProperty("voltageVals", this::getVoltage, this::throwError);
		builder.addDoubleArrayProperty("temperatureVals", this::getTemperature,
				this::throwError);
		builder.addBooleanArrayProperty("workingVals", this::getWorking, this::throwError);
	}
	
	private void throwError(Object o) {
		System.out.println("Properties of the robot state are readonly.");
	}
	
	protected void update() {
		for (int i = 0; i < devices.length; i++) {
			devices[i].periodic();
		}
	}
	
	protected void handleError() {
		while (!tasks.isEmpty()) {
			tasks.poll().run(onError);
		}
	}
	
	protected void addTask(Task t) {
		tasks.add(t);
	}
	
	protected void setDevice(Device d) {
		devices[d.getChannel()] = d;
	}
	
	public static void onError(Consumer<DeviceError> onError) {
		debug.onError = onError;
	}
	
	public static void bindMotor(String name, BaseMotorController motor, EncoderBase encoder) {
		debug.setDevice(new MotorDevice(debug, motor, encoder));
	}
	
	public static void bindMotor(String name, BaseMotorController motor) {
		debug.setDevice(new MotorDevice(debug, motor, null));
	}
	
	public static Device getDevice(int channel) {
		return debug.devices[channel];
	}
}
