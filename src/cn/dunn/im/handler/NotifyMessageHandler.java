package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.File;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.constant.ThreadContainer;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.NotifyMessage;
import cn.dunn.im.model.User;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.ChatGroupWind;

public class NotifyMessageHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof NotifyMessage) {
			NotifyMessage message = (NotifyMessage) msg;
			User u = (User) message.getBody();
			switch (message.getType()) {
			case Message.TYPE_CHAT:

				if (message.getStatus().equals(NotifyMessage.STATUS_OFFLINE)) {
					setUserStatus(u, false);
				} else if (message.getStatus().equals(NotifyMessage.STATUS_ONLINE)) {
					setUserStatus(u, true);
				} else if (message.getStatus().equals(NotifyMessage.STATUS_MODIFY)) {
					modifyUserInfo(u);
				}
				break;
			case Message.TYPE_GROUP:

				if (message.getStatus().equals(NotifyMessage.STATUS_OFFLINE)) {
					setMemberStatus(u, false);
				} else if (message.getStatus().equals(NotifyMessage.STATUS_ONLINE)) {
					setMemberStatus(u, true);
				} else if (message.getStatus().equals(NotifyMessage.STATUS_MODIFY)) {
					// TODO 修改群组信息
				}
				break;
			default:
				break;
			}

		} else {
			ctx.fireChannelRead(msg);
		}
	}

	/**
	 * 修改好友信息
	 * 
	 * @param u
	 */
	private void modifyUserInfo(User u) {

		ObservableList<TitledPane> panes = ComponentContainer._FRIEND_GROUP.getPanes();
		for (final TitledPane titledPane : panes) {
			@SuppressWarnings("unchecked")
			ListView<Node> content = (ListView<Node>) titledPane.getContent();

			ObservableList<Node> items = content.getItems();
			for (Node node : items) {
				Pane pane = (Pane) node;
				Label useridLabel = (Label) pane.getChildrenUnmodifiable().get(3);
				if (useridLabel.getText().equals(u.getUserid())) {

					ImageView _headImg = (ImageView) pane.getChildrenUnmodifiable().get(0);
					Label _username = (Label) pane.getChildren().get(1);
					Label _headfilename = (Label) pane.getChildren().get(6);
					Hyperlink _username_ = (Hyperlink) pane.getChildren().get(7);
					Label _autograph = (Label) pane.getChildren().get(2);
					ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
						@Override
						public void run() {
							Platform.runLater(new Runnable() {
								public void run() {
									if (u.getHead() != null) {
										ImageUtils.copyHead(u.getHead(), new File(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg()));
										ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 50, 50);
										_headImg.setImage(new Image(in));
									}
									_username.setText(u.getNickname());
									_username_.setText(u.getNickname());
									_headfilename.setText(u.getHeadImg());
									_autograph.setText(u.getAutograph());
								}
							});
						}
					});

				}
			}
		}

	}

	private void setUserStatus(User u, final boolean status) {
		ObservableList<TitledPane> panes = ComponentContainer._FRIEND_GROUP.getPanes();
		for (final TitledPane titledPane : panes) {
			@SuppressWarnings("unchecked")
			ListView<Node> content = (ListView<Node>) titledPane.getContent();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					titledPane.setText(updateOnlineCount(titledPane.getText(), status));
				}
			});

			ObservableList<Node> items = content.getItems();
			for (Node node : items) {
				Parent parent = (Parent) node;
				Label useridLabel = (Label) parent.getChildrenUnmodifiable().get(3);
				if (useridLabel.getText().equals(u.getUserid())) {
					final Label _status = (Label) parent.getChildrenUnmodifiable().get(4);

					ImageView _headImg = (ImageView) parent.getChildrenUnmodifiable().get(0);

					Platform.runLater(new Runnable() {
						public void run() {
							if (status) {
								ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 50, 50);
								_headImg.setImage(new Image(in));
							} else {
								ByteArrayInputStream in1 = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 50, 50);
								ByteArrayInputStream convertIn1 = ImageUtils.convertGray(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), in1);
								_headImg.setImage(new Image(convertIn1));
							}
							_status.setText(status ? User.ONLINE : User.OFFLINE);
						}
					});
				}
			}
		}
	}

	private String updateOnlineCount(String str, boolean flag) {
		int lastIndexOf = str.lastIndexOf("(");
		String s1 = str.substring(0, lastIndexOf + 1);
		if (flag) {
			s1 += Integer.valueOf(str.substring(lastIndexOf + 1, lastIndexOf + 2)) + 1;
		} else {
			s1 += Integer.valueOf(str.substring(lastIndexOf + 1, lastIndexOf + 2)) - 1;
		}
		s1 += str.substring(lastIndexOf + 2);
		return s1;
	}

	private void setMemberStatus(User u, final boolean status) {
		ChatGroupWind groupWind = CurrentWinContainer.getGroupWind(u.getChatGroupId());
		if (groupWind != null) {
			groupWind.updateMemberStatus(u, status);
		}
	}
}
