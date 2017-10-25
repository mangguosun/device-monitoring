package cn.dunn.im.handler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.model.ModifyUserInfoResp;
import cn.dunn.im.util.ImageUtils;

public class ModifyUserInfoRespHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ModifyUserInfoResp) {
			ModifyUserInfoResp resp = (ModifyUserInfoResp) msg;
			if (resp.getSuccess()) {
				System.out.println("修改成功");
				ConnectConstants.LOGIN_USER = resp.getUser();
			
				Parent root = ComponentContainer._LOGINMAIN;
				Label _username = (Label) root.lookup("#headnickname");
				Label _autograph = (Label) root.lookup("#headautograph");
				
				
				Platform.runLater(new Runnable() {
					public void run() {
						_username.setText(ConnectConstants.LOGIN_USER.getNickname());
						_autograph.setText(ConnectConstants.LOGIN_USER.getAutograph());
						if (resp.getUser().getHead() != null) {
							try {
								FileUtils.copyFile(resp.getUser().getHead(), new File(ConnectConstants.USER_IMAGE_PATH + ConnectConstants.LOGIN_USER.getHeadImg()));
								ImageView _headImage = (ImageView)  root.lookup("#headImage");
								_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + ConnectConstants.LOGIN_USER.getHeadImg(), 80, 80)));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
			} else {
				System.out.println("修改失败");
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

}
