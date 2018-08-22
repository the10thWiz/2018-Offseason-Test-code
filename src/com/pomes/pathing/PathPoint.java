package com.pomes.pathing;

public class PathPoint {
	private double t;
	private Vector pos;
	private Vector dir;
	private double angle;
	
	public PathPoint(double t, Vector pos, Vector dir) {
		this.t = t;
		this.pos = pos;
		this.dir = dir;
		this.angle = dir.angleOf();
	}
	
	public double dist(PathPoint p) {
		return this.pos.distTo(p.pos);
	}
	
	public double angleTo(PathPoint p) {
		return p.angle - this.angle;
	}
	
	public double getT() {
		return t;
	}
}
