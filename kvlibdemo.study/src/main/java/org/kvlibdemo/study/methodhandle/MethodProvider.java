/*
 * 2017年2月5日 
 */
package org.kvlibdemo.study.methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

/**
 * @author Kevin
 *
 */
public class MethodProvider {

	public int calc() {
		int a = 100;
		int b = 200;
		int c = 300;
		return (a + b) * c;
	}

	public static void mst(String param) {
		System.out.println("static method " + param);
	}

	public void m(String param) {
		System.out.println("common method " + param);
	}

	public static void main(String[] args) throws Throwable {
		MethodType methodType = MethodType.methodType(void.class, String.class);
		MethodProvider provider = new MethodProvider();
		MethodHandle method = MethodHandles.lookup()
				.findVirtual(MethodProvider.class, "m", methodType)
				.bindTo(provider);
		method.invokeExact("paramValue");
	}
}
