/*
 * 2017年4月25日 
 */
package org.kvlibdemo.study;

/**
 * @author Kevin
 *
 */
public class Singleton {

	private static class Holder {
		static {
			System.out.println("in holder");
		}
		static Singleton instance1 = new Singleton();
	}

	static Singleton instance2 = new Singleton();

	public static Singleton getInstance1() {
		return Holder.instance1;
	}

	public static Singleton getInstance2() {
		return instance2;
	}

	public static void main(String[] args) {
		System.out.println(getInstance1());
	}

}
