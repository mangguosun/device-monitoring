package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.model.ChatGroupMemberResp;
import cn.dunn.im.wind.ChatGroupWind;

/**
 * 
 * @Title: ChatGroupMemberRespHandler.java
 * @Package cn.dunn.im.handler
 * @Description: 接收群成员响应
 * @author TangTianXiong
 * @date 2015年6月9日 上午10:07:49
 */
public class ChatGroupMemberRespHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ChatGroupMemberResp) {
			Thread.sleep(500); // TODO 模拟真实延迟效果
			ChatGroupMemberResp resp = (ChatGroupMemberResp) msg;
			ChatGroupWind groupWind = CurrentWinContainer.getGroupWind(resp.getWindKey());
			if (groupWind != null) {
				groupWind.memberLoadFinish(resp.getMember());
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}
}
