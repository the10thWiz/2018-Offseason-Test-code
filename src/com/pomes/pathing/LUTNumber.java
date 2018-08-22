package com.pomes.pathing;

public class LUTNumber extends LookUpTable<Double, Double> {
	
	public LUTNumber(Entry<Double, Double>[] e) {
		super(e);
	}
	
	public double getAt(double key) {
		int max = entries.length - 1;
		int min = 0;
		while (max - min > 1) {
			int mid = (max + min) / 2;
			if (entries[mid].key.compareTo(key) == 0) {
				return entries[mid].value;
			} else if (entries[mid].key.compareTo(key) < 0) {
				min = mid;
			} else {
				max = mid;
			}
		}
		double dist = (entries[max].key - entries[min].key);
		return entries[max].value * ((entries[max].key - key) / dist)
				+ entries[min].value * ((entries[min].key - key) / dist);
	}
	
	public double getAtR(double value) {
		int max = entries.length - 1;
		int min = 0;
		while (max - min > 1) {
			int mid = (max + min) / 2;
			if (entries[mid].value.compareTo(value) == 0) {
				return entries[mid].key;
			} else if (entries[mid].value.compareTo(value) < 0) {
				min = mid;
			} else {
				max = mid;
			}
		}
		double dist = (entries[max].value - entries[min].value);
		return entries[max].key * ((entries[max].value - value) / dist)
				+ entries[min].key * ((entries[min].value - value) / dist);
	}
	
}
