/*
 * 2017年4月14日 
 */
package org.kvlibdemo.study.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin
 *
 */
public class HeapOOM {

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		int i = 0;
		while (++i != 0) {
			list.add(new OOMObject());
			if (i / 10000 == 0) {
				System.out.println(i + " objects");
			}
		}
	}
}
