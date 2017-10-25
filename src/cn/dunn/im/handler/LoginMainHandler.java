package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
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
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.events.LoginEvent;
import cn.dunn.im.events.LoginMainEvent;
import cn.dunn.im.events.MainEvent;
import cn.dunn.im.model.DeviceRequestMessage;
import cn.dunn.im.model.LoginRequestMessage;
import cn.dunn.im.model.LoginResponseMessage;
import cn.dunn.im.model.MyDeviceGroup;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.UserInfoWind;
import cn.dunn.im.wind.UserInfoWind.TYPE;

public class LoginMainHandler extends ChannelHandlerAdapter {
	Point point = new Point();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Parent root = ComponentContainer._LOGINMAIN;
		if (msg instanceof LoginResponseMessage) {

			// Thread.sleep(1000); // TODO 模拟登录延迟效果
			LoginResponseMessage message = (LoginResponseMessage) msg;

			if (!message.getSuccess()) {
				final Pane _loginErrorPane = (Pane) root.lookup("#loginError");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Label errmsg = (Label) root.lookup("#errmsg");
						errmsg.setText(message.getMsg());
						_loginErrorPane.setVisible(true);
					}
				});
			} else {
				// TODO 配置文件执行
				ConnectConstants.LOGIN_USER = message.getUser();
				Platform.runLater(new Runnable() {
					public void run() {

						Pane userinfo = (Pane) root.lookup("#userinfo");
						ImageView headImage = (ImageView) root.lookup("#headImage");
						ImageView buttonLogin = (ImageView) root.lookup("#buttonLogin");
						Label nickname = (Label) root.lookup("#headnickname");
						Label autograph = (Label) root.lookup("#headautograph");
						Pane loginpane = (Pane) root.lookup("#loginpane");
						headImage.setOnMouseClicked(new EventHandler<Event>() {
							@Override
							public void handle(Event event) {
								UserInfoWind userInfoWind = new UserInfoWind(ConnectConstants.LOGIN_USER, TYPE.EDIT_INFO);
								userInfoWind.show();
							}
						});
						
						ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "default.png", 80,80);
						headImage.setImage(new Image(in));
						
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
										headImage.setImage(new Image(inputStream));

									}
								});

							}
						});
						nickname.setText(ConnectConstants.LOGIN_USER.getNickname());
						autograph.setText(ConnectConstants.LOGIN_USER.getAutograph());
						userinfo.setVisible(true);
						buttonLogin.setVisible(false);
						loginpane.setVisible(false);
						
						MyDeviceGroup myDeviceGroup = new MyDeviceGroup();
						myDeviceGroup.setUserid(ConnectConstants.LOGIN_USER.getUserid());
						ConnectConstants.CHANNEL.writeAndFlush(myDeviceGroup);
						
					}
				});

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

}
