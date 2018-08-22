package com.pomes.pathing;

public class Vector {
	private double x;
	private double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * gets the 0 component
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * gets the 1 component
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * sets the 0 component
	 * 
	 * @param x
	 *            the value
	 */
	public Vector setX(double x) {
		this.x = x;
		return this;
	}
	
	/**
	 * sets the 1 component
	 * 
	 * @param y
	 *            the value
	 */
	public Vector setY(double y) {
		this.y = y;
		return this;
	}
	
	public int size() {
		return 2;
	}
	
	public Vector add(Vector v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector addOut(Vector v) {
		return new Vector(this.x + v.x, this.y + v.y);
	}
	
	public Vector sub(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vector subOut(Vector v) {
		return new Vector(this.x - v.x, this.y - v.y);
	}
	
	public Vector mult(double s) {
		this.x *= s;
		this.y *= s;
		return this;
	}
	
	public Vector negate() {
		this.x = -this.x;
		this.y = -this.y;
		return this;
	}
	
	public Vector multOut(double s) {
		return new Vector(this.x * s, this.y * s);
	}
	
	public Vector div(double s) {
		this.x /= s;
		this.y /= s;
		return this;
	}
	
	public Vector divOut(double s) {
		return new Vector(this.x / s, this.y / s);
	}
	
	public double mag() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public double distTo(Vector v) {
		return Math.sqrt((this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y));
	}
	
	public double distSqTo(Vector v) {
		return (this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y);
	}
	
	public boolean closeTo(Vector v, double dist) {
		return (this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y) < dist * dist;
	}
	
	public Vector normalize() {
		double m = mag();
		if (m == 0) {
			return this;
		}
		div(m);
		return this;
	}
	
	public Vector normalizeOut() {
		return divOut(mag());
	}
	
	public double dot(Vector v) {
		return (this.x * v.x) + (this.y * v.y);
	}
	
	public Vector rotate(double angle) {
		return new Vector(this.x * Math.cos(angle) - this.y * Math.sin(angle),
				this.x * Math.sin(angle) + this.y * Math.cos(angle));
	}
	
	public Vector rotate90() {
		return new Vector(-this.y, this.x);
	}
	
	public Vector rotaten90() {
		return new Vector(this.y, -this.x);
	}
	
	public double angleOf() {
		return Math.atan2(this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "("+this.x+", "+this.y+")";
	}
	
	public Vector set(Vector v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}
	
	public Vector clone() {
		return new Vector(this.x, this.y);
	}
	
	public Vector clamp(double x, double y, double width, double height) {
		if (this.x < x) {
			this.x = x;
		}
		if (this.y < y) {
			this.y = y;
		}
		if (this.x > x + width) {
			this.x = x + width;
		}
		if (this.y > y + height) {
			this.y = y + height;
		}
		return this;
	}
	
	public double angleBetween(Vector v) {
		return Math.acos(dot(v) / (mag() * v.mag()));
	}
	
}
