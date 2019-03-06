/*
. * 2016年7月8日 
 */
package kvlibdemo.netty.object;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author Kevin
 *
 */
public class ObjectDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		Channel channel = ctx.channel();
		AttributeKey<AttObject> objKey = AttributeKey.valueOf("obj");
		Attribute<AttObject> attValue = channel.attr(objKey);
		AttObject value = attValue.get();
		if (value == null) {
			value = new AttObject();
			attValue.set(value);
		}
		int readable = in.readableBytes();
		for (int i = 0; i < readable; i++) {
			byte bt = in.readByte();
			Object obj = value.addByte(bt);
			if (obj != null) {
				out.add(obj);
			}
		}
	}

	private class AttObject {

		ByteBuffer lenBuffer;

		ByteBuffer valueBuffer;

		int len = -1;

		AttObject() {
			lenBuffer = ByteBuffer.allocate(Integer.BYTES);
		}

		Object addByte(byte bt) {
			if (valueBuffer == null) {
				if (len < 0) {
					lenBuffer.put(bt);
					if (!lenBuffer.hasRemaining()) {
						lenBuffer.flip();
						len = lenBuffer.getInt();
						lenBuffer.clear();
						if (len <= 0) {
							return null;
						}
						valueBuffer = ByteBuffer.allocate(len);
					}
				}
			} else {
				valueBuffer.put(bt);
				if (!valueBuffer.hasRemaining()) {
					valueBuffer.flip();
					Serializable objValue = bytesToSerObj(valueBuffer.array());
					valueBuffer = null;
					len = -1;

					return objValue;
				}
			}
			return null;
		}
	}

	public static Serializable bytesToSerObj(byte[] bytes) {
		ByteArrayInputStream bytein = new ByteArrayInputStream(bytes);

		try {
			ObjectInputStream objin = new ObjectInputStream(bytein);
			return (Serializable) objin.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
