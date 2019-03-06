/*
 * 2016年3月23日 
 */
package org.kvlibdemo.study.algorithm;

/**
 * @author Kevin
 *
 */
public class BinarySearch {

	public static <T extends Comparable<? super T>> int search(T[] array,
			T value) {
		int l = 0;
		int r = array.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			T mvalue = array[mid];
			int cmp = mvalue.compareTo(value);
			if (cmp == 0) {
				return cmp;
			}
			if (cmp > 0) {
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return -1;
	}
}
