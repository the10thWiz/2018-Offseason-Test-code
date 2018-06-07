package org.usfirst.frc.team1732.robot.commands.auto.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithEncoders extends Command {
	
	//Variable Declaration - PID
	public static double P = 0.026;
	public static double I = 0.00012;
	public static double D = 0.20;
	
	//Variable Declaration - Angle Control (Amplifies the correction of angle control the smaller it is [1.0 or less])
	public static final double JANK_AMP = 0.89;
	
	//Variable Declaration - Misc (Janks are not finals, I just like caps lock)
	public double inches;
	public double angleControl;
	public double L_JANK;
	public double R_JANK;
	public boolean stop = false;
	public final double TOLERANCE = 1.0;

	//Hardware Declaration
	private static PIDController leftDistance = new PIDController(P, I, D, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return driveTrain.leftEncoder.getPosition();
		}
		
	},d->{});
	
	private static PIDController rightDistance = new PIDController(P, I, D, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return driveTrain.rightEncoder.getPosition();
		}
		
	},d->{});
	
    public DriveWithEncoders(double inches) {
        requires(driveTrain);
        this.inches = inches;
    }

    protected void initialize() {
    	//Encoders
    	Robot.driveTrain.resetEncoders();
    	leftDistance.setSetpoint(inches);
    	leftDistance.setAbsoluteTolerance(TOLERANCE);
    	leftDistance.enable();
    	rightDistance.setSetpoint(inches);
    	rightDistance.setAbsoluteTolerance(TOLERANCE);
    	rightDistance.enable();
    	
    	//NavX
		Robot.ahrs.zeroYaw();
    }

    protected void execute() {
    	//Debugging Prints
		SmartDashboard.putNumber("Left Distance: ", driveTrain.leftEncoder.getPosition());
		SmartDashboard.putNumber("Angle Control: ", angleControl);
		SmartDashboard.putNumber("RJANK: ", R_JANK);
		SmartDashboard.putNumber("LJANK: ", L_JANK);
		SmartDashboard.putBoolean("Finished", whenCloseEnough());
		
		//Angle Control
		angleControl = calculateAngleCorrection() * JANK_AMP;
			// if 0 < angle < 180 (Deviating clockwise)
		if(angleControl < 0.5){
			R_JANK = 1.0;
			L_JANK = 1.0 - angleControl;
		}
			// if 180 < angle < 360 (Deviating counter-clockwise)
		else if(angleControl > 0.5){
			R_JANK = angleControl;
			L_JANK = 1.0;
		}
			// if angle == 180, turn around
		else{
			R_JANK = 0;
			L_JANK = 1.0;
		}
		
		//Motor Control (Both on leftDistance.get())
		driveTrain.drive(leftDistance.get() * L_JANK, leftDistance.get() * R_JANK);
    	stop = whenCloseEnough();
    }

    protected boolean isFinished() {
        return stop;
    }

    protected void end() {
    	leftDistance.disable();
    	rightDistance.disable();
    	driveTrain.drive(0, 0);
    }
    
    //Angle Control Attempt #2 - IF angleControl > 1/2, apply to a certain side after subtracting from 1
  	private static double calculateAngleCorrection(){
  		double angleControl = Robot.navx.getHeading() / 360.0;
  		return angleControl;
  	}
    
    protected boolean whenCloseEnough(){
    	//Only currently using closeLeft because of failure to remain straight
    	boolean closeLeft = Math.abs(driveTrain.leftEncoder.getPosition() - inches) <= TOLERANCE;
		return closeLeft;
    }
}
