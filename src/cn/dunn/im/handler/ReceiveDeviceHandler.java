package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.prism.paint.Color;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.DevicesContainer;
import cn.dunn.im.container.FriendsContainer;
import cn.dunn.im.container.UserIMContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.io.ServerInfoVo;
import cn.dunn.im.model.Device;
import cn.dunn.im.model.DeviceGroup;
import cn.dunn.im.model.DeviceRequestMessage;
import cn.dunn.im.model.DeviceResponseMessage;
import cn.dunn.im.model.FriendGroup;
import cn.dunn.im.model.HaveReadMessageChatNotify;
import cn.dunn.im.model.LoginRequestMessage;
import cn.dunn.im.model.LoginResponseMessage;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.MyDeviceGroup;
import cn.dunn.im.model.MyFriendGroup;
import cn.dunn.im.model.UnReadMessageReq;
import cn.dunn.im.model.User;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.ChatWind;

public class ReceiveDeviceHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		DeviceRequestMessage deviceRequestMessage = new DeviceRequestMessage();

		

		Map<String, String> map = System.getenv();
		
		String userName = map.get("USERNAME");// 获取用户名
		String computerName = map.get("COMPUTERNAME");// 获取计算机名
		String userDomain = map.get("USERDOMAIN");// 获取计算机域名
		
		ServerInfoVo serviceInfo = ConnectConstants.SERVERINFOVO;
		String computerIp = serviceInfo.getServerIP();
		String computerMac = serviceInfo.getMAC();// 获取计算机域名
		
		deviceRequestMessage.setDevicename(userName);
		deviceRequestMessage.setAutograph(computerName);
		deviceRequestMessage.setDeviceip(computerIp);
		deviceRequestMessage.setDevicemac(computerMac);

		deviceRequestMessage.setPassword("666666");
		
		ctx.writeAndFlush(deviceRequestMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, final Object msg) throws Exception {

		Parent root = ComponentContainer._LOGINMAIN;
		if (msg instanceof DeviceResponseMessage) {

			// Thread.sleep(1000); // TODO 模拟登录延迟效果
			DeviceResponseMessage message = (DeviceResponseMessage) msg;

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
				ConnectConstants.LOGIN_DEVICE = message.getDevice();
				 Platform.runLater(new Runnable() {
				 public void run() {
				
					 MyDeviceGroup myDeviceGroup = new MyDeviceGroup();
					 ConnectConstants.CHANNEL.writeAndFlush(myDeviceGroup);
				
				 }
				 });

			}

		} else {
			ctx.fireChannelRead(msg);
		}
	}

}
