package cn.dunn.im.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javafx.scene.control.Tooltip;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import cn.dunn.im.common.EventUtil;
import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.container.CollectorContainer;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.ResourceContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.events.LoginMainEvent;
import cn.dunn.im.handler.ChatGroupMemberRespHandler;
import cn.dunn.im.handler.HistoryMessageRespHandler;
import cn.dunn.im.handler.LoginMainHandler;
import cn.dunn.im.handler.MarshallingCodeCFactory;
import cn.dunn.im.handler.MessageReceiveHandler;
import cn.dunn.im.handler.ModifyDeviceInfoRespHandler;
import cn.dunn.im.handler.ModifyUserInfoRespHandler;
import cn.dunn.im.handler.NotifyMessageDeviceHandler;
import cn.dunn.im.handler.NotifyMessageHandler;
import cn.dunn.im.handler.ReceiveChatGroupHandler;
import cn.dunn.im.handler.ReceiveDeviceGroupHandler;
import cn.dunn.im.handler.ReceiveDeviceHandler;
import cn.dunn.im.handler.ReceiveFriendHandler;
import cn.dunn.im.handler.UnReadMessageRespHandler;
import cn.dunn.im.io.CpuInfoVo;
import cn.dunn.im.io.HddInfoVo;
import cn.dunn.im.io.ServerInfo;
import cn.dunn.im.io.ServerInfoVo;
import cn.dunn.im.model.DeviceRequestMessage;
import cn.dunn.im.thread.MonitorThread;
import cn.dunn.im.util.HttpUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Main extends Application {

	private Point point = new Point();

	static Parent root = WindContainer.getLoginMain();

	public static XYChart.Series cpu_series = new XYChart.Series();
	public static XYChart.Series mem_series = new XYChart.Series();
	public static XYChart.Series swap_series = new XYChart.Series();

	public static XYChart.Series SeriesHddUsage = new XYChart.Series();
	public static XYChart.Series SeriesHddAvailable = new XYChart.Series();

	public static MonitorThread threadMonitor = null;// 创建实例
	public static StackedBarChart<Number, String> hddbarChart = null;
	public static NumberAxis xAxis;

	public static String m_id[] = new String[] { "Detection", "Device", "Collector" };
	public static ImageView[] menuButtons = new ImageView[m_id.length];

	@Override
	public void init() throws Exception {

	}

	public void NavMenuClick(ImageView chickButton) {
		String chickMenuId = chickButton.getId();

		for (int i = 0; i < menuButtons.length; i++) {
			ImageView menuButton = menuButtons[i];

			String menuButtonId = menuButton.getId();
			String pan_id = menuButtonId.toLowerCase() + "Box";
			if (chickMenuId.equals(menuButtonId)) {
				menuButton.setImage(ResourceC(menuButtonId, true));
				root.lookup("#" + pan_id).setVisible(true);
			} else {
				menuButton.setImage(ResourceC(menuButtonId, false));
				root.lookup("#" + pan_id).setVisible(false);
			}
		}
		switch (chickMenuId) {
		case "Detection":

			System.out.println("Detection");

			break;
		case "Device":
			break;
		case "Collector":
			//
			// CollectorContainer collector = new CollectorContainer();
			// collector.init("http://sh.bidcenter.com.cn/zblist-2-1.html");
			// ArrayList<HashMap> listspage = collector.getlists("TABLE", "a");
			//
			// for (HashMap datalist : listspage) {
			//
			// String urls = datalist.get("hrefurl").toString();
			//
			// collector.init(urls);
			//
			// HashMap sTag = new HashMap();
			//
			// sTag.put("title", ".nro_title >h3");
			// sTag.put("acer", ".nro_title >p >span|last");
			//
			// HashMap contentpage = collector.getContent(sTag);
			// System.out.print(contentpage);
			//
			// break;
			// }

			break;
		default:

			break;

		}

	}

	public static Image ResourceC(String Zid, boolean is_hover) {
		Image result = null;
		if (!is_hover) {
			Zid = "getButton" + Zid;
		} else {
			Zid = "getButton" + Zid + "_1";
		}
		try {
			Class<ResourceContainer> classType = ResourceContainer.class;
			Method method = classType.getMethod(Zid, null);
			result = (Image) method.invoke(method, null);

		} catch (Exception e) {
			System.out.println("open failure");
		}
		return result;
	}

	@Override
	public void start(final Stage stage) throws Exception {

		Label titleversion = (Label) root.lookup("#titleversion");
		titleversion.setText("齐信双活灾备3.0");
		ComponentContainer._LOGINMAIN = root;
		Scene scene = new Scene(root, 800, 600);
		
		scene.getStylesheets().add(Thread.currentThread().getContextClassLoader()
				.getResource("loginmain/xml/MainStyle.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("首页");
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
		LoginMainEvent.stage = stage;
		EventUtil.setCommonEvent(stage, root, point);
		
		ConnectConstants.SERVERINFOVO =  new ServerInfo().start();
		
		connect();
		
		
		
		
		
		
		
		
		Thread.currentThread().sleep(500);
		
		callSystemn();
		Pane navnenu = (Pane) root.lookup("#navMenu");
		int j = 10;
		for (int i = 0; i < m_id.length; i++) {
			final int k = i;
			ImageView menuButton = new ImageView();
			menuButton.setId(m_id[i]);
			menuButton.setFitWidth(80);
			menuButton.setFitHeight(80);
			menuButton.setImage(ResourceC(m_id[i], false));
			menuButton.setLayoutX(j);
			menuButton.setPickOnBounds(true);
			menuButton.setPreserveRatio(true);
			Tooltip.install(menuButton, new Tooltip(m_id[i])); // Doesn't work
			j = j + 10 + 80;
			menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					NavMenuClick(menuButton);
				}
			});

			menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					menuButton.setImage(ResourceC(m_id[k], true));
				}
			});

			menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					menuButton.setImage(ResourceC(m_id[k], false));
				}
			});

			menuButtons[i] = menuButton;

		}
		navnenu.getChildren().addAll(menuButtons);

		//
		threadMonitor = new MonitorThread();
		threadMonitor.startThread();// 启动ThreadA线程
		//
		CheckBox Monitorbutton = (CheckBox) root.lookup("#Monitorbutton");
		if (Main.threadMonitor == null || Main.threadMonitor.getRunning() == false) {
			Monitorbutton.setSelected(false);
		} else {
			Monitorbutton.setSelected(true);
		}

		// Runtime rt = Runtime.getRuntime();
		// Process p = null;
		// String fileLac = "";
		// try {
		// fileLac = "c:\\Program Files\\Notepad++\\notepad++.exe";//要调用的程序路径
		// p = rt.exec(fileLac);
		// } catch (Exception e) {
		// System.out.println("open failure");
		// }

	}

	private void connect() {

		new Thread() {
			public void run() {
				EventLoopGroup group = new NioEventLoopGroup();
				ChannelFuture future = null;
				// 配置客户端NIO线程组
				try {
					Bootstrap bootstrap = new Bootstrap();

					bootstrap.group(group);
					bootstrap.channel(NioSocketChannel.class);
					bootstrap.option(ChannelOption.TCP_NODELAY, true);
					bootstrap.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// ch.pipeline().addLast(new
							// PacageDecoderHandler(1024 * 1024 * 1024, 0, 4));
							// ch.pipeline().addLast(new
							// PacageEncoderHandler());
							// ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
							// ch.pipeline().addLast(new HeartBeatClientHandler());
							ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
							ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
							ch.pipeline().addLast(new ReceiveDeviceHandler());
							ch.pipeline().addLast(new ReceiveDeviceGroupHandler());
							ch.pipeline().addLast(new ModifyDeviceInfoRespHandler());
							ch.pipeline().addLast(new NotifyMessageDeviceHandler());
							
							
							
							ch.pipeline().addLast(new LoginMainHandler());
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
					future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 80),
							new InetSocketAddress("127.0.0.1", HttpUtil.getUsableProt())).sync();

					if (future.isSuccess()) {
						ConnectConstants.CHANNEL = future.channel();
						System.out.println("connect server  成功---------");
						future.channel().closeFuture().sync();
					} else {
						System.out.println("连接失败！");
						System.out.println("准备重连！");
						connect();
					}
				} catch (InterruptedException e) {

				} finally {

					// if(null != future){
					// if(null != future.channel() && future.channel().isOpen()){
					// future.channel().close();
					// }
					// }
					// System.out.println("准备重连！");
					// connect(username, password);
					// System.out.println("关闭连接-------");
					// group.shutdownGracefully();
				}

			};
		}.start();
	}

	public static void main(String[] args) {
		launch();
	}

	public static void callSystemn() throws Exception {

		Label CpuCountLabel = (Label) root.lookup("#CpuCount");
		Label MemoryCountLabel = (Label) root.lookup("#MemoryCount");
		Label SystemIpLabel = (Label) root.lookup("#SystemIp");
		Label OsDescribeLabel = (Label) root.lookup("#OsDescribe");
		Label OsMacAddressLabel = (Label) root.lookup("#OsMacAddress");

		AnchorPane SystemMonitorChartBOX = (AnchorPane) root.lookup("#SystemMonitorChartBOX");
		AnchorPane HddMonitorChartBOX = (AnchorPane) root.lookup("#HddMonitorChartBOX");

		
		
		ServerInfoVo serviceInfo = ConnectConstants.SERVERINFOVO;
		
	
		OsDescribeLabel.setText(serviceInfo.getOsName() + " " +serviceInfo.getOsVersion());
		OsMacAddressLabel.setText(serviceInfo.getMAC());
		SystemIpLabel.setText(serviceInfo.getServerIP());
		CpuCountLabel.setText(serviceInfo.getCpuNum() + "个");
		
		MemoryCountLabel.setText("物理内存 " + serviceInfo.getMemTotalFormat() + " 虚拟内存 " + serviceInfo.getSwapTotalFormat());

		xAxis = new NumberAxis(0, 60, 5);
		final NumberAxis yAxis = new NumberAxis(0, 100, 20);
		// xAxis.setLabel("TIME");

		// creating the chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setId("lineStockDemo");
		lineChart.setCreateSymbols(false);
		lineChart.setAnimated(false);

		// lineChart.setLegendVisible(false);
		lineChart.setPrefSize(800, 250);
		// lineChart.setTitle("实时监控");
		// xAxis.setLabel("Time");
		xAxis.setForceZeroInRange(false);
		xAxis.setTickLabelsVisible(false);
		xAxis.setMinorTickVisible(true);
		yAxis.setLabel("使用率(%)");
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, null, ""));

		SystemMonitorChartBOX.getChildren().add(lineChart);

		cpu_series.setName("处理器");
		mem_series.setName("物理内存");
		swap_series.setName("虚拟内存");
		// for(int i=0; i<=60; i++) {
		// cpu_series.getData().add(new XYChart.Data(i, 0));
		// mem_series.getData().add(new XYChart.Data(i, 0));
		// swap_series.getData().add(new XYChart.Data(i, 0));
		// }
		lineChart.getData().addAll(cpu_series, mem_series, swap_series);

		final NumberAxis xAxisHdd = new NumberAxis(0, 100, 10);
		final CategoryAxis yAxisHdd = new CategoryAxis();
		hddbarChart = new StackedBarChart<Number, String>(xAxisHdd, yAxisHdd);
		hddbarChart.setPrefSize(800, 250);
		// hddbarChart.setTitle("磁盘监控");

		// hddbarChart.setHorizontalGridLinesVisible(true);

		// xAxisHdd.setLabel("磁盘");
		// xAxisHdd.setLabel("使用率(%)");
		xAxisHdd.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxisHdd, null, "%"));
		HddMonitorChartBOX.getChildren().add(hddbarChart);

		SeriesHddUsage.setName("已用");
		SeriesHddAvailable.setName("可用");
		
		List<HddInfoVo> HddList = serviceInfo.getHddList();
		
		for (int j = 0; j < HddList.size(); j++) {
			
			HddInfoVo HddTerm = HddList.get(j);	
			int TypeNumber = Integer.parseInt(String.valueOf(HddTerm.getTypeNumber()));
			if (TypeNumber != 2) {
				continue;
			}
			String DevName = HddTerm.getDevName();
			String Total = HddTerm.getTotal();
			Number Usage = stringtonumber(HddTerm.getUsage().toString());
			Number Available = 100 - Usage.intValue();
			String ckey = DevName + "(" + Total + ")";
			SeriesHddUsage.getData().add(new XYChart.Data(Usage, ckey));
			SeriesHddAvailable.getData().add(new XYChart.Data(Available, ckey));
		}

		hddbarChart.getData().addAll(SeriesHddUsage, SeriesHddAvailable);

	}

	private static Number stringtonumber(String velue) {
		velue.substring(0, velue.length() - 1);
		Number numbers = 0;
		try {
			numbers = NumberFormat.getInstance().parse(velue);
		} catch (ParseException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		return numbers;
	}

}
