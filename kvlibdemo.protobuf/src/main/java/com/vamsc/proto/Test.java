/*
 * 2016年9月22日 
 */
package com.vamsc.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import com.vamsc.proto.PersonOuterClass.Person;
import com.vamsc.proto.PersonOuterClass.Person.Builder;

/**
 * @author Kevin
 *
 */
public class Test {

	public static void main(String[] args)
			throws InvalidProtocolBufferException {
		Builder builder = Person.newBuilder();
		builder.addPhone("1558286830");
		builder.setEmail("kevin.shn@live.com");
		builder.setId(1);
		builder.setName("Kevin");
		Person p1 = builder.build();
		System.out.println(p1);
		byte[] bytes = p1.toByteArray();
		Person p2 = Person.parseFrom(bytes);
		System.out.println(p2);
	}
}
