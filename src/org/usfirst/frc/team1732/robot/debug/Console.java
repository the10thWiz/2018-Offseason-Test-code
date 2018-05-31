package org.usfirst.frc.team1732.robot.debug;

import edu.wpi.first.wpilibj.command.Command;

public class Console {
	private static Mode mode = Mode.SIMPLE;
	
	public static void logAlways(Command c, Object info) {
		println(c, info.toString(), Mode.OFF);
	}
	
	public static void logAlways(Object info) {
		println(null, info.toString(), Mode.OFF);
	}
	
	public static void logAlways(Command c, String format, Object... args) {
		println(c, String.format(format, args), Mode.OFF);
	}
	
	public static void logAlways(String format, Object... args) {
		println(null, String.format(format, args), Mode.OFF);
	}
	
	// ----- SIMPLE INFO -----
	public static void logSimpleInfo(Command c, Object info) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, info.toString(), Mode.SIMPLE);
		}
	}
	
	public static void logSimpleInfo(Object info) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(null, info.toString(), Mode.SIMPLE);
		}
	}
	
	public static void logSimpleInfo(Command c, String format, Object... args) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, String.format(format, args), Mode.SIMPLE);
		}
	}
	
	public static void logSimpleInfo(String format, Object... args) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(null, String.format(format, args), Mode.SIMPLE);
		}
	}
	
	// ----- LOG START -----
	public static void logStart(Command c, Object info) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, "Starting " + info.toString(), Mode.SIMPLE);
		}
	}
	
	public static void logStart(Command c) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, "Starting", Mode.SIMPLE);
		}
	}
	
	public static void logStart(Command c, String format, Object... args) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, "Starting " + String.format(format, args), Mode.SIMPLE);
		}
	}
	
	// ----- LOG END -----
	public static void logEnd(Command c, Object info) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, "Finished " + info.toString(), Mode.SIMPLE);
		}
	}
	
	public static void logEnd(Command c) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, "Finished", Mode.SIMPLE);
		}
	}
	
	public static void logEnd(Command c, String format, Object... args) {
		if (mode.val >= Mode.SIMPLE.val) {
			println(c, "Finished " + String.format(format, args), Mode.SIMPLE);
		}
	}
	
	// ----- DETAILED INFO -----
	public static void logDetailedInfo(Command c, Object info) {
		if (mode.val >= Mode.DETAILED.val) {
			println(c, info.toString(), Mode.DETAILED);
		}
	}
	
	public static void logDetailedInfo(Object info) {
		if (mode.val >= Mode.DETAILED.val) {
			println(null, info.toString(), Mode.DETAILED);
		}
	}
	
	public static void logDetailedInfo(Command c, String format, Object... args) {
		if (mode.val >= Mode.DETAILED.val) {
			println(c, String.format(format, args), Mode.DETAILED);
		}
	}
	
	public static void logDetailedInfo(String format, Object... args) {
		if (mode.val >= Mode.DETAILED.val) {
			println(null, String.format(format, args), Mode.DETAILED);
		}
	}
	
	public static void graph(Object... parts) {
		if (mode.val >= Mode.GRAPH_LOG.val) {
			for (int i = 0; i < parts.length - 1; i++) {
				System.out.print(parts[i].toString());
				System.out.print(", ");
			}
			System.out.println(parts[parts.length - 1].toString());
		}
	}
	
	public static void graph(double[] parts) {
		if (mode.val >= Mode.GRAPH_LOG.val) {
			for (int i = 0; i < parts.length - 1; i++) {
				System.out.print(parts[i]);
				System.out.print(", ");
			}
			System.out.println(parts[parts.length - 1]);
		}
	}
	
	public static void graph(int[] parts) {
		if (mode.val >= Mode.GRAPH_LOG.val) {
			for (int i = 0; i < parts.length - 1; i++) {
				System.out.print(parts[i]);
				System.out.print(", ");
			}
			System.out.println(parts[parts.length - 1]);
		}
	}
	
	// ----- PRINT -----
	private static void println(Command c, String info, Mode level) {
		System.out.println((c == null ? "" : (c.getClass().getSimpleName() + ": ")) + info);
	}
	
	// ----- SET MODE -----
	public static void disable() {
		mode = Mode.OFF;
	}
	
	public static void enableSimple() {
		mode = Mode.SIMPLE;
	}
	
	public static void enableDetailed() {
		mode = Mode.DETAILED;
	}
	
	public static void enableGraphData() {
		mode = Mode.GRAPH_LOG;
	}
	
	protected enum Mode {
		OFF(0), SIMPLE(1), DETAILED(2), GRAPH_LOG(3);
		
		public final int val;
		
		private Mode(int n) {
			val = n;
		}
	}
}
