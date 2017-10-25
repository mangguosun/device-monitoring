package cn.dunn.im.thread;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.control.Main;
import cn.dunn.im.io.HddInfoVo;
import cn.dunn.im.io.ServerInfo;
import cn.dunn.im.io.ServerInfoVo;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class MonitorThread extends Thread {// 继承Java.lang.Thread类定义线程
	private volatile boolean running = false;// 标记线程是否需要运行

	private int cpuIndex = 0;

	private static String Memoryusage(Long Total, Long Used) {

		float Mt = Float.parseFloat(Total.toString());
		float Mu = Float.parseFloat(Used.toString());
		float percent = Mu / Mt * 100;
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(percent) + '%';
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

	public void start() {// 覆盖了父类的start方法
		this.running = true;// 将running置为true，表示线程需要运行
		super.setDaemon(true);
		super.start();
		// System.out.println("before start(), Monitor.isAlive()=" + Monitor.isAlive());
		// System.out.println("just after start(), Monitor.isAlive()=" +
		// Monitor.isAlive());

	}

	public void run() {
		while (true) {
			if (!running) {

			} else {
				
				ServerInfoVo updateServerInfo = ConnectConstants.SERVERINFOVO =  new ServerInfo().start();
				
				Number CpuTotalUseds = stringtonumber(updateServerInfo.getCpuUsedFormat());
				Number MemoryTotalUseds = stringtonumber(Memoryusage(updateServerInfo.getMemTotal(), updateServerInfo.getMemUsed()));
				Number SwapTotalUseds = stringtonumber(Memoryusage(updateServerInfo.getSwapTotal(), updateServerInfo.getSwapUsed()));

			
				Main.cpu_series.getData().add(new XYChart.Data(cpuIndex, CpuTotalUseds));
				Main.mem_series.getData().add(new XYChart.Data(cpuIndex, MemoryTotalUseds));
				Main.swap_series.getData().add(new XYChart.Data(cpuIndex, SwapTotalUseds));

				// System.out.print(cpuIndex+","+CpuTotalUseds+","+MemoryTotalUseds+","+SwapTotalUseds+"\n");

				if (cpuIndex <= Main.xAxis.getUpperBound()) {
					cpuIndex++;
				} else {
					Main.xAxis.setLowerBound(Main.xAxis.getLowerBound() + 1);
					Main.xAxis.setUpperBound(Main.xAxis.getUpperBound() + 1);
					Main.cpu_series.getData().remove(0);
					Main.mem_series.getData().remove(0);
					Main.swap_series.getData().remove(0);
				}
				
				
				//HDD
				//Main.hddbarChart.getData().clear();
				
				//Main.SeriesHddUsage.getData().clear();
				//Main.SeriesHddAvailable.getData().clear();
//				List<HddInfoVo> HddList = updateServerInfo.getHddList();
//				
//				for (int j = 0; j < HddList.size(); j++) {
//					
//					HddInfoVo HddTerm = HddList.get(j);	
//					int TypeNumber = Integer.parseInt(String.valueOf(HddTerm.getTypeNumber()));
//					if (TypeNumber != 2) {
//						continue;
//					}
//					String DevName = HddTerm.getDevName();
//					String Total = HddTerm.getTotal();
//					Number Usage = stringtonumber(HddTerm.getUsage().toString());
//					Number Available = 100 - Usage.intValue();
//					String ckey = DevName + "(" + Total + ")";
//					Main.SeriesHddUsage.getData().add(new XYChart.Data(Usage, ckey));
//					Main.SeriesHddAvailable.getData().add(new XYChart.Data(Available, ckey));
//				}			
				
				//System.out.println(Main.hddbarChart.getData());
				//Main.hddbarChart.getData().addAll(SeriesHddUsage, SeriesHddAvailable);
			
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}

		}
	}

	public void setRunning(boolean running) {// 设置线程运行状态
		this.running = running;
	}

	public boolean getRunning() {// 设置线程运行状态
		return this.running;
	}

	public void startThread() {// 启动ThreadA线程
		System.out.println("启动监控");
		this.start();
	}

	public void stopThread() {
		System.out.println("暂停监控");
		this.setRunning(false);
	}

	public void restartThread() {
		System.out.println("重启监控");
		this.setRunning(true);
	}

}
