package cn.dunn.im.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import cn.dunn.im.container.ConnectContainer;
import cn.dunn.im.model.Message;

public class PacageDispatchHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object obj) throws Exception {
		if (obj instanceof Message) {
			Message message = (Message) obj;
			String to = message.getTo();
			Channel channel = ConnectContainer.INSTANCE.getChannelByUsername(to);
			if (channel != null) {
				channel.writeAndFlush(message);
			} else {
				message.setFrom("server");
				message.setBody("没有在线!");
				ctx.writeAndFlush(message);
			}

		} else
			ctx.fireChannelRead(obj);
	}

}
