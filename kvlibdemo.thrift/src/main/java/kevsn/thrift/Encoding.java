/*
 * 2016年9月22日 
 */
package kevsn.thrift;

import java.util.Arrays;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;

import kevsn.thrift.model.Person;

/**
 * @author Kevin
 *
 */
public class Encoding {

	public static void main(String[] args) throws Exception {
		Person p = new Person();
		p.setName("Kevin");
		p.setYear(1988);
		TMemoryBuffer buffer = new TMemoryBuffer(60);
		TBinaryProtocol protocol = new TBinaryProtocol(buffer);
		p.write(protocol);
		byte[] bytes = buffer.getArray();
		System.out.println(buffer.length());
		System.out.println(Arrays.toString(bytes));

		Person person = decode(bytes, buffer.length());
		System.out.println(person);
	}

	public static Person decode(byte[] bytes, int len) throws Exception {
		TMemoryBuffer buffer = new TMemoryBuffer(len);
		buffer.write(bytes);
		TBinaryProtocol protocol = new TBinaryProtocol(buffer);
		Person p = new Person();
		p.read(protocol);
		return p;
	}
}
