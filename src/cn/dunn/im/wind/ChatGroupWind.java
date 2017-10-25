package cn.dunn.im.wind;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import cn.dunn.im.common.AbstractDesktop;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.constant.ThreadContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.FriendsContainer;
import cn.dunn.im.container.ResourceContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.ChatGroup;
import cn.dunn.im.model.HistoryMessageReq;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.UnReadMessageReq;
import cn.dunn.im.model.User;
import cn.dunn.im.util.ImageUtils;

public class ChatGroupWind extends AbstractDesktop {
	private ChatGroup chatGroup;
	private int count;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
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
	private Pane root = WindContainer.getCharGroupWind();
	private Scene scene = new Scene(root);

	public ChatGroupWind(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	@Override
	protected Stage getStage() {
		return stage;
	}

	@Override
	protected Pane getRoot() {
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
		CurrentWinContainer.closeGroupWind(chatGroup.getId());
	}

	@Override
	protected void handle() {
		// 设置头像
		ImageView _headIamge = (ImageView) getRoot().getChildren().get(1);
		_headIamge.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + chatGroup.getHeadImg(), 60, 60)));

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
				historyMessageReq.setType(Message.TYPE_GROUP);
				historyMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
				historyMessageReq.setWindKey(chatGroup.getId());
				historyMessageReq.setLastLoadTime(getLastLoadTime());
				ConnectConstants.CHANNEL.writeAndFlush(historyMessageReq);
			}

		});
		// 设置关闭按钮事件
		Button _close = (Button) root.getChildrenUnmodifiable().get(6);
		_close.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				CurrentWinContainer.closeGroupWind(chatGroup.getId());
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
				msg.setTo(chatGroup.getId());
				msg.setCreateTime(new Date().getTime());
				msg.setType(Message.TYPE_GROUP);
				msg.setUser(ConnectConstants.LOGIN_USER);
				ConnectConstants.CHANNEL.writeAndFlush(msg);

				// receiveMessage(msg);

				waitArea.setText("");

			}
		});

		Label _chatGrouName = (Label) root.getChildrenUnmodifiable().get(2);
		Label _charGroupTheme = (Label) root.getChildrenUnmodifiable().get(3);
		Label _chatGroupId = (Label) root.getChildrenUnmodifiable().get(9);
		_chatGroupId.setText(chatGroup.getId());
		_chatGrouName.setText(chatGroup.getChatGroupName());
		_charGroupTheme.setText(chatGroup.getTheme());
		stage.setTitle(chatGroup.getChatGroupName());
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
		if (messages.size() == 0)
			return;
		// setLastLoadTime(messages.get(0).getCreateTime());
		// Collections.reverse(messages);
		setLastLoadTime(messages.get(messages.size() - 1).getCreateTime());
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
		if (messages.size() == 0)
			return;
		setLastLoadTime(messages.get(0).getCreateTime());
		Collections.reverse(messages);
		for (Message message2 : messages) {
			receiveMessage(message2);
		}
		// 设置加载历史消息标记
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

	/**
	 * 群成员加载中
	 */
	public void memberLoading() {
		root.getChildren().get(10).setVisible(false);
		root.getChildren().get(11).setVisible(true);
	}

	/**
	 * 群成员加载完成
	 */
	public void memberLoadFinish(List<User> members) {
		@SuppressWarnings("unchecked")
		final ListView<Node> listView = (ListView<Node>) root.getChildren().get(10);
		for (final User user : members) {
			final Pane pane = WindContainer.getMemberItem();

			pane.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					count++;
					if (count == 2) {
						count = 0;
						if (user.getUserid().equals(ConnectConstants.LOGIN_USER.getUserid())) {
							System.out.println("是自己");
						} else {
							if (FriendsContainer.INSTANCE.isFriends(user.getUserid())) {
								// TODO 默认在线 以后修改
								ChatWind wind = new ChatWind(user.getUserid(), user.getNickname(), user.getAutograph(), user.getHeadImg(), User.ONLINE);
								wind.setLastLoadTime(new Date().getTime());
								CurrentWinContainer.openChatWind(user.getUserid(), wind);

								// 发送未读消息请求
								wind.messageLoading();
								UnReadMessageReq unReadMessageReq = new UnReadMessageReq();
								unReadMessageReq.setType(UnReadMessageReq.CHAT);
								unReadMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
								unReadMessageReq.setAnother(user.getUserid());
								ConnectConstants.CHANNEL.writeAndFlush(unReadMessageReq);
							} else {
								System.out.println("不是好友");
							}
						}
					} else if (count == 1) {
						executor.schedule(new Runnable() {
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

			boolean flag = false;
			if (ChatGroup.ROLE_GROUP_CREATOR.equals(user.getGroupRole())) {
				ImageView _role = (ImageView) pane.getChildren().get(0);
				_role.setImage(ResourceContainer.getCreator());
				Platform.runLater(new Runnable() {
					public void run() {
						listView.getItems().add(0, pane);
					}
				});
				flag = true;
			}
			if (ChatGroup.ROLE_GROUP_ADMIN.equals(user.getGroupRole())) {
				ImageView _role = (ImageView) pane.getChildren().get(0);
				_role.setImage(ResourceContainer.getAdmin());
				Platform.runLater(new Runnable() {
					public void run() {
						listView.getItems().add(1, pane);
					}
				});

				flag = true;
			}
			ImageView _headImg = (ImageView) pane.getChildren().get(1);
			Label _isOnling = (Label) pane.getChildren().get(4);
			if (user.getIsOnline()) {
				_isOnling.setText(User.ONLINE);
				_headImg.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "default.png", 16, 16)));// 设置默认头像
			} else {
				_isOnling.setText(User.OFFLINE);
				_headImg.setImage(new Image(ImageUtils.convertGray("default.png", ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "default.png", 16, 16))));// 设置默认头像
			}

			File headFile = new File(ConnectConstants.USER_IMAGE_PATH + user.getHeadImg());
			ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
				public void run() {
					if (!headFile.exists())
						ImageUtils.copyHead(user.getHead(), new File(ConnectConstants.USER_IMAGE_PATH + user.getHeadImg()));
					if (user.getIsOnline()) {
						_headImg.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + user.getHeadImg(), 16, 16)));// 设置默认头像
					} else {
						_headImg.setImage(new Image(ImageUtils.convertGray(user.getHeadImg(), ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + user.getHeadImg(), 16, 16))));// 设置默认头像
					}

				}
			});

			Label _nikename = (Label) pane.getChildren().get(2);
			Label _userid = (Label) pane.getChildren().get(3);
			Platform.runLater(new Runnable() {
				public void run() {
					_nikename.setText(user.getNickname());
					_userid.setText(user.getUserid());
				}
			});
			if (!flag)
				Platform.runLater(new Runnable() {
					public void run() {
						listView.getItems().add(pane);
					}
				});
		}
		root.getChildren().get(11).setVisible(false);
		listView.setVisible(true);
	}

	public void updateMemberStatus(User u, final boolean status) {
		@SuppressWarnings("unchecked")
		ListView<Node> listView = (ListView<Node>) root.getChildren().get(10);
		ObservableList<Node> items = listView.getItems();
		for (Node node : items) {
			Pane _pane = (Pane) node;
			Label _userid = (Label) _pane.getChildren().get(3);
			if (_userid.getText().equals(u.getUserid())) {
				final Label _status = (Label) _pane.getChildren().get(4);
				final ImageView _headImage = (ImageView) _pane.getChildren().get(1);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						if (status) {
							_status.setText(User.ONLINE);
							_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 16, 16)));
						} else {
							_status.setText(User.OFFLINE);
							_headImage.setImage(new Image(ImageUtils.convertGray(u.getHeadImg(), ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 16, 16))));
						}
					}
				});
				return;
			}
		}
	}
}
