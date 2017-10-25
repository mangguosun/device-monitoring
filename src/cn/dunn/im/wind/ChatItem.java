package cn.dunn.im.wind;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.User;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.UserInfoWind.TYPE;

public class ChatItem {
	private Pane pane;
	private Message message;

	public ChatItem(Message message) {
		this.message = message;
		this.pane = WindContainer.getChatItem();
		Hyperlink _nikename = (Hyperlink) pane.getChildren().get(0);
		_nikename.setText(message.getUser().getNickname());
		Label _createTime = (Label) pane.getChildren().get(1);
		_createTime.setText(new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss").format(new Date(message.getCreateTime())));
		Label _body = (Label) pane.getChildren().get(2);
		_body.setText(message.getBody());
	}

	public Pane build() {
		setEvent();
		loadHeadImage();
		return pane;
	}

	// 加载头像
	private void loadHeadImage() {
		ImageView _headImage = (ImageView) pane.getChildren().get(3);
		_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + message.getUser().getHeadImg(), 50, 50)));
	}

	private void setEvent() {
		Hyperlink _nickname = (Hyperlink) pane.getChildren().get(0);
		_nickname.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				User user = message.getUser();
				if (user.getUserid().equals(ConnectConstants.LOGIN_USER.getUserid())) {
					new UserInfoWind(user, TYPE.EDIT_INFO).show();
				} else {
					new UserInfoWind(user, TYPE.SEND_MESSAGE).show();
				}
			}
		});

	}
}
