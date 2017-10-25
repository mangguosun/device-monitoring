package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.List;

import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.model.HaveReadMessageChatNotify;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.UnReadMessageResp;
import cn.dunn.im.wind.ChatGroupWind;
import cn.dunn.im.wind.ChatWind;

/**
 * 未读消息响应
 * 
 * @author Administrator
 * 
 */
public class UnReadMessageRespHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof UnReadMessageResp) {
			UnReadMessageResp message = (UnReadMessageResp) msg;
			Thread.sleep(1000);// TODO 模拟消息加载效果
			switch (message.getType()) {
			case UnReadMessageResp.CHAT:
				ChatWind wind = CurrentWinContainer.getChatWind(message.getWindKey());
				if (wind != null) {
					List<Message> messages = message.getMessages();
					if (messages.size() != 0) {
						// 接受未读消息
						wind.receiveUnReadMessage(messages);
						// 设置加载历史消息标记
						wind.setLastLoadTime(messages.get(0).getCreateTime());
					}
					// 消息接受完成
					wind.receiveFinish();
				}
				// 更新读取消息时间
				HaveReadMessageChatNotify haveReadMessageChatNotify = new HaveReadMessageChatNotify();
				haveReadMessageChatNotify.setUserid(ConnectConstants.LOGIN_USER.getUserid());
				haveReadMessageChatNotify.setAnother(message.getWindKey());
				haveReadMessageChatNotify.setType(HaveReadMessageChatNotify.CHAT);
				haveReadMessageChatNotify.setReadTime(new Date().getTime());
				ConnectConstants.CHANNEL.writeAndFlush(haveReadMessageChatNotify);
				break;
			case UnReadMessageResp.CHAT_GROUP:
				ChatGroupWind groupWind = CurrentWinContainer.getGroupWind(message.getWindKey());
				if (groupWind != null) {
					List<Message> messages = message.getMessages();
					// 接受未读消息
					groupWind.receiveUnReadMessage(messages);
					// 消息接受完成
					groupWind.receiveFinish();
				}
				break;

			default:
				ctx.fireChannelRead(msg);
				break;
			}

		} else {
			ctx.fireChannelRead(msg);
		}
	}
}
