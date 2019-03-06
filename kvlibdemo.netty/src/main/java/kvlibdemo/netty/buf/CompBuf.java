/*
 * 2017年7月2日 
 */
package kvlibdemo.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Kevin
 *
 */
public class CompBuf {

	public static void main(String[] args) {
		ByteBufAllocator d = ByteBufAllocator.DEFAULT;
		ByteBuf buf = d.buffer();
		int capacity = buf.capacity();
		System.out.println(capacity);
		for (int i = 0; i < capacity + 10; i++) {
			buf.writeByte(i);
		}
		while (buf.isReadable()) {
			byte bt = buf.readByte();
			System.out.println(bt);
		}
	}
}
