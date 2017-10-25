package cn.dunn.im.handler;

import java.util.Date;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.model.HaveReadMessageChatNotify;
import cn.dunn.im.model.Message;
import cn.dunn.im.wind.ChatGroupWind;
import cn.dunn.im.wind.ChatWind;

/**
 * 消息接受处理器
 * 
 * @author Administrator
 * 
 */
public class MessageReceiveHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message) {
			Message message = (Message) msg;

			switch (message.getType()) {
			case Message.TYPE_CHAT:
				receiveChatMessage(message);

				break;
			case Message.TYPE_GROUP:

				ChatGroupWind groupWind = CurrentWinContainer.getGroupWind(message.getTo());
				if (groupWind != null) {
					groupWind.receiveMessage(message);
					HaveReadMessageChatNotify haveReadMessageChatNotify = new HaveReadMessageChatNotify();
					haveReadMessageChatNotify.setUserid(ConnectConstants.LOGIN_USER.getUserid());
					haveReadMessageChatNotify.setAnother(message.getTo());
					haveReadMessageChatNotify.setType(message.getType());
					haveReadMessageChatNotify.setReadTime(new Date().getTime());
					ConnectConstants.CHANNEL.writeAndFlush(haveReadMessageChatNotify);
				} else {
					ListView<Node> chatGroups = ComponentContainer._CHAT_GROUP;
					ObservableList<Node> items = chatGroups.getItems();
					for (Node node : items) {
						Pane _pane = (Pane) node;
						Label _groupId = (Label) _pane.getChildren().get(2);
						if (_groupId.getText().equals(message.getTo())) {
							final Label _unReadCount = (Label) _pane.getChildren().get(3);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									if (_unReadCount.isVisible()) {
										_unReadCount.setText(Integer.valueOf(_unReadCount.getText()) + 1 + "");
									} else {
										_unReadCount.setVisible(true);
										_unReadCount.setText("1");
									}
								}
							});

							return;
						}
					}
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

	private void receiveChatMessage(Message message) {
		ChatWind wind = CurrentWinContainer.getChatWind(message.getFrom());
		if (wind != null) {
			wind.receiveMessage(message);
			HaveReadMessageChatNotify haveReadMessageChatNotify = new HaveReadMessageChatNotify();
			haveReadMessageChatNotify.setUserid(message.getTo());
			haveReadMessageChatNotify.setAnother(message.getFrom());
			haveReadMessageChatNotify.setType(message.getType());
			haveReadMessageChatNotify.setReadTime(new Date().getTime());
			ConnectConstants.CHANNEL.writeAndFlush(haveReadMessageChatNotify);
		} else {
			ObservableList<TitledPane> panes = ComponentContainer._FRIEND_GROUP.getPanes();
			for (TitledPane titledPane : panes) {
				@SuppressWarnings("unchecked")
				ListView<Node> content = (ListView<Node>) titledPane.getContent();
				ObservableList<Node> items = content.getItems();
				for (Node node : items) {
					Parent parent = (Parent) node;
					Label _userid = (Label) parent.getChildrenUnmodifiable().get(3);
					if (_userid.getText().equals(message.getFrom())) {
						final Label _unReadMessageCount = (Label) parent.getChildrenUnmodifiable().get(5);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (_unReadMessageCount.isVisible()) {
									_unReadMessageCount.setText(Integer.valueOf(_unReadMessageCount.getText()) + 1 + "");
								} else {
									_unReadMessageCount.setText("1");
									_unReadMessageCount.setVisible(true);
								}
							}
						});
						return;
					}

				}
			}
		}
	}
}
