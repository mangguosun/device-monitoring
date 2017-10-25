package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.model.ChatGroup;
import cn.dunn.im.model.MyChatGroup;
import cn.dunn.im.wind.ChatGroupItem;

/**
 * 
 * @Title: ReceiveChatGroupHandler.java
 * @Package cn.dunn.im.handler
 * @Description: 接受我的群组
 * @author TangTianXiong
 * @date 2015年6月8日 下午9:04:38
 */
public class ReceiveChatGroupHandler extends ChannelHandlerAdapter {
	// private int count;
	// private ScheduledExecutorService threadPool =
	// Executors.newSingleThreadScheduledExecutor();

	@Override
	public void channelRead(ChannelHandlerContext ctx, final Object msg) throws Exception {
		if (msg instanceof MyChatGroup) {
			if (ComponentContainer._LOGINMAIN == null) {
				ctx.fireChannelRead(msg);
				return;
			}
			ConnectConstants.CHANNEL.eventLoop().execute(new Runnable() {
				@Override
				public void run() {
					// 从主面板中获取好友分组
					ScrollPane _pane = getChatGroupList();
					@SuppressWarnings("unchecked")
					final ListView<Node> listView = (ListView<Node>) _pane.getContent();
					ComponentContainer._CHAT_GROUP = listView;
					MyChatGroup friends = (MyChatGroup) msg;
					List<ChatGroup> chatGroups = friends.getChatGroup();
					if (chatGroups != null) {
						for (final ChatGroup chatGroup : chatGroups) {
							Platform.runLater(new Runnable() {
								public void run() {
									listView.getItems().add(new ChatGroupItem(chatGroup).build());
								}
							});
						}
					}
				}

			});

		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private ScrollPane getChatGroupList() {
		TabPane node1 = (TabPane) ComponentContainer._LOGINMAIN.lookup("#friendtabpane");
		Tab tab = node1.getTabs().get(1);
		AnchorPane node2 = (AnchorPane) tab.getContent();
		ScrollPane node3 = (ScrollPane) node2.getChildren().get(0);
		return node3;
	}

	/**
	 * 设置双击事件
	 * 
	 * @param listView
	 */
	// private void setEvent(ListView<Node> listView) {
	// listView.setOnMouseClicked(new EventHandler<Event>() {
	// @Override
	// public void handle(Event event) {
	// count++;
	// if (count == 2) {
	// @SuppressWarnings("unchecked")
	// ListView<Node> view = (ListView<Node>) event.getSource();
	// Node selectedItem = view.getSelectionModel().getSelectedItem();
	// if (selectedItem == null)
	// return;
	// Pane pane = (Pane) selectedItem;
	// @SuppressWarnings("unused")
	// // TODO 头像
	// ImageView _headImage = (ImageView) pane.getChildren().get(0);
	// Label _username = (Label) pane.getChildren().get(1);
	// Label _autograph = (Label) pane.getChildren().get(2);
	// Label _userid = (Label) pane.getChildren().get(3);
	// Label _unReadCount = (Label) pane.getChildren().get(5);
	// ChatWind wind = new ChatWind(_userid.getText(), _username.getText(),
	// _autograph.getText());
	// if (_unReadCount.isVisible()) {
	//
	// wind.messageLoading();
	// _unReadCount.setVisible(false);
	// _unReadCount.setText("0");
	//
	// // 发起获取未读消息请求
	// UnReadMessageReq unReadMessageReq = new UnReadMessageReq();
	// unReadMessageReq.setType(UnReadMessageReq.CHAT);
	// unReadMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
	// unReadMessageReq.setAnother(_userid.getText());
	// ConnectConstants.CHANNEL.writeAndFlush(unReadMessageReq);
	// }
	// wind.setLastLoadTime(new Date().getTime());
	// CurrentWinContainer.open(_userid.getText(), wind);
	// count = 0;
	// } else if (count == 1) {
	// threadPool.schedule(new Runnable() {
	// @Override
	// public void run() {
	// count = 0;
	// }
	// }, 200, TimeUnit.MILLISECONDS);
	// } else {
	// count = 0;
	// }
	// }
	// });
	// }

}
