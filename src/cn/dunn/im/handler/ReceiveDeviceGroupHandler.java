package cn.dunn.im.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.DeviceIMContainer;
import cn.dunn.im.container.DevicesContainer;
import cn.dunn.im.container.FriendsContainer;
import cn.dunn.im.container.UserIMContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.Device;
import cn.dunn.im.model.DeviceGroup;
import cn.dunn.im.model.DeviceRequestMessage;
import cn.dunn.im.model.FriendGroup;
import cn.dunn.im.model.HaveReadMessageChatNotify;
import cn.dunn.im.model.LoginRequestMessage;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.MyDeviceGroup;
import cn.dunn.im.model.MyFriendGroup;
import cn.dunn.im.model.UnReadMessageReq;
import cn.dunn.im.model.User;
import cn.dunn.im.wind.DeviceWind;

public class ReceiveDeviceGroupHandler extends ChannelHandlerAdapter {
	private int count;
	private ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

	@Override
	public void channelRead(ChannelHandlerContext ctx, final Object msg) throws Exception {

		if (msg instanceof MyDeviceGroup) {
			if (ComponentContainer._LOGINMAIN == null) {
				ctx.fireChannelRead(msg);
				return;
			}

			ConnectConstants.CHANNEL.eventLoop().execute(new Runnable() {
				@Override
				public void run() {
					// 从主面板中获取好友分组
					final TabPane _deviceGroup = (TabPane) getDeviceList();
					ComponentContainer._Device_GROUP = _deviceGroup;
					MyDeviceGroup groups = (MyDeviceGroup) msg;
					List<DeviceGroup> deviceGroups = groups.getDeviceGroup();
					Map<String, Device> map = new HashMap<String, Device>();
					Map<String, Pane> map1 = new HashMap<String, Pane>();
					List<Device> devicess = new ArrayList<Device>();

					if (deviceGroups != null) {

						for (Tab tabs : _deviceGroup.getTabs()) {
							Platform.runLater(new Runnable() {
								public void run() {
									_deviceGroup.getTabs().remove(tabs);

								}
							});
						}
						for (int j = 0; j < deviceGroups.size(); j++) {
							DeviceGroup deviceGroup = deviceGroups.get(j);
							ListView<Node> listView = new ListView<Node>();
							setEvent(listView);
							List<Device> devices = deviceGroup.getDevices();

							int onlineCount = 0;
							for (Device device : devices) {
								map.put(device.getDeviceid(), device);
								devicess.add(device);
								if (device.getIsOnline()) {
									onlineCount++;
								}
								Pane pane = WindContainer.getDeviceItem(device);
								map1.put(device.getDeviceid(), pane);
								listView.getItems().add(pane);
							}
							final String tp = deviceGroup.getGroupName() + "(" + onlineCount + "/" + devices.size()
									+ ")";
							final int key = j;
							Platform.runLater(new Runnable() {
								public void run() {
									Tab tab = generateTab(tp);
									tab.setContent(listView);
									_deviceGroup.getTabs().addAll(tab);

								}
							});
						}
					}
					DevicesContainer.INSTANCE.setDevices(devicess);
					DeviceIMContainer.INSTANCE.setDevices(map);
					DeviceIMContainer.INSTANCE.setDeviceComponent(map1);
				}

			});

		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private Tab generateTab(String tabName) {
		Tab tab = new Tab(tabName);
		return tab;
	}

	private TabPane getDeviceList() {

		Pane node1 = (Pane) ComponentContainer._LOGINMAIN.lookup("#deviceBox");
		TabPane node2 = (TabPane) node1.getChildren().get(0);
		return node2;
		// Pane node1 = (Pane) ComponentContainer._LOGINMAIN.lookup("#deviceBox");
		// ScrollPane node2 = (ScrollPane) node1.getChildren().get(0);
		// return node2;

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

					Label _deviceip = (Label) pane.getChildren().get(2);
					Label _deviceame = (Label) pane.getChildren().get(1);
					Label _autograph = (Label) pane.getChildren().get(6);
					Label _status = (Label) pane.getChildren().get(4);
					Label _deviceid = (Label) pane.getChildren().get(3);
					Label _unReadCount = (Label) pane.getChildren().get(5);

					DeviceWind deviceWind = new DeviceWind(_deviceid.getText(), _deviceame.getText(),
							_autograph.getText(), _deviceip.getText(), _status.getText());

					if (_unReadCount.isVisible()) {

						deviceWind.messageLoading();
						_unReadCount.setVisible(false);
						_unReadCount.setText("0");
					}
					// // 发起获取未读消息请求
					// UnReadMessageReq unReadMessageReq = new UnReadMessageReq();
					// unReadMessageReq.setType(UnReadMessageReq.CHAT);
					// unReadMessageReq.setUserid(ConnectConstants.LOGIN_USER.getUserid());
					// unReadMessageReq.setAnother(_deviceid.getText());
					// ConnectConstants.CHANNEL.writeAndFlush(unReadMessageReq);
					//
					// // 更新最后读取消息的时间
					// HaveReadMessageChatNotify haveReadMessageChatNotify = new
					// HaveReadMessageChatNotify();
					// haveReadMessageChatNotify.setUserid(ConnectConstants.LOGIN_USER.getUserid());
					// haveReadMessageChatNotify.setAnother(_deviceid.getText());
					// haveReadMessageChatNotify.setType(Message.TYPE_CHAT);
					// haveReadMessageChatNotify.setReadTime(new Date().getTime());
					// ConnectConstants.CHANNEL.writeAndFlush(haveReadMessageChatNotify);

					deviceWind.setLastLoadTime(new Date().getTime());
					CurrentWinContainer.openDeviceWind(_deviceid.getText(), deviceWind);

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
