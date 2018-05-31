package org.usfirst.frc.team1732.robot.debug;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.debug.Console.Mode;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
	private static ConcurrentLinkedQueue<Entry> entries = new ConcurrentLinkedQueue<>();
	
	public Dashboard() {
	}
	
	protected void update() {
		entries.forEach(this::call);
	}
	
	private void call(Entry e) {
		e.putToDashboard();
	}
	
	public static void add(String name, Supplier<?> sup) {
		entries.add(new Entry(name, sup, Mode.SIMPLE));
	}
	
	public static void add(String name, Sendable obj) {
		entries.add(new EntrySendable(name, obj, Mode.SIMPLE));
	}
	
	public static void addDetail(String name, Supplier<?> sup) {
		entries.add(new Entry(name, sup, Mode.DETAILED));
	}
	
	public static void addDetail(String name, Sendable obj) {
		entries.add(new EntrySendable(name, obj, Mode.DETAILED));
	}
	
	public static void remove(String name) {
		entries.removeIf((e) -> e.name.equals(name));
	}
	
	private static class Entry {
		private final Mode level;
		private final String name;
		private final Supplier<?> sup;
		
		public Entry(String name, Supplier<?> sup, Mode level) {
			this.name = name;
			this.sup = sup;
			this.level = level;
		}
		
		public void putToDashboard() {
			Object o = sup.get();
			if (o instanceof Number) {
				SmartDashboard.putNumber(name, ((Number) o).doubleValue());
			} else if (o instanceof Boolean) {
				SmartDashboard.putBoolean(name, (Boolean) o);
			} else if (o instanceof String) {
				SmartDashboard.putString(name, (String) o);
			} else if (o instanceof Sendable) {
				SmartDashboard.putData(name, (Sendable) o);
			}
		}
		
	}
	
	private static class EntrySendable extends Entry {
		private final String name;
		private final Sendable obj;
		
		public EntrySendable(String name, Sendable obj, Mode level) {
			super(null, null, level);
			this.name = name;
			this.obj = obj;
		}
		
		public void putToDashboard() {
			SmartDashboard.putData(name, obj);
		}
	}
}
