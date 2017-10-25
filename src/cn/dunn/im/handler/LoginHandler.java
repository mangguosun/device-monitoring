package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import cn.dunn.im.common.EventUtil;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.constant.ThreadContainer;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.events.LoginEvent;
import cn.dunn.im.events.MainEvent;
import cn.dunn.im.model.LoginRequestMessage;
import cn.dunn.im.model.LoginResponseMessage;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.UserInfoWind;
import cn.dunn.im.wind.UserInfoWind.TYPE;

public class LoginHandler extends ChannelHandlerAdapter {
	Point point = new Point();
	private String username;
	private String password;

	public LoginHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LoginRequestMessage loginRequestMessage = new LoginRequestMessage();
		loginRequestMessage.setUsername(username);
		loginRequestMessage.setPassword(password);
		ctx.writeAndFlush(loginRequestMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof LoginResponseMessage) {

//			Thread.sleep(1000); // TODO 模拟登录延迟效果
			LoginResponseMessage message = (LoginResponseMessage) msg;

			if (!message.getSuccess()) {
				final Pane _loginErrorPane = (Pane) ComponentContainer._LOGIN.getChildrenUnmodifiable().get(12);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ComponentContainer._LOGIN.getChildrenUnmodifiable().get(13).setVisible(false);
						ComponentContainer._LOGIN.getChildrenUnmodifiable().get(14).setVisible(false);
						_loginErrorPane.setVisible(true);
					}
				});

				ctx.close();
			} else {
				// TODO 配置文件执行
				ConnectConstants.LOGIN_USER = message.getUser();

				Platform.runLater(new Runnable() {
					public void run() {
						Stage stage = LoginEvent.stage;
						Pane root = ComponentContainer._MAIN_PARENT;
						ImageView _headImage = (ImageView) root.getChildren().get(1);
						ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "default.png", 80, 80);
						_headImage.setImage(new Image(in));

						setHeadImageClick(_headImage);

						Platform.runLater(new Runnable() {
							public void run() {

								ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
									public void run() {
										File file = new File(ConnectConstants.USER_IMAGE_PATH + ConnectConstants.LOGIN_USER.getHeadImg());
										if (!file.exists()) {
											File file2 = new File(ConnectConstants.USER_IMAGE_PATH + "default.png");
											ImageUtils.copyHead(file2, file);
										}
										ByteArrayInputStream inputStream = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + ConnectConstants.LOGIN_USER.getHeadImg(), 80, 80);
										// TODO 设置大小的图片
										_headImage.setImage(new Image(inputStream));

									}
								});

							}
						});

						Scene scene = new Scene(root, 300, 700);
						stage.setScene(scene);
						stage.setTitle("main");
						MainEvent.stage = stage;
						EventUtil.setCommonEvent(stage, root, point);
					}
				});

				Label _username = (Label) ComponentContainer._MAIN_PARENT.getChildrenUnmodifiable().get(6);
				_username.setText(ConnectConstants.LOGIN_USER.getNickname());
				Label _autograph = (Label) ComponentContainer._MAIN_PARENT.getChildrenUnmodifiable().get(7);
				_autograph.setText(ConnectConstants.LOGIN_USER.getAutograph());

			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			ctx.close();
		} else {
			ctx.fireExceptionCaught(cause);
		}
	}

	private void setHeadImageClick(ImageView _headImage) {
		_headImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				UserInfoWind userInfoWind = new UserInfoWind(ConnectConstants.LOGIN_USER, TYPE.EDIT_INFO);
				userInfoWind.show();
			}
		});
	}
}
