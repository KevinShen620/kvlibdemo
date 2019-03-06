/*
 * 2016年7月8日 
 */
package kvlibdemo.netty.buff;

import java.nio.charset.StandardCharsets;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author Kevin
 *
 */
public class BServer {

	private NioEventLoopGroup bossGroup;
	private NioEventLoopGroup workerGroup;

	public void start() {
		ServerBootstrap boots = new ServerBootstrap();
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		boots.group(bossGroup, workerGroup);
		boots.channel(NioServerSocketChannel.class);
		boots.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeLine = ch.pipeline();
				pipeLine.addLast(new BHandler());
			}
		});
		boots.bind(8000);
		System.out.println("服务已经启动");
	}

	public void stop() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public static void main(String[] args) {
		BServer server = new BServer();
		server.start();
	}

	private class BHandler extends ByteToMessageDecoder {

		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf in,
				List<Object> out) throws Exception {
			int bytesSize = in.readableBytes();
			if (bytesSize >= 10) {
				CharSequence str = in.readCharSequence(bytesSize,
						StandardCharsets.UTF_16);
				System.out.println("收到消息：" + str);
			}
		}
	}
}
