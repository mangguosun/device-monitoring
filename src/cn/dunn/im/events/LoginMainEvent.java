package cn.dunn.im.events;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.ResourceContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.control.Main;
import cn.dunn.im.handler.ChatGroupMemberRespHandler;
import cn.dunn.im.handler.HeartBeatClientHandler;
import cn.dunn.im.handler.HistoryMessageRespHandler;
import cn.dunn.im.handler.LoginMainHandler;
import cn.dunn.im.handler.MarshallingCodeCFactory;
import cn.dunn.im.handler.MessageReceiveHandler;
import cn.dunn.im.handler.ModifyUserInfoRespHandler;
import cn.dunn.im.handler.NotifyMessageHandler;
import cn.dunn.im.handler.ReceiveChatGroupHandler;
import cn.dunn.im.handler.ReceiveDeviceHandler;
import cn.dunn.im.handler.ReceiveFriendHandler;
import cn.dunn.im.handler.UnReadMessageRespHandler;
import cn.dunn.im.io.CpuInfoVo;
import cn.dunn.im.io.RuntimeSystem;
import cn.dunn.im.io.SigarInfoEntity;
import cn.dunn.im.io.SigarTools;
import cn.dunn.im.model.LoginRequestMessage;
import cn.dunn.im.thread.MonitorThread;
import cn.dunn.im.util.HttpUtil;

public class LoginMainEvent {

	public static Stage stage;
	public static Point point = new Point();
	@FXML
	private Button login;
	@FXML
	private Label titleversion;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private CheckBox rememberPsw;
	@FXML
	private CheckBox autoLogin;
	@FXML
	private ImageView close;
	@FXML
	private ImageView min;
	@FXML
	private Pane loginError;
	@FXML
	private ImageView buttonLogin;
	@FXML
	private Pane detectionBox;
	@FXML
	private Pane collectorBox;	
	@FXML
	private Pane loginpane;
	@FXML
	private Pane deviceBox;
	@FXML
	private ImageView buttoncpu;
	@FXML
	private Pane cpubox;
	@FXML
	private CheckBox Monitorbutton;
	@FXML
	private ListView cpulist;
	@FXML
	private ImageView loginclose;
	@FXML
	private ImageView friendbutton;	
	@FXML
	private Pane friendbox;	
	@FXML
	private TabPane friendtabpane;	
	
	static boolean isOpen = false;
	
	static boolean friendisOpen = false;

	
	@FXML
	private void monitorStatus() throws IOException {

		if (Main.threadMonitor == null) {
			Main.threadMonitor = new MonitorThread();
			Main.threadMonitor.startThread();// 启动ThreadA线程
		} else {

			boolean Monitor_running = Main.threadMonitor.getRunning();
			if (Monitor_running == true) {
				Main.threadMonitor.stopThread();
			} else {
				Main.threadMonitor.restartThread();
			}

		}
	}

	@FXML
	private void login() throws IOException {

		final String username_t = username.getText();
		final String password_t = password.getText();
		ComponentContainer._MAIN_PARENT = WindContainer.getMain();

		LoginRequestMessage loginRequestMessage = new LoginRequestMessage();
		loginRequestMessage.setUsername(username_t);
		loginRequestMessage.setPassword(password_t);
		ConnectConstants.CHANNEL.writeAndFlush(loginRequestMessage);
		
		
		username.setDisable(true);
		password.setDisable(true);
		login.setDisable(true);
		rememberPsw.setDisable(true);
		autoLogin.setDisable(true);
	}

	@FXML
	private void close() {
		stage.close();

		System.exit(1);
	}

	@FXML
	private void loginclose() {
		loginpane.setVisible(false);
	}

	@FXML
	private void min() {
		stage.setIconified(true);
	}

	@FXML
	private void logincloseEntered() {
		Image image = ResourceContainer.getClose_1();
		loginclose.setImage(image);
	}

	@FXML
	private void logincloseExited() {
		Image image = ResourceContainer.getClose();
		loginclose.setImage(image);
	}

	@FXML
	private void closeEntered() {
		Image image = ResourceContainer.getClose_1();
		close.setImage(image);
	}

	@FXML
	private void closeExited() {
		Image image = ResourceContainer.getClose();
		close.setImage(image);
	}

	@FXML
	private void minEntered() {
		Image image = ResourceContainer.getMin_1();
		min.setImage(image);
	}

	@FXML
	private void minExited() {
		Image image = ResourceContainer.getMin();
		min.setImage(image);
	}
	
	
	@FXML
	private void friendButton() {
		
		Image image ;
		if(friendisOpen ==true) {
			image = ResourceContainer.friendClose();
			friendisOpen = false;
			friendbox.setLayoutX(790);
			friendtabpane.setVisible(false);
			
		}else {
			image = ResourceContainer.friendOpen();
			friendisOpen = true;
			friendbox.setLayoutX(600);
			friendtabpane.setVisible(true);
		}
		friendbutton.setImage(image);
	}	

	@FXML
	private void buttonLogin() {

		boolean isvisible = loginpane.isVisible();
		if (isvisible) {
			loginpane.setVisible(false);
		} else {
			loginpane.setVisible(true);
		}
	}

	@FXML
	private void loginEntered() {
		Image image = ResourceContainer.getButtonLogin_1();
		buttonLogin.setImage(image);
	}

	@FXML
	private void loginExited() {
		Image image = ResourceContainer.getButtonLogin();
		buttonLogin.setImage(image);
	}

	@FXML
	private void buttonCpu() {
		if (isOpen == true) {
			isOpen = false;
			Image image_down = ResourceContainer.getButtonDown();
			cpubox.setVisible(false);
			buttoncpu.setImage(image_down);
		} else {
			isOpen = true;
			Image image_up = ResourceContainer.getButtonUp();
			cpubox.setVisible(true);
			buttoncpu.setImage(image_up);

			ObservableList<String> items = FXCollections.observableArrayList("厂商 总量 类别");
			List<CpuInfoVo> infos = ConnectConstants.SERVERINFOVO.getCpuList();
			
			for (int i = 0; i < infos.size(); i++) {// 不管是单块CPU还是多CPU都适用
				CpuInfoVo info = infos.get(i);
				items.add(info.getVendor()+" "+info.getCpuMhz()+" "+info.getModel());
			}
			cpulist.setItems(items);
			cpulist.setPrefSize(380, 100);

		}
	}

	@FXML
	private void callBackLogin() {
		username.setDisable(false);
		password.setDisable(false);
		login.setDisable(false);
		rememberPsw.setDisable(false);
		autoLogin.setDisable(false);
		loginError.setVisible(false);
	}

	@FXML
	private void login_en() {
		login.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
	}

	@FXML
	private void login_ex() {
		login.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
	}
}
