package com.pomes.pathing;

public class Path {
	private double lookAhead = 0.01;
	private Bezier[] path;

	public Path(Bezier... path) {
		this.path = path;
	}

	public PathPoint getNext(PathPoint cur) {
		double t = cur.getT() + lookAhead;
		Bezier p = path[(int) t];
		return new PathPoint(t, p.getAt(t - Math.floor(t)), p.getTangentAt(t - Math.floor(t)));
	}

	public void setLookAhead(double lookAhead) {
		this.lookAhead = lookAhead;
	}
}
