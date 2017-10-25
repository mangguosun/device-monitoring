package cn.dunn.im.events;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
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
import cn.dunn.im.handler.ChatGroupMemberRespHandler;
import cn.dunn.im.handler.HistoryMessageRespHandler;
import cn.dunn.im.handler.LoginHandler;
import cn.dunn.im.handler.MarshallingCodeCFactory;
import cn.dunn.im.handler.MessageReceiveHandler;
import cn.dunn.im.handler.ModifyUserInfoRespHandler;
import cn.dunn.im.handler.NotifyMessageHandler;
import cn.dunn.im.handler.ReceiveChatGroupHandler;
import cn.dunn.im.handler.ReceiveFriendHandler;
import cn.dunn.im.handler.UnReadMessageRespHandler;
import cn.dunn.im.util.HttpUtil;

public class LoginEvent {

	public static Stage stage;
	public static Point point = new Point();
	@FXML
	private Button login;
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
	private void login() throws IOException {
		final String username_t = username.getText();
		final String password_t = password.getText();
		ComponentContainer._MAIN_PARENT = WindContainer.getMain();
		new Thread() {
			public void run() {
				connect(username_t, password_t);
			};
		}.start();

		ObservableList<Node> list = ComponentContainer._LOGIN.getChildrenUnmodifiable();
		for (Node node : list) {
			node.setDisable(true);
		}
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(9).setDisable(false);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(10).setDisable(false);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(11).setDisable(false);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(12).setDisable(false);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(1).setVisible(false);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(11).setVisible(true);

	}

	@FXML
	private void close() {
		stage.close();
	}

	@FXML
	private void min() {
		stage.setIconified(true);
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

	private void connect(final String username, final String password) {

		new Thread() {
			public void run() {
				EventLoopGroup group = new NioEventLoopGroup();
				// 配置客户端NIO线程组
				try {
					Bootstrap b = new Bootstrap();
					b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// ch.pipeline().addLast(new
							// PacageDecoderHandler(1024 * 1024 * 1024, 0, 4));
							// ch.pipeline().addLast(new
							// PacageEncoderHandler());
							ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
							ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
							ch.pipeline().addLast(new LoginHandler(username, password));
							ch.pipeline().addLast(new ReceiveFriendHandler());
							ch.pipeline().addLast(new ReceiveChatGroupHandler());
							ch.pipeline().addLast(new NotifyMessageHandler());
							ch.pipeline().addLast(new MessageReceiveHandler());
							ch.pipeline().addLast(new UnReadMessageRespHandler());
							ch.pipeline().addLast(new ModifyUserInfoRespHandler());
							ch.pipeline().addLast(new HistoryMessageRespHandler());
							ch.pipeline().addLast(new ChatGroupMemberRespHandler());
						}
					});
					// 发起异步连接操作
					ChannelFuture future = b.connect(new InetSocketAddress("127.0.0.1", 80), new InetSocketAddress("127.0.0.1", HttpUtil.getUsableProt())).sync();
					ConnectConstants.CHANNEL = future.channel();
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) {
				} finally {
					System.out.println("关闭连接-------");
					group.shutdownGracefully();
				}

			};
		}.start();
	}

	@FXML
	private void callBackLogin() {

		ObservableList<Node> list = ComponentContainer._LOGIN.getChildrenUnmodifiable();
		for (Node node : list) {
			node.setDisable(false);
		}
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(1).setVisible(true);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(11).setVisible(false);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(13).setVisible(true);
		ComponentContainer._LOGIN.getChildrenUnmodifiable().get(14).setVisible(true);
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
