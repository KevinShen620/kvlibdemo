/*
 * 2016年7月8日 
 */
package kvlibdemo.netty.object;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Kevin
 *
 */
public class Server {

	public static void main(String[] args) throws InterruptedException {
		// InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						// ch.pipeline()
						// .addLast(new LoggingHandler(LogLevel.ERROR));
						ch.pipeline().addLast(new ObjectDecoder());
						ch.pipeline().addLast(new ObjectEncoder());
						ch.pipeline().addLast(new ObjectPrintHandler(false));
					}
				});
		b.bind(9001).sync();
	}
}
