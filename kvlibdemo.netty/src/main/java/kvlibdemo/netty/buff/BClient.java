/*
 * 2017年7月31日 
 */
package kvlibdemo.netty.buff;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Kevin
 *
 */
public class BClient {

	private NioEventLoopGroup group;
	private Channel channel;

	private Channel initChannel() {
		Bootstrap boots = new Bootstrap();
		group = new NioEventLoopGroup(1);
		boots.group(group);
		boots.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new BEncoder());
				pipeline.addLast(new ChunkedWriteHandler());
			}
		});
		boots.channel(NioSocketChannel.class);
		ChannelFuture f = boots
				.connect(new InetSocketAddress("localhost", 8000));
		try {
			return f.sync().channel();
		} catch (Exception e) {
			System.err.println("连接失败");
			group.shutdownGracefully();
			throw new RuntimeException(e);
		}
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void start() {
		this.channel = initChannel();
	}

	public void stop() {
		channel.close();
		this.group.shutdownGracefully();
	}

	private class BEncoder extends MessageToByteEncoder<String> {

		@Override
		protected void encode(ChannelHandlerContext ctx, String msg,
				ByteBuf out) throws Exception {
			byte[] bytes = msg.getBytes(StandardCharsets.UTF_16);
			out.writeBytes(bytes);
		}

	}
}
