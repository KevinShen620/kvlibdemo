/*
 * 2016年7月8日 
 */
package kvlibdemo.netty.object;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Kevin
 *
 */
public class ObjectEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		byte[] bytes = serObjToBytes(msg);
		out.writeInt(bytes.length);// 消息长度
		out.writeBytes(bytes);
	}

	public static byte[] serObjToBytes(Object obj) {
		ByteArrayOutputStream byteout = null;
		ObjectOutputStream out = null;
		byteout = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(byteout);
			out.writeObject(obj);// 把对象写入到输出流
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return byteout.toByteArray();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		throw new RuntimeException(cause);
	}

}
