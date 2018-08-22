package org.usfirst.frc.team1732.robot.pathing;

public class PathPoint {
	private double x;
	private double y;
	private double angle;

	public PathPoint(double x, double y, double angle) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public PathPoint(Point p, double angle) {
		super();
		this.x = p.getX();
		this.y = p.getY();
		this.angle = angle;
	}

	public PathPoint(Point p, Point angle) {
		super();
		this.x = p.getX();
		this.y = p.getY();
		this.angle = Math.atan2(angle.getX(), angle.getY());
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getAgnle() {
		return this.angle;
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
