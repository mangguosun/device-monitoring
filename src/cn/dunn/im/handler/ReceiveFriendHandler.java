package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.FriendsContainer;
import cn.dunn.im.container.UserIMContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.FriendGroup;
import cn.dunn.im.model.HaveReadMessageChatNotify;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.MyFriendGroup;
import cn.dunn.im.model.UnReadMessageReq;
import cn.dunn.im.model.User;
import cn.dunn.im.wind.ChatWind;

public class ReceiveFriendHandler extends ChannelHandlerAdapter {
	private int count;
	private ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

	@Override
	public void channelRead(ChannelHandlerContext ctx, final Object msg) throws Exception {
		// File userFile = new File(ConnectConstants.USER_IMAGE_PATH);
		// if (!userFile.exists()) {
		// userFile.mkdirs();
		// }
		if (msg instanceof MyFriendGroup) {

			if (ComponentContainer._LOGINMAIN == null) {
				ctx.fireChannelRead(msg);
				return;
			}
			ConnectConstants.CHANNEL.eventLoop().execute(new Runnable() {
				@Override
				public void run() {

					// 从主面板中获取好友分组
					final Accordion _friendGroup = (Accordion) getFriendList().getContent();

					ComponentContainer._FRIEND_GROUP = _friendGroup;
					MyFriendGroup friends = (MyFriendGroup) msg;
					List<FriendGroup> friendGroups = friends.getFriendGroup();
					Map<String, User> map = new HashMap<String, User>();
					Map<String, Pane> map1 = new HashMap<String, Pane>();
					List<User> friendss = new ArrayList<User>();
					if (friendGroups != null) {
						final TitledPane[] tps = new TitledPane[friendGroups.size()];
						for (int i = 0; i < friendGroups.size(); i++) {
							FriendGroup friendGroup = friendGroups.get(i);
							ListView<Node> listView = new ListView<Node>();
							setEvent(listView);
							List<User> users = friendGroup.getFriends();
							int onlineCount = 0;
							for (User user : users) {
								map.put(user.getUserid(), user);
								friendss.add(user);
								if (user.getIsOnline()) {
									onlineCount++;
								}
								Pane pane = WindContainer.getFriendItem(user);
								map1.put(user.getUserid(), pane);
								listView.getItems().add(pane);
							}
							tps[i] = new TitledPane(
									friendGroup.getGroupName() + "(" + onlineCount + "/" + users.size() + ")",
									listView);
						}
						Platform.runLater(new Runnable() {
							public void run() {
								// _friendGroup.getPanes().clear();
								_friendGroup.getPanes().addAll(tps);
							}
						});
						_friendGroup.setExpandedPane(tps[0]);
						Pane friendbox = (Pane) ComponentContainer._LOGINMAIN.lookup("#friendbox");
						friendbox.setVisible(true);

					}
					FriendsContainer.INSTANCE.setFriends(friendss);
					UserIMContainer.INSTANCE.setFriends(map);
					UserIMContainer.INSTANCE.setFriendComponent(map1);
				}

			});

		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private ScrollPane getFriendList() {
		TabPane node1 = (TabPane) ComponentContainer._LOGINMAIN.lookup("#friendtabpane");
		Tab tab = node1.getTabs().get(0);
		AnchorPane node2 = (AnchorPane) tab.getContent();
		ScrollPane node3 = (ScrollPane) node2.getChildren().get(0);
		return node3;
	}

	/**
	 * 设置双击事件
	 * 
	 * @param listView
	 */
	private void setEvent(ListView<Node> listView) {
		listView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				count++;
				if (count == 2) {
					@SuppressWarnings("unchecked")
					ListView<Node> view = (ListView<Node>) event.getSource();
					Node selectedItem = view.getSelectionModel().getSelectedItem();
					if (selectedItem == null)
						return;
					Pane pane = (Pane) selectedItem;
					Label headFileName = (Label) pane.getChildren().get(6);
					Label _username = (Label) pane.getChildren().get(1);
					Label _autograph = (Label) pane.getChildren().get(2);
					Label _status = (Label) pane.getChildren().get(4);
					Label _userid = (Label) pane.getChildren().get(3);
					Label _unReadCount = (Label) pane.getChildren().get(5);
					ChatWind wind = new ChatWind(_userid.getText(), _username.getText(), _autograph.getText(),
							headFileName.getText(), _status.getText());
					if (_unReadCount.isVisible()) {

						wind.messageLoading();
						_unReadCount.setVisible(false);
						_unReadCount.setText("0");
					}
					// 发起获取未读消息请求
					UnReadMessageReq unReadMessageReq = new UnReadMessageReq();
					unReadMessageReq.setType(UnReadMessageReq.CHAT);
					unReadMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
					unReadMessageReq.setAnother(_userid.getText());
					ConnectConstants.CHANNEL.writeAndFlush(unReadMessageReq);

					// 更新最后读取消息的时间
					HaveReadMessageChatNotify haveReadMessageChatNotify = new HaveReadMessageChatNotify();
					haveReadMessageChatNotify.setUserid(ConnectConstants.LOGIN_USER.getUserid());
					haveReadMessageChatNotify.setAnother(_userid.getText());
					haveReadMessageChatNotify.setType(Message.TYPE_CHAT);
					haveReadMessageChatNotify.setReadTime(new Date().getTime());
					ConnectConstants.CHANNEL.writeAndFlush(haveReadMessageChatNotify);

					wind.setLastLoadTime(new Date().getTime());
					CurrentWinContainer.openChatWind(_userid.getText(), wind);
					count = 0;
				} else if (count == 1) {
					threadPool.schedule(new Runnable() {
						@Override
						public void run() {
							count = 0;
						}
					}, 300, TimeUnit.MILLISECONDS);
				} else {
					count = 0;
				}
			}
		});
	}

}
