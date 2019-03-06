/*
 * 2017年4月18日 
 */
package org.kvlibdemo.study.jvm;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * @author Kevin
 *
 */
public class Mem {

	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		// 设置该Field为可访问
		field.setAccessible(true);
		// 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
		Unsafe unsafe = (Unsafe) field.get(null);
		long addr = unsafe.allocateMemory(10);
		unsafe.freeMemory(addr);
	}
}
