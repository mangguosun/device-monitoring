package cn.dunn.im.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import cn.dunn.im.handler.LoginHandler;
import cn.dunn.im.handler.MessageReceiveHandler;
import cn.dunn.im.handler.PacageDecoderHandler;
import cn.dunn.im.handler.PacageEncoderHandler;

public class IMClientBootstrap {
	public static void mains(String[] args) {
		try {
			new IMClientBootstrap().connect("", 8080);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		/*
	
	
		try {
			new Thread() {
				public void run() {
					try {
						new IMClientBootstrap().connect("", 8080);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
			while (ch == null) {
				Thread.yield();
			}
			System.out.println("连接成功");
			Scanner scan = new Scanner(System.in);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] split = line.split("#");
				Message message = new Message();
				message.setTo(split[0]);
				message.setBody(split[1]);
				message.setFrom(split[2]);
				ch.writeAndFlush(message);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	*/}

	public static EventLoopGroup group = new NioEventLoopGroup();
	public static Channel ch = null;

	public void connect(String host, int port) throws Exception {
		// 配置客户端NIO线程组
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new PacageDecoderHandler(1024 * 1024, 4, 4));
					ch.pipeline().addLast(new PacageEncoderHandler());
					ch.pipeline().addLast(new LoginHandler("zhangsan", "zhangsan"));
					ch.pipeline().addLast(new MessageReceiveHandler());
				}
			});
			// 发起异步连接操作
			ch = b.connect(new InetSocketAddress("127.0.0.1", 80), new InetSocketAddress("127.0.0.1", 81)).channel();
			ChannelFuture future = ch.closeFuture().sync();
			future.channel().closeFuture().sync();
		} finally {
			System.out.println("关闭连接");
			group.shutdownGracefully();
		}
	}
}
