package cn.dunn.im.wind;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import cn.dunn.im.common.AbstractDesktop;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.UserIMContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.HistoryMessageReq;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.User;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.UserInfoWind.TYPE;

public class ChatWind extends AbstractDesktop {
	private String username;
	private String autograph;
	private String userid;

	/**
	 * 最后加载的时间
	 */
	private Long lastLoadTime;

	public Long getLastLoadTime() {
		return lastLoadTime;
	}

	public void setLastLoadTime(Long lastLoadTime) {
		this.lastLoadTime = lastLoadTime;
	}

	private Stage stage = new Stage(StageStyle.TRANSPARENT);;
	private Parent root = WindContainer.getCharWind();
	private Scene scene = new Scene(getRoot());
	private String headFileName;
	private String status;

	public ChatWind(String userid, String username, String autograph, String headFileName, String status) {
		this.username = username;
		this.autograph = autograph;
		this.userid = userid;
		this.headFileName = headFileName;
		this.status = status;
	}

	@Override
	protected Stage getStage() {
		return stage;
	}

	@Override
	protected Parent getRoot() {
		return root;
	}

	@Override
	protected Scene getScene() {
		return scene;
	}

	@Override
	protected int minIndex() {
		return 7;
	}

	@Override
	protected int closeIndex() {
		return 8;
	}

	@Override
	protected void before() {
		CurrentWinContainer.closeChatWind(this.userid);
	}

