package com.pomes.pathing;

public class LookUpTable<K extends Comparable<K>, V extends Comparable<V>> {
	/**
	 * Sorted lowest to highest
	 */
	protected Entry<K, V>[] entries;
	protected int size = 0;

	@SuppressWarnings("unchecked")
	public LookUpTable() {
		entries = new Entry[20];
	}
	public LookUpTable(Entry<K, V>[] e) {
//		raw = new LinkedList<>();
		entries = e;
		size = e.length;
	}
	
	public void addValue(K key, V value) {
//		raw.add(new Entry<K, V>(key, value));
		size++;
		if(size >= entries.length) {
			entries = ArrayUtil.expandArray(entries, size + 20);
		}
		int i;
		for(i = size - 1; i > 0 && entries[i - 1].key.compareTo(key) > 0; i--) {
			entries[i] = entries[i-1];
		}
		entries[i] = new Entry<K, V>(key, value);
	}
	
//	@SuppressWarnings("unchecked")
//	public void compile() {
//		if(entries != null) {
//			for(int i = 0; i < entries.length; i++) {
//				raw.add(entries[i]);
//			}
//		}
//		entries = new Entry[raw.size()];
//		for(int i = 0; i < entries.length; i++) {
//			Entry<K, V> max = raw.getFirst();
//			for(Entry<K, V> cur : raw) {
//				if(cur.key.compareTo(max.key) > 0) {
//					max = cur;
//				}
//			}
//			raw.remove(max);
//			entries[i] = max;
//		}
//	}

	public V getAbove(K key) {
		int max = entries.length - 1;
		int min = 0;
		while(max - min > 1) {
			int mid = (max + min)/2;
			if(entries[mid].key.compareTo(key) == 0) {
				return entries[mid].value;
			}else if(entries[mid].key.compareTo(key) < 0) {
				min = mid + 1;
			}else {
				max = mid - 1;
			}
		}
		return entries[max].value;
	}
	public V getBelow(K key) {
		int max = size - 1;
		int min = 0;
		while(max - min > 1) {
			int mid = (max + min)/2;
			if(entries[mid].key.compareTo(key) == 0) {
				return entries[mid].value;
			}else if(entries[mid].key.compareTo(key) < 0) {
				min = mid + 1;
			}else {
				max = mid - 1;
			}
		}
		return entries[min].value;
	}

	public K getAboveR(V value) {
		int max = entries.length - 1;
		int min = 0;
		while(max - min > 1) {
			int mid = (max + min)/2;
			if(entries[mid].value.compareTo(value) == 0) {
				return entries[mid].key;
			}else if(entries[mid].value.compareTo(value) < 0) {
				min = mid + 1;
			}else {
				max = mid - 1;
			}
		}
		return entries[max].key;
	}
	public K getBelowR(V value) {
		int max = size - 1;
		int min = 0;
		while(max - min > 1) {
			int mid = (max + min)/2;
			if(entries[mid].value.compareTo(value) == 0) {
				return entries[mid].key;
			}else if(entries[mid].value.compareTo(value) < 0) {
				min = mid + 1;
			}else {
				max = mid - 1;
			}
		}
		return entries[min].key;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < size; i++) {
			s.append("(");
			s.append(entries[i].key);
			s.append(", ");
			s.append(entries[i].value);
			s.append("),\n");
		}
		
		return s.toString();
	}
	
}
