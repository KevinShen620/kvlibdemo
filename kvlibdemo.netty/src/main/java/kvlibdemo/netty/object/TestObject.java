/*
 * 2016年7月8日 
 */
package kvlibdemo.netty.object;

import java.io.Serializable;

/**
 * @author Kevin
 *
 */
public class TestObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3180863331005845955L;

	private String str;

	private int i;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	@Override
	public String toString() {
		return "TestObject [str=" + str + ", i=" + i + "]";
	}

}
