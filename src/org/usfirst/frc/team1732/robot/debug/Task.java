package org.usfirst.frc.team1732.robot.debug;

import java.util.function.Consumer;

public class Task<T> {
	protected Consumer<T> com;
	protected T value;
	public Task(Consumer<T> com, T value) {
		super();
		this.com = com;
		this.value = value;
	}
	public void run(Consumer<T> other) {
		com.accept(value);
		if(other != null) {
			other.accept(value);
		}
	}
}
