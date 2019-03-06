/*
 * 2017年4月15日 
 */
package org.kvlibdemo.study.jvm.gc;

/**
 * @author Kevin
 *
 */
public class ReferenceCountingGC {

	public Object instance = null;

	private static final int _1MB = 1024 * 1024;

	private byte[] bigSize = new byte[2 * _1MB];

	public static void testGC() throws InterruptedException {
		ReferenceCountingGC obja = new ReferenceCountingGC();
		ReferenceCountingGC objb = new ReferenceCountingGC();
		obja.instance = objb;
		objb.instance = obja;
		obja = null;
		objb = null;
		System.gc();
		int i = 0;
		while (i++ < 2) {
			Thread.sleep(10 * 1000);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method exected");
		this.instance = new ReferenceCountingGC();
	}

	public static void main(String[] args) throws InterruptedException {
		testGC();
	}

}
