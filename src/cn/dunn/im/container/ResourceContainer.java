package cn.dunn.im.container;

import javafx.scene.image.Image;

public class ResourceContainer {
	private static Image close = getImage("public/img/close.png");
	private static Image close_1 = getImage("public/img/close_1.png");
	private static Image min = getImage("public/img/min.png");
	private static Image min_1 = getImage("public/img/min_1.png");
	private static Image creator = getImage("main/img/creator.png");
	private static Image admin = getImage("main/img/admin.png");
	private static Image headImg = getImage("main/img/headImg.png");
	
	
	private static Image buttondetection = getImage("public/img/button_detection.png");
	private static Image buttondetection_1 = getImage("public/img/button_detection_1.png");
	private static Image buttondevice = getImage("public/img/button_device.png");
	private static Image buttondevice_1 = getImage("public/img/button_device_1.png");
	private static Image buttonlogin = getImage("public/img/button_login.png");	
	private static Image buttonlogin_1 = getImage("public/img/button_login_1.png");	
	private static Image buttoncollector = getImage("public/img/button_collector.png");
	private static Image buttoncollector_1 = getImage("public/img/button_collector_1.png");
	private static Image buttonup = getImage("public/img/up.png");
	private static Image buttondown = getImage("public/img/down.png");
	private static Image friendopen = getImage("public/img/friendbutton_open.png");
	private static Image friendclose = getImage("public/img/friendbutton_close.png");
	
	
	public static Image getClose() {
		return close;
	}

	public static Image getClose_1() {
		return close_1;
	}

	public static Image getMin() {
		return min;
	}

	public static Image getMin_1() {
		return min_1;
	}

	public static Image getCreator() {
		return creator;
	}

	public static Image getAdmin() {
		return admin;
	}

	public static Image getHeadImg() {
		return headImg;
	}

	public static Image getButtonDetection() {
		return buttondetection;
	}

	public static Image getButtonDetection_1() {
		return buttondetection_1;
	}
	public static Image getButtonDevice() {
		return buttondevice;
	}

	public static Image getButtonDevice_1() {
		return buttondevice_1;
	}
	public static Image getButtonCollector() {
		return buttoncollector;
	}

	public static Image getButtonCollector_1() {
		return buttoncollector_1;
	}
	public static Image getButtonLogin() {
		return buttonlogin;
	}

	public static Image getButtonLogin_1() {
		return buttonlogin_1;
	}	
	
	
	public static Image getButtonUp() {
		return buttonup;
	}

	public static Image getButtonDown() {
		return buttondown;
	}
	
	
	public static Image friendOpen() {
		return friendopen;
	}
	
	public static Image friendClose() {
		return friendclose;
	}
	
	private static Image getImage(String resourcePath) {
		return new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath));

	}

}