	@Override
	protected void handle() {
		// 设置头像
		ImageView _headImage = (ImageView) getRoot().getChildrenUnmodifiable().get(1);
		if (User.OFFLINE.equals(status)) {
			ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + headFileName, 60, 60);
			ByteArrayInputStream in1 = ImageUtils.convertGray(ConnectConstants.USER_IMAGE_PATH + headFileName, in);
			_headImage.setImage(new Image(in1));
		} else {
			_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + headFileName, 60, 60)));
		}

		// 设置内容滚动区域V不能滚动
		SplitPane splitPane = (SplitPane) getRoot().getChildrenUnmodifiable().get(0);
		ObservableList<Node> items = splitPane.getItems();
		AnchorPane anchorPane = (AnchorPane) items.get(0);
		ScrollPane scrollPane = (ScrollPane) anchorPane.getChildren().get(0);
		scrollPane.setFitToWidth(true);

		// 设置加载历史消息事件
		Button loadMore = (Button) getChatArea().getChildrenUnmodifiable().get(2);
		loadMore.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				historyMessageLoading();
				HistoryMessageReq historyMessageReq = new HistoryMessageReq();
				historyMessageReq.setType(Message.TYPE_CHAT);
				historyMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
				historyMessageReq.setWindKey(userid);
				historyMessageReq.setLastLoadTime(getLastLoadTime());
				ConnectConstants.CHANNEL.writeAndFlush(historyMessageReq);
			}

		});
		// 设置关闭按钮事件
		Button _close = (Button) root.getChildrenUnmodifiable().get(6);
		_close.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				CurrentWinContainer.closeChatWind(userid);
				stage.close();

			}
		});
		// 设置发送按钮事件
		Button _send = (Button) root.getChildrenUnmodifiable().get(5);
		_send.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				Message msg = new Message();

				SplitPane splitPane = (SplitPane) getRoot().getChildrenUnmodifiable().get(0);
				ObservableList<Node> items = splitPane.getItems();
				AnchorPane anchorPane = (AnchorPane) items.get(1);
				TextArea waitArea = (TextArea) anchorPane.getChildren().get(0);

				msg.setBody(waitArea.getText().trim());
				msg.setFrom(ConnectConstants.LOGIN_USER.getUserid());
				msg.setTo(userid);
				msg.setCreateTime(new Date().getTime());
				msg.setType(Message.TYPE_CHAT);
				msg.setUser(ConnectConstants.LOGIN_USER);
				ConnectConstants.CHANNEL.writeAndFlush(msg);

				receiveMessage(msg);

				waitArea.setText("");

				AnchorPane anchorPane1 = (AnchorPane) items.get(0);
				ScrollPane scrollPane = (ScrollPane) anchorPane1.getChildren().get(0);
				System.out.println(scrollPane.getPrefHeight());
				System.out.println(scrollPane.getHeight());
				System.out.println(scrollPane.getHmax());
				System.out.println(scrollPane.getMaxHeight());
				System.out.println(scrollPane.getPrefViewportHeight());
				scrollPane.setVvalue(scrollPane.getHeight() + 600);

			}
		});

		Label _username = (Label) root.getChildrenUnmodifiable().get(2);
		Label _autograph = (Label) root.getChildrenUnmodifiable().get(3);
		Label _userid = (Label) root.getChildrenUnmodifiable().get(9);
		Hyperlink _username_ = (Hyperlink) root.getChildrenUnmodifiable().get(10);
		_username_.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				UserInfoWind wind = new UserInfoWind(UserIMContainer.INSTANCE.getFriends().get(userid), TYPE.SEND_MESSAGE);
				wind.show();
			}
		});

		_userid.setText(userid);
		_username.setText(username);
		_username_.setText(username);
		_autograph.setText(autograph);
		stage.setTitle(username);
	}

	/**
	 * 接受消息
	 * 
	 * @param message
	 */
	public void receiveMessage(final Message message) {
		SplitPane splitPane = (SplitPane) getRoot().getChildrenUnmodifiable().get(0);
		ObservableList<Node> items = splitPane.getItems();
		AnchorPane anchorPane = (AnchorPane) items.get(0);
		ScrollPane scrollPane = (ScrollPane) anchorPane.getChildren().get(0);
		final VBox vbox = (VBox) scrollPane.getContent();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vbox.getChildren().add(new ChatItem(message).build());
			}
		});
	}

	/**
	 * 接受历史消息
	 * 
	 * @param messages
	 */
	public void receiveHistoryMessage(List<Message> messages) {
		if (messages.size() != 0)
			lastLoadTime = messages.get(messages.size() - 1).getCreateTime();
		for (final Message message : messages) {
			SplitPane splitPane = (SplitPane) getRoot().getChildrenUnmodifiable().get(0);
			ObservableList<Node> items = splitPane.getItems();
			AnchorPane anchorPane = (AnchorPane) items.get(0);
			ScrollPane scrollPane = (ScrollPane) anchorPane.getChildren().get(0);
			final VBox vbox = (VBox) scrollPane.getContent();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					vbox.getChildren().add(0, new ChatItem(message).build());
				}
			});
		}
	}

	/**
	 * 接受未读消息
	 * 
	 * @param messages
	 */
	public void receiveUnReadMessage(List<Message> messages) {
		Collections.reverse(messages);
		for (Message message2 : messages) {
			receiveMessage(message2);
		}
	}

	/**
	 * 获取进度控件
	 * 
	 * @return
	 */
	private ProgressIndicator getSchedule() {
		SplitPane splitPane = (SplitPane) getRoot().getChildrenUnmodifiable().get(0);
		ObservableList<Node> items = splitPane.getItems();
		AnchorPane anchorPane = (AnchorPane) items.get(0);
		ProgressIndicator _schedule = (ProgressIndicator) anchorPane.getChildren().get(1);
		return _schedule;
	}

	/**
	 * 历史消息加载中
	 */
	public void historyMessageLoading() {
		final Button _load = (Button) getChatArea().getChildrenUnmodifiable().get(2);
		final ProgressBar _loading = (ProgressBar) getChatArea().getChildrenUnmodifiable().get(3);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				_load.setVisible(false);
				_loading.setVisible(true);
			}
		});

	}

	/**
	 * 历史消息加载完成
	 * 
	 * @param loadIsShow
	 */
	public void historyMessageLoadFinish(final boolean loadIsShow) {
		final Button _load = (Button) getChatArea().getChildrenUnmodifiable().get(2);
		final ProgressBar _loading = (ProgressBar) getChatArea().getChildrenUnmodifiable().get(3);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				_load.setVisible(true);
				if (!loadIsShow) {
					_load.setDisable(true);
					_load.setText("没有更多消息了");
				}
				_loading.setVisible(false);
			}
		});

	}

	/**
	 * 消息接受完成
	 */
	public void receiveFinish() {
		ProgressIndicator _schedule = getSchedule();
		_schedule.setVisible(false);
	}

	/**
	 * 消息加载中
	 */
	public void messageLoading() {
		ProgressIndicator _schedule = getSchedule();
		_schedule.setVisible(true);
	}

	/**
	 * 获取聊天区域
	 * 
	 * @return
	 */
	private AnchorPane getChatArea() {
		SplitPane splitPane = (SplitPane) getRoot().getChildrenUnmodifiable().get(0);
		ObservableList<Node> items = splitPane.getItems();
		AnchorPane anchorPane = (AnchorPane) items.get(0);
		return anchorPane;
	}

}
