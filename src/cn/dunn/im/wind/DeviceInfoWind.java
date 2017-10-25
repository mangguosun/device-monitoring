package cn.dunn.im.wind;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.io.FilenameUtils;

import cn.dunn.im.common.AbstractDesktop;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.CurrentWinContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.model.Device;
import cn.dunn.im.model.ModifyDeviceInfoReq;
import cn.dunn.im.util.ImageUtils;

public class DeviceInfoWind extends AbstractDesktop {

	public enum DEVICETYPE {
		APPLY_RELATION, EDIT_INFO, SEND_MESSAGE
	}

	private Stage stage = new Stage(StageStyle.TRANSPARENT);
	private Pane root = WindContainer.getdeviceInfoWind();
	private Scene scene = new Scene(getRoot());
	private Device device;
	private DEVICETYPE type;
	private TextField _devicenameText;
	private TextField _autographText;
	private Label _deviceid;
	private Label _registerTime;
	

	public DeviceInfoWind(Device device, DEVICETYPE type) {
		stage.setTitle(device.getDevicename());
		this.device = device;
		this.type = type;
	}

	@Override
	protected Stage getStage() {
		return stage;
	}

	@Override
	protected Parent getRoot() {
		return root;
	}

	@Override
	protected Scene getScene() {
		return scene;
	}

	@Override
	protected int minIndex() {
		return 4;
	}

	@Override
	protected int closeIndex() {
		return 5;
	}

	@Override
	protected void before() {

	}

	@Override
	protected void handle() {
		setCommonEvent();
		ImageView _headImage = (ImageView) root.getChildren().get(1);
		_headImage.setImage(new Image(ImageUtils.scale(ConnectConstants.DEVICE_IMAGE_PATH + "device.png", 60, 60)));

		Label _devicename = (Label) root.getChildren().get(2);
		_devicename.setText(device.getDevicename());

		Label _autograph = (Label) root.getChildren().get(3);
		_autograph.setText(device.getAutograph());

		Label _deviceip = (Label) root.getChildren().get(13);
		_deviceip.setText(device.getDeviceip());
		
		
		Label _devicenameauto = (Label) root.getChildren().get(14);
		_devicenameauto.setText(device.getDevicename()+"("+device.getAutograph()+")");
		
		
		
		_deviceid = (Label) root.getChildren().get(6);
		_deviceid.setText(device.getDeviceid());

		_devicenameText = (TextField) root.getChildren().get(10);
		_devicenameText.setText(device.getDevicename());
		_autographText = (TextField) root.getChildren().get(11);
		_autographText.setText(device.getAutograph());

		_registerTime = (Label) root.getChildren().get(12);
		_registerTime.setText(
				"更新时间 : " + new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(device.getRegisterTime())));

		// _headImagePath = (Label) root.getChildren().get(13);
	

		switch (type) {
		case APPLY_RELATION:
			root.getChildren().get(7).setVisible(true);
			_devicename.setVisible(false);
			_autograph.setVisible(false);
			_devicenameauto.setVisible(true);
			
			_devicenameText.setVisible(false);
			_autographText.setVisible(false);
			break;
		case SEND_MESSAGE:
			root.getChildren().get(8).setVisible(true);
			_devicename.setVisible(false);
			_autograph.setVisible(false);
			_devicenameauto.setVisible(true);
			
			_devicenameText.setVisible(false);
			_autographText.setVisible(false);
			break;
		case EDIT_INFO:
			root.getChildren().get(9).setVisible(true);
			_devicename.setVisible(false);
			_autograph.setVisible(false);
			_devicenameText.setVisible(true);
			_autographText.setVisible(true);

			_headImage.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					// FileChooser fileChooser = new FileChooser();
					// FileChooser.ExtensionFilter extFilter = new
					// FileChooser.ExtensionFilter("png格式", "*.png");
					// FileChooser.ExtensionFilter extFilter1 = new
					// FileChooser.ExtensionFilter("jpg格式", "*.jpg");
					// FileChooser.ExtensionFilter extFilter2 = new
					// FileChooser.ExtensionFilter("jpeg格式", "*.jpeg");
					// FileChooser.ExtensionFilter extFilter3 = new
					// FileChooser.ExtensionFilter("gif格式", "*.gif");
					// fileChooser.getExtensionFilters().add(extFilter);
					// fileChooser.getExtensionFilters().add(extFilter1);
					// fileChooser.getExtensionFilters().add(extFilter2);
					// fileChooser.getExtensionFilters().add(extFilter3);
					// // TODO 设置初始打开目录
					// if (ConnectConstants.INIT_OPEN_PATH != null) {
					// fileChooser.setInitialDirectory(ConnectConstants.INIT_OPEN_PATH);
					// }
					// File file = fileChooser.showOpenDialog(null);
					// if (file == null)
					// return;
					// ConnectConstants.INIT_OPEN_PATH = file.getParentFile();
					// _headImage.setImage(new Image(ImageUtils.scale(file.getAbsolutePath(), 60,
					// 60)));
					// _headImagePath.setText(file.getAbsolutePath());
					// _headImageMD5.setText(ImageUtils.getMd5ByFile(file));

				}
			});
			break;

		}
	}

	private void setCommonEvent() {
		Button _applyRelation = (Button) root.getChildren().get(7);
		_applyRelation.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO
				System.out.println("申请好友关系");
			}
		});
		Button _sendMessage = (Button) root.getChildren().get(8);
		_sendMessage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO 无法确认好友是否在线
				DeviceWind devicewind = new DeviceWind(device.getDeviceid(), device.getDevicename(),
						device.getAutograph(), device.getDeviceip(), device.ONLINE);
				devicewind.setLastLoadTime(new Date().getTime());
				CurrentWinContainer.openDeviceWind(device.getDeviceid(), devicewind);
				stage.close();
			}
		});
		Button _editInfo = (Button) root.getChildren().get(9);
		_editInfo.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ModifyDeviceInfoReq modifyDeviceInfoReq = new ModifyDeviceInfoReq();
				Device d = new Device();
				d.setDevicename(_devicenameText.getText());
				d.setDeviceid(_deviceid.getText());
				d.setAutograph(_autographText.getText());
				
				_registerTime.setText(
						"更新时间 : " + new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(d.getRegisterTime())));
				
				
				// if (!_headImagePath.getText().equals("headImagePath")) {
				// u.setHeadImg(ConnectConstants.LOGIN_USER.getUsername() + "_" +
				// _headImageMD5.getText() + "." +
				// FilenameUtils.getExtension(_headImagePath.getText()));
				// u.setHead(new File(_headImagePath.getText()));
				// }

				modifyDeviceInfoReq.setDevice(d);
				ConnectConstants.CHANNEL.writeAndFlush(modifyDeviceInfoReq);
			}
		});

	}
}
