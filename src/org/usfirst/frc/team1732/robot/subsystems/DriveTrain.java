package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.teleop.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.subsystems.encoders.TalonEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	public static final String NAME = "Drive Train";

	public DriveTrain() {
		super(NAME);
		configureMotors();
		configureEncoders();
	}

	// Sets inches per encoder pulse (Note: Keep negative to align with direction of
	// robot)
	private final double INCHES_PER_PULSE = -1.0 / 482;

	// Declares robot width (Needs to be measured)
	private final double ROBOT_WIDTH = 26;

	// Limiter limits the speed. Keep below 1.0 (Percentage) (Turns negative for
	// override)
	private static double limiter = 1.0;

	// Motors
	public final static TalonSRX leftMaster = new TalonSRX(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	public final static VictorSPX left1 = new VictorSPX(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	public final static VictorSPX left2 = new VictorSPX(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);

	public final static TalonSRX rightMaster = new TalonSRX(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	public final static VictorSPX right1 = new VictorSPX(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	public final static VictorSPX right2 = new VictorSPX(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	// Encoder
	public TalonEncoder leftEncoder;
	public TalonEncoder rightEncoder;

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Left Encoder Distance: ", leftEncoder.getPosition());
		SmartDashboard.putNumber("Right Encoder Distance: ", rightEncoder.getPosition());
		SmartDashboard.putNumber("NavX Heading: ", Robot.navx.getHeading());
	}

	private void configureMotors() {
		// Inversion
		leftMaster.setInverted(true);
		left1.setInverted(true);
		left2.setInverted(true);
		rightMaster.setInverted(false);

		// Set Followers
		left1.set(ControlMode.Follower, leftMaster.getDeviceID());
		left2.set(ControlMode.Follower, leftMaster.getDeviceID());
		right1.set(ControlMode.Follower, rightMaster.getDeviceID());
		right2.set(ControlMode.Follower, rightMaster.getDeviceID());

		// Set Brake Mode
		leftMaster.setNeutralMode(NeutralMode.Brake);
		left1.setNeutralMode(NeutralMode.Coast);
		left2.setNeutralMode(NeutralMode.Coast);

		rightMaster.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Coast);
		right2.setNeutralMode(NeutralMode.Coast);

		// Initialize at rest
		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);
	}

	private void configureEncoders() {
		rightEncoder = new TalonEncoder(leftMaster, FeedbackDevice.QuadEncoder);
		leftEncoder = new TalonEncoder(rightMaster, FeedbackDevice.QuadEncoder);
		leftEncoder.setPhase(true);
		rightEncoder.setPhase(true);
		leftEncoder.setDistancePerPulse(INCHES_PER_PULSE);
		rightEncoder.setDistancePerPulse(INCHES_PER_PULSE);
		rightEncoder.zero();
		leftEncoder.zero();
	}

	// Default Command
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	// Methods - teleop
	public void driveWithJoysticks(double left, double right) {
		leftMaster.set(ControlMode.PercentOutput, limiter * left);
		rightMaster.set(ControlMode.PercentOutput, limiter * right);
	}

	// Methods - auton
	public void drive(double left, double right) {
		leftMaster.set(ControlMode.PercentOutput, limiter * left);
		rightMaster.set(ControlMode.PercentOutput, limiter * right);
	}

	// Methods - helper
	public void resetEncoders() {
		rightEncoder.zero();
		leftEncoder.zero();
	}

	public double getRobotRadius() {
		return ROBOT_WIDTH / 2;
	}

	public void toggleLimiter() {
		limiter = -limiter;
	}

	public void stop() {
		drive(0, 0);
	}
}
