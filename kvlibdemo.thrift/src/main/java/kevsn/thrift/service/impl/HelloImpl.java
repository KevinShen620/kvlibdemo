/*
 * 2016年4月8日 
 */
package kevsn.thrift.service.impl;

import org.apache.thrift.TException;

/**
 * @author Kevin
 *
 */
public class HelloImpl implements kevsn.thrift.service.Hello.Iface {

	@Override
	public String helloString(String para) throws TException {
		System.out.println("hello,String");
		return "Hello " + para;
	}

	@Override
	public int helloInt(int para) throws TException {
		System.out.println("hello,int");
		return para;
	}

	@Override
	public boolean helloBoolean(boolean para) throws TException {
		System.out.println("hello,boolean");
		return para;
	}

	@Override
	public void helloVoid() throws TException {
		System.out.println("hello,void");
	}

	@Override
	public String helloNull() throws TException {
		System.out.println("hello,null");
		return null;
	}

}
