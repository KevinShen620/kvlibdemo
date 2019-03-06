/*
 * 2017年4月26日 
 */
package org.kvlibdemo.study.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * @author Kevin
 *
 */
public class UnSafeTest {

	public static void main(String[] args) {
		Unsafe unsafe = getUnsafe();
		OffObj obj = new OffObj();
		obj.setI(10);
		obj.setL(900000);
		obj.setS("hello,world");
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fname = field.getName();
			long offset = unsafe.objectFieldOffset(field);
			System.out.println(fname + ":" + offset);
		}
	}

	private static Unsafe getUnsafe() {
		Field field = null;
		try {
			field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			// 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
			Unsafe unsafe = (Unsafe) field.get(null);
			return unsafe;
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
