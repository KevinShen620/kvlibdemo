/*
 * 2017年4月9日 
 */
package org.kvlibdemo.study.java;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Kevin
 *
 */
public class Buffer {

	{
		System.out.println("block");
	}

	/**
	 * 
	 */
	public Buffer() {
		System.out.println("constructor");
	}

	public static void main(String[] args) throws IOException {
		new Buffer();
	}
}
