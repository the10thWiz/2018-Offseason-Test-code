package com.pomes.pathing;

import java.util.LinkedList;

public class Bezier {
	protected int[] pascalLevel;
	
	protected Vector[] bezier;
	protected Bezier derivative;
	
	public Bezier(Vector... values) {
		bezier = values;
		if (values.length > 1) {
			derivative = new Bezier(getDerivate(values));
		}
		pascalLevel = getPascal(values.length);
	}
	
	public Bezier axisAlign() {
		Vector[] v = new Vector[bezier.length];
		for (int i = bezier.length - 1; i >= 0; i--) {
			v[i] = bezier[i].subOut(bezier[0]);
		}
		double angle = bezier[bezier.length - 1].angleOf();
		for (int i = bezier.length - 1; i >= 0; i--) {
			v[i].rotate(angle);
		}
		return new Bezier(v);
	}
	
	public Vector getAtDeCasteljau(double t) {
		return getAtDeCasteljau(bezier, t)[0];
	}
	
	public Vector getAtSimple(double t) {
		return getAtSimple(bezier, pascalLevel, t);
	}
	
	public Vector getAt(double t) {
		return getAtDeCasteljau(t);
	}
	
	public Vector getTangentAt(double t) {
		return derivative.getAtDeCasteljau(t);
	}
	
	public Vector getNormalAt(double t) {
		return derivative.getAtDeCasteljau(t).rotate90();
	}
	
	public Vector getRNormalAt(double t) {
		return derivative.getAtDeCasteljau(t).rotaten90();
	}
	
	public double getXAt(double t) {
		return getAtSimple(t).getX();
	}
	
	public double getYAt(double t) {
		return getAtSimple(t).getY();
	}
	
	public double getCurvature(double t) {
		Vector d1 = derivative.getAt(t);
		Vector d2 = derivative.derivative.getAt(t);
		
		return d1.getX() * d2.getY() - d1.getY() * d2.getX();
	}
	
	public double[] getInflectionPoints() {
		double a = bezier[2].getX() * bezier[1].getY();
		double b = bezier[3].getX() * bezier[1].getY();
		double c = bezier[1].getX() * bezier[2].getY();
		double d = bezier[3].getX() * bezier[2].getY();
		
		double x = 18 * (-3 * a + 2 * b - d);
		double y = 18 * (3 * a - b - 3 * c);
		double z = 18 * (c - a);
		
		double disc = y * y - 4 * z * x;
		if (disc < 0 || x == 0) {
			return new double[0];
		} else if (disc == 0) {
			return new double[] { (-y + Math.sqrt(disc)) / 2 * x };
		} else {
			return new double[] { (-y + Math.sqrt(disc)) / 2 * x, (-y - Math.sqrt(disc)) / 2 * x };
		}
	}
	
	public Vector getLastPointCanonicalForm() {
		Vector v = new Vector(0, 0);
		double f1 = bezier[2].getY() / bezier[1].getY();
		double f2 = bezier[3].getY() / bezier[1].getY();
		v.setX((bezier[3].getX() - bezier[1].getX() * f2)
				/ (bezier[2].getX() - bezier[1].getX() * f1));
		v.setY(f2 + (1 - f1) * v.getX());
		return v;
	}
	
	public String featuresCanonicalForm() {
		Vector v = getLastPointCanonicalForm();
		
		if (v.getY() < 1) {
			return "Single Inflection";
		} else if (v.getX() > 1) {
			return "Plain Curve";
		} else if (v.getY() < (-v.getX() * v.getX() + 2 * v.getX() + 3) / 4) {
			return "Double Inflection";
		} else if (v.getX() <= 0) {
			if (v.getY() < (-v.getX() * v.getX() + 3 * v.getX()) / 3) {
				return "Loop";
			} else {
				return "Plain Curve";
			}
		} else {
			if (v.getY() < (Math.sqrt(3 * (4 * v.getX() - v.getX() * v.getX())) - v.getX()) / 2) {
				return "Loop";
			} else {
				return "Plain Curve";
			}
		}
	}
	
	public LUTNumber createDistanceLUT(int size) {
		@SuppressWarnings("unchecked")
		Entry<Double, Double>[] entries = new Entry[size+1];
		double distance = 0;
		double time = 0;
		double increment = 1.0/size;
		Vector last = getAtDeCasteljau(0);
		for (int i = 0; i < size + 1; i++) {
			Vector cur = getAtDeCasteljau(time);
			distance+= last.distTo(cur);
			last = cur;
			entries[i] = new Entry<Double, Double>(time, distance);
			time+= increment;
		}
		return new LUTNumber(entries);
	}
	
	public Vector getPoint(int i) {
		return bezier[i];
	}
	
	public int size() {
		return bezier.length;
	}
	
	private static Vector[] getAtDeCasteljau(Vector[] points, double t) {
		if (points.length == 1) {
			return points;
		} else {
			Vector[] newPoints = new Vector[points.length - 1];
			for (int i = 0; i < newPoints.length; i++) {
				newPoints[i] = new Vector((1 - t) * points[i].getX() + t * points[i + 1].getX(),
						(1 - t) * points[i].getY() + t * points[i + 1].getY());
			}
			return getAtDeCasteljau(newPoints, t);
		}
	}
	
	private static Vector getAtSimple(Vector[] points, int[] pascal, double t) {
		// function Bezier(n,t,w[]):
		Vector sum = new Vector(0, 0);
		for (int k = 0; k < points.length; k++) {
			sum.setX(sum.getX() + points[k].getX() * pascal[k] * Math.pow(1 - t, points.length - k)
					* Math.pow(t, k));
			sum.setY(sum.getY() + points[k].getY() * pascal[k] * Math.pow(1 - t, points.length - k)
					* Math.pow(t, k));
		}
		return sum;
	}
	
	public static Vector[] getDerivate(Vector[] values) {
		Vector[] out = new Vector[values.length - 1];
		for (int i = 0; i < out.length; i++) {
			out[i] = new Vector(out.length * (values[i + 1].getX() - values[i].getX()),
					out.length * (values[i + 1].getY() - values[i].getY()));
		}
		return out;
	}
	
	public static LinkedList<int[]> list = new LinkedList<>();
	static {
		list.add(new int[] { 1 });
		list.add(new int[] { 1, 1 });
		list.add(new int[] { 1, 2, 1 });
		list.add(new int[] { 1, 3, 3, 1 });
		list.add(new int[] { 1, 4, 6, 4, 1 });
		list.add(new int[] { 1, 5, 10, 10, 5, 1 });
		list.add(new int[] { 1, 6, 15, 20, 15, 6, 1 });
	}
	
	public static int[] getPascal(int level) {
		while (list.size() <= level) {
			int[] nextRow = new int[list.size() + 1];
			int[] prev = list.get(list.size() - 1);
			nextRow[0] = 1;
			for (int i = 1; i < prev.length; i++) {
				nextRow[i] = prev[i - 1] + prev[i];
			}
			nextRow[list.size()] = 1;
			list.add(nextRow);
		}
		return list.get(level - 1);
	}
}
