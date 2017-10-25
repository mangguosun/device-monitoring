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
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.constant.ThreadContainer;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.model.Device;
import cn.dunn.im.model.Message;
import cn.dunn.im.model.NotifyMessageDevice;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.ChatGroupWind;

public class NotifyMessageDeviceHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof NotifyMessageDevice) {
			NotifyMessageDevice message = (NotifyMessageDevice) msg;
			Device device = (Device) message.getBody();
			switch (message.getType()) {
			case Message.TYPE_CHAT:

				if (message.getStatus().equals(NotifyMessageDevice.STATUS_OFFLINE)) {
					setUserStatus(device, false);
				} else if (message.getStatus().equals(NotifyMessageDevice.STATUS_ONLINE)) {
					setUserStatus(device, true);
				} else if (message.getStatus().equals(NotifyMessageDevice.STATUS_MODIFY)) {
					modifyDeviceInfo(device);
				}
				break;
			case Message.TYPE_GROUP:

				if (message.getStatus().equals(NotifyMessageDevice.STATUS_OFFLINE)) {
					// setMemberStatus(device, false);
				} else if (message.getStatus().equals(NotifyMessageDevice.STATUS_ONLINE)) {
					// setMemberStatus(device, true);
				} else if (message.getStatus().equals(NotifyMessageDevice.STATUS_MODIFY)) {
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
	private void modifyDeviceInfo(Device u) {

		ObservableList<Tab> panes = ComponentContainer._Device_GROUP.getTabs();
		
		for (final Tab tab : panes) {
			@SuppressWarnings("unchecked")
			ListView<Node> content = (ListView<Node>) tab.getContent();
			ObservableList<Node> items = content.getItems();
			for (Node node : items) {
				Pane pane = (Pane) node;
				Label useridLabel = (Label) pane.getChildrenUnmodifiable().get(3);
				if (useridLabel.getText().equals(u.getDeviceid())) {

					ImageView _headImg = (ImageView) pane.getChildrenUnmodifiable().get(0);
					Label _devicename = (Label) pane.getChildren().get(1);
					Label _deviceip = (Label) pane.getChildren().get(2);
					Hyperlink _devicename_ = (Hyperlink) pane.getChildren().get(7);
					Label _autograph = (Label) pane.getChildren().get(6);
					ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
						@Override
						public void run() {
							Platform.runLater(new Runnable() {
								public void run() {
									_devicename.setText(u.getDevicename());
									_deviceip.setText(u.getDeviceip());
									_devicename_.setText(u.getDevicename());
									_autograph.setText(u.getAutograph());
								}
							});
						}
					});

				}
			}
		}

	}

	private void setUserStatus(Device u, final boolean status) {
		ObservableList<Tab> panes = ComponentContainer._Device_GROUP.getTabs();
		for (final Tab tab : panes) {
			@SuppressWarnings("unchecked")
			ListView<Node> content = (ListView<Node>) tab.getContent();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					tab.setText(updateOnlineCount(tab.getText(), status));
				}
			});

			ObservableList<Node> items = content.getItems();
			for (Node node : items) {
				Parent parent = (Parent) node;
				Label useridLabel = (Label) parent.getChildrenUnmodifiable().get(3);
				if (useridLabel.getText().equals(u.getDeviceid())) {
					final Label _status = (Label) parent.getChildrenUnmodifiable().get(4);

					ImageView _headImg = (ImageView) parent.getChildrenUnmodifiable().get(0);

					Platform.runLater(new Runnable() {
						public void run() {
							if (status) {
								ByteArrayInputStream in = ImageUtils
										.scale(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", 50, 50);
								_headImg.setImage(new Image(in));
							} else {
								ByteArrayInputStream in1 = ImageUtils
										.scale(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", 50, 50);
								ByteArrayInputStream convertIn1 = ImageUtils
										.convertGray(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", in1);
								_headImg.setImage(new Image(convertIn1));
							}
							_status.setText(status ? Device.ONLINE : Device.OFFLINE);
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

	private void setMemberStatus(Device u, final boolean status) {

	}
}
