package com.pomes.pathing;

public class Entry<K extends Comparable<K>, V> {
	public K key;
	public V value;
	
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
}