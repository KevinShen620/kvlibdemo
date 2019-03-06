/*
 * 2017年7月13日 
 */
package org.kvlibdemo.study.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Kevin
 *
 */
public class ChannelTest {

	public static void main(String[] args) throws IOException {
		Path file = Paths.get("/Users/kevin/initdb_vessel.sql");
		long s1 = Files.size(file);
		System.out.println(s1);
		try (FileChannel channel = FileChannel.open(file)) {
			long s2 = channel.size();
			System.out.println(s2);
		}
	}
}
