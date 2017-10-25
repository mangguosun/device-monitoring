package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.model.HistoryMessageResp;
import cn.dunn.im.model.Message;
import cn.dunn.im.wind.ChatGroupWind;
import cn.dunn.im.wind.ChatWind;

/**
 * 
 * @Title: HistoryMessageRespHandler.java
 * @Package cn.dunn.im.handler
 * @Description: 历史消息响应handler
 * @author TangTianXiong
 * @date 2015年6月8日 上午10:55:55
 */
public class HistoryMessageRespHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HistoryMessageResp) {
			Thread.sleep(1000);// TODO 模拟加载延迟情况
			HistoryMessageResp historyMessageResp = (HistoryMessageResp) msg;

			switch (historyMessageResp.getType()) {
			case Message.TYPE_CHAT:
				ChatWind wind = CurrentWinContainer.getChatWind(historyMessageResp.getWindKey());
				if (wind != null) {
					if (historyMessageResp.getHistoryMessage().size() < 20) {
						wind.historyMessageLoadFinish(false);
					} else {
						wind.historyMessageLoadFinish(true);
					}
					wind.receiveHistoryMessage(historyMessageResp.getHistoryMessage());
				}
				break;
			case Message.TYPE_GROUP:
				ChatGroupWind groupWind = CurrentWinContainer.getGroupWind(historyMessageResp.getWindKey());
				if (groupWind != null) {
					if (historyMessageResp.getHistoryMessage().size() < 20) {
						groupWind.historyMessageLoadFinish(false);
					} else {
						groupWind.historyMessageLoadFinish(true);
					}
					groupWind.receiveHistoryMessage(historyMessageResp.getHistoryMessage());
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
