package cn.dunn.im.wind;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.constant.ThreadContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.ChatGroup;
import cn.dunn.im.model.HaveReadMessageChatNotify;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.UnReadMessageReq;
import cn.dunn.im.util.ImageUtils;

public class ChatGroupItem {
	private Pane pane;
	private int count;
	private ChatGroup chatGroup;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public ChatGroupItem(final ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
		this.pane = WindContainer.getChatGroupItem();

		Label _groupName = (Label) pane.getChildren().get(1);
		_groupName.setText(chatGroup.getChatGroupName());

		Label _groupId = (Label) pane.getChildren().get(2);
		_groupId.setText(chatGroup.getId());
		Label _unReadCount = (Label) pane.getChildren().get(3);
		if (chatGroup.getUnReadMessageCount() > 0) {
			_unReadCount.setVisible(true);
			if (chatGroup.getUnReadMessageCount() >= 99) {
				_unReadCount.setText("99");
			} else {
				_unReadCount.setText(chatGroup.getUnReadMessageCount() + "");
			}
		} else {
			_unReadCount.setVisible(false);
		}
		pane.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				pane.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						count++;
						if (count == 2) {
							ChatGroupWind chatGroupWind = new ChatGroupWind(chatGroup);
							CurrentWinContainer.openGroupWind(chatGroup.getId(), chatGroupWind);
							count = 0;
							if (isLoadUnReadMessage()) {
								Label _unReadCount = (Label) pane.getChildren().get(3);
								_unReadCount.setVisible(false);
							} else {
								chatGroupWind.setLastLoadTime(new Date().getTime());
							}
							chatGroupWind.messageLoading();
							UnReadMessageReq unReadMessageReq = new UnReadMessageReq();
							unReadMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
							unReadMessageReq.setAnother(chatGroup.getId());
							unReadMessageReq.setType(UnReadMessageReq.CHAT_GROUP);
							ConnectConstants.CHANNEL.writeAndFlush(unReadMessageReq);
							// 更新最后读取消息的时间
							HaveReadMessageChatNotify haveReadMessageChatNotify = new HaveReadMessageChatNotify();
							haveReadMessageChatNotify.setUserid(ConnectConstants.LOGIN_USER.getUserid());
							haveReadMessageChatNotify.setAnother(chatGroup.getId());
							haveReadMessageChatNotify.setType(Message.TYPE_GROUP);
							haveReadMessageChatNotify.setReadTime(new Date().getTime());
							ConnectConstants.CHANNEL.writeAndFlush(haveReadMessageChatNotify);

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

			}
		});
	}

	/**
	 * 是否有未读消息
	 * 
	 * @return
	 */
	public boolean isLoadUnReadMessage() {
		return pane.getChildren().get(3).isVisible();
	}

	public Pane build() {
		// 加载图片
		loadHeadImage();
		return pane;
	}

	public void loadHeadImage() {
		ImageView _headImage = (ImageView) pane.getChildren().get(0);
		_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "group_defult.png", 50, 50)));
		ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
			public void run() {
				File headFile = new File(ConnectConstants.USER_IMAGE_PATH + chatGroup.getHeadImg());
				if (!headFile.exists()) {
					ImageUtils.copyHead(chatGroup.getHead(), headFile);
				}
				Platform.runLater(new Runnable() {
					public void run() {
						_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + chatGroup.getHeadImg(), 50, 50)));
					}
				});
			}
		});
	}
}
