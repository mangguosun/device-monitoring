package cn.dunn.im.container;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.constant.ThreadContainer;
import cn.dunn.im.model.Device;
import cn.dunn.im.model.User;
import cn.dunn.im.util.ImageUtils;
import cn.dunn.im.wind.DeviceInfoWind;
import cn.dunn.im.wind.UserInfoWind;
import cn.dunn.im.wind.UserInfoWind.TYPE;
import cn.dunn.im.wind.DeviceInfoWind.DEVICETYPE;
public class WindContainer {
	private static URL friendItemUrl;
	private static URL charWindUrl;
	private static URL mainUrl;
	private static URL loginUrl;
	private static URL loginMainUrl;
	private static URL chatItemUrl;
	private static URL chatGroupItemUrl;
	private static URL chatGroupWindUrl;
	private static URL memberUrl;
	private static URL userInfoWindUrl;
	private static URL deviceItemUrl;
	private static URL deviceWindUrl;
	private static URL deviceInfoWindUrl;
	static {

		friendItemUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/friendItem.fxml");
		chatItemUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/chatItem.fxml");
		charWindUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/charWind.fxml");
		
		chatGroupItemUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/chatGroupItem.fxml");
		chatGroupWindUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/charGroupWind.fxml");
		
		mainUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/main.fxml");
		loginUrl = Thread.currentThread().getContextClassLoader().getResource("login/xml/login.fxml");
		loginMainUrl = Thread.currentThread().getContextClassLoader().getResource("loginmain/xml/loginmain.fxml");
		
		memberUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/member.fxml");
		userInfoWindUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/userInfoWind.fxml");
		deviceItemUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/deviceItem.fxml");
		deviceWindUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/deviceWind.fxml");
		
		
		deviceInfoWindUrl = Thread.currentThread().getContextClassLoader().getResource("main/xml/deviceInfoWind.fxml");
		
	}
	public static Pane getDeviceItem(Device d) {
		try {
			Pane load = FXMLLoader.load(deviceItemUrl);
				
			
			// ImageView _headImg = (ImageView) load.getChildren().get(0);//
			// TODO
			// 设置头像
			// _headImg.setImage(new
			// Image("http://www.qqzhi.com/uploadpic/2014-11-12/190218795.jpg"));

			// BufferedImage grayImage = null;
			// ByteArrayOutputStream output = null;
			// ImageIO.write(grayImage, "", output);
			// byte[] byteArray = output.toByteArray();
			// ByteArrayInputStream byteArrayInputStream = new
			// ByteArrayInputStream(byteArray);
			// ImageView imageView = new ImageView(new
			// Image(byteArrayInputStream));
			// 以上具体灰度图转换 参照image.ImageDemo
			Label devicenameLabel = (Label) load.getChildrenUnmodifiable().get(1);
			devicenameLabel.setText(d.getDevicename());
			Label autographLabel = (Label) load.getChildrenUnmodifiable().get(6);
			autographLabel.setText(d.getAutograph());
			Label deviceidLabel = (Label) load.getChildrenUnmodifiable().get(3);
			deviceidLabel.setText(d.getDeviceid());
			Label deviceipName = (Label) load.getChildrenUnmodifiable().get(2);
			deviceipName.setText(d.getDeviceip());

			Hyperlink _devicename_ = (Hyperlink) load.getChildren().get(7);
			_devicename_.setText(d.getDevicename());
			_devicename_.setOnMouseClicked(new EventHandler<Event>() {

				public void handle(Event event) {
					Label _status = (Label) load.getChildren().get(4);
					d.setIsOnline(_status.getText().equals(Device.ONLINE));
					if(ConnectConstants.LOGIN_DEVICE.getDeviceid().equals(d.getDeviceid())) {
						new DeviceInfoWind(d, DEVICETYPE.EDIT_INFO).show();
					}else {
						new DeviceInfoWind(d, DEVICETYPE.SEND_MESSAGE).show();
					}
				}
			});

			if (d.getIsOnline()) {
				Label status = (Label) load.getChildrenUnmodifiable().get(4);
				status.setText(Device.ONLINE);
				ImageView _headImg = (ImageView) load.getChildren().get(0);
				// 先设置默认头像
				ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", 50, 50);
				_headImg.setImage(new Image(in));
			} else {
				Label status = (Label) load.getChildrenUnmodifiable().get(4);
				status.setText(Device.OFFLINE);
				ImageView _headImg = (ImageView) load.getChildren().get(0);
				// 先设置默认头像
				ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", 50, 50);
				// 设置灰度图数据
				ByteArrayInputStream convertIn = ImageUtils.convertGray(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", in);
				_headImg.setImage(new Image(convertIn));
			}
			int count = d.getUnReadMessageCount();
			if (count > 0) {
				Label _unReadMessageCount = (Label) load.getChildrenUnmodifiable().get(5);
				if (count > 99) {
					_unReadMessageCount.setText("99+");
				} else {
					_unReadMessageCount.setText(count + "");
				}
				_unReadMessageCount.setVisible(true);
			}
			return load;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Pane getFriendItem(User u) {

		try {
			Pane load = FXMLLoader.load(friendItemUrl);
			// ImageView _headImg = (ImageView) load.getChildren().get(0);//
			// TODO
			// 设置头像
			// _headImg.setImage(new
			// Image("http://www.qqzhi.com/uploadpic/2014-11-12/190218795.jpg"));

			// BufferedImage grayImage = null;
			// ByteArrayOutputStream output = null;
			// ImageIO.write(grayImage, "", output);
			// byte[] byteArray = output.toByteArray();
			// ByteArrayInputStream byteArrayInputStream = new
			// ByteArrayInputStream(byteArray);
			// ImageView imageView = new ImageView(new
			// Image(byteArrayInputStream));
			// 以上具体灰度图转换 参照image.ImageDemo
			Label usernameLabel = (Label) load.getChildrenUnmodifiable().get(1);
			usernameLabel.setText(u.getNickname());
			Label autographLabel = (Label) load.getChildrenUnmodifiable().get(2);
			autographLabel.setText(u.getAutograph());
			Label useridLabel = (Label) load.getChildrenUnmodifiable().get(3);
			useridLabel.setText(u.getUserid());
			Label headFileName = (Label) load.getChildrenUnmodifiable().get(6);
			headFileName.setText(u.getHeadImg());

			Hyperlink _username_ = (Hyperlink) load.getChildren().get(7);
			_username_.setText(u.getNickname());
			_username_.setOnMouseClicked(new EventHandler<Event>() {

				public void handle(Event event) {
					Label _status = (Label) load.getChildren().get(4);
					u.setIsOnline(_status.getText().equals(User.ONLINE));
					new UserInfoWind(u, TYPE.SEND_MESSAGE).show();
				}
			});

			if (u.getIsOnline()) {
				Label status = (Label) load.getChildrenUnmodifiable().get(4);
				status.setText(User.ONLINE);
				ImageView _headImg = (ImageView) load.getChildren().get(0);
				
				
		
				
				// 先设置默认头像
				ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "default.png", 50, 50);
				_headImg.setImage(new Image(in));

				// 判断本地是否有缓存有头像
				File head = new File(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg());

				Platform.runLater(new Runnable() {
					public void run() {
						ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
							public void run() {
								if (!head.exists())
									ImageUtils.copyHead(u.getHead(), head);
								ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 50, 50);
								_headImg.setImage(new Image(in));
							}
						});
					}
				});
			} else {
				Label status = (Label) load.getChildrenUnmodifiable().get(4);
				status.setText(User.OFFLINE);
				ImageView _headImg = (ImageView) load.getChildren().get(0);
				// 先设置默认头像
				ByteArrayInputStream in = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + "default.png", 50, 50);
				// 设置灰度图数据
				ByteArrayInputStream convertIn = ImageUtils.convertGray(ConnectConstants.USER_IMAGE_PATH + "default.png", in);
				_headImg.setImage(new Image(convertIn));
				// 判断本地是否有缓存有头像
				File head = new File(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg());

				Platform.runLater(new Runnable() {
					public void run() {
						ThreadContainer.EXECUTOR_THREAD_POOL.execute(new Runnable() {
							public void run() {
								if (!head.exists()) {
									File file2 = new File(ConnectConstants.USER_IMAGE_PATH + "default.png");
									ImageUtils.copyHead(file2, head);
								}
								ByteArrayInputStream in1 = ImageUtils.scale(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), 50, 50);
								ByteArrayInputStream convertIn1 = ImageUtils.convertGray(ConnectConstants.USER_IMAGE_PATH + u.getHeadImg(), in1);
								_headImg.setImage(new Image(convertIn1));
							}
						});
					}
				});

			}
			int count = u.getUnReadMessageCount();
			if (count > 0) {
				Label _unReadMessageCount = (Label) load.getChildrenUnmodifiable().get(5);
				if (count > 99) {
					_unReadMessageCount.setText("99+");
				} else {
					_unReadMessageCount.setText(count + "");
				}
				_unReadMessageCount.setVisible(true);
			}
			return load;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Parent getCharWind() {
		try {
			return FXMLLoader.load(charWindUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Parent getDeviceWind() {
		try {
			return FXMLLoader.load(deviceWindUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Pane getCharGroupWind() {
		try {
			return FXMLLoader.load(chatGroupWindUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Pane getMain() {
		try {
			return FXMLLoader.load(mainUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Parent getLoginMain() {
		try {
			return FXMLLoader.load(loginMainUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Parent getLogin() {
		try {
			return FXMLLoader.load(loginUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Pane getChatItem() {
		try {
			return FXMLLoader.load(chatItemUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Pane getuserInfoWind() {
		try {
			return FXMLLoader.load(userInfoWindUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Pane getChatGroupItem() {
		try {
			return FXMLLoader.load(chatGroupItemUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Pane getMemberItem() {
		try {
			return FXMLLoader.load(memberUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Pane getdeviceInfoWind() {
		try {
			return FXMLLoader.load(deviceInfoWindUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
