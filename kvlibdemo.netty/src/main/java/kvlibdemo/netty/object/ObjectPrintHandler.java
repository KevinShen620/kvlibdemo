/*
 * 2016年7月8日 
 */
package kvlibdemo.netty.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Kevin
 *
 */
public class ObjectPrintHandler extends ChannelInboundHandlerAdapter {

	private boolean client;

	public ObjectPrintHandler(boolean client) {
		this.client = client;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(ObjectPrintHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (client) {
			System.out.println(msg);
		} else {
			TestObject t = (TestObject) msg;
			System.out.println("Object Received:" + t);
			ctx.writeAndFlush("OK for message " + t.getI());
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("Channel Closed");
	}

}
