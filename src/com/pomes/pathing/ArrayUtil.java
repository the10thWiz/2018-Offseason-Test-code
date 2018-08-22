package com.pomes.pathing;

import java.lang.reflect.Array;

public class ArrayUtil {
	@SuppressWarnings("unchecked")
	public static <K extends Object> K[] expandArray(K[] e, int newSize) {
		K[] out = (K[]) Array.newInstance(e[0].getClass(), newSize);
		
		for(int i = 0; i < e.length; i++) {
			out[i] = e[i];
		}
		
		return out;
	}
	
	public static int incCircle(int i, int len) {
		if(i + 1 < len) {
			return i + 1;
		}else {
			return 0;
		}
	}
	
	public static int decCircle(int i, int len) {
		if(i - 1 >= 0) {
			return i - 1;
		}else {
			return len - 1;
		}
	}
}
