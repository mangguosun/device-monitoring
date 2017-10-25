package cn.dunn.im.constant;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;

import cn.dunn.im.io.ServerInfoVo;
import cn.dunn.im.model.Device;
import cn.dunn.im.model.User;
import io.netty.channel.Channel;

public class ConnectConstants {
	public static String LOCAL_IP = "127.0.0.1";
	public static int LOCAL_PORT = 80;
	public static Channel CHANNEL = null;
	public static String USER_IMAGE_PATH = "./resources/resource/userImg/";
	public static String DEVICE_IMAGE_PATH = "./resources/resource/deviceImg/";
	// public static String USER_INFO = "";
	public static User LOGIN_USER;
	public static Device LOGIN_DEVICE;
	public static ServerInfoVo SERVERINFOVO;
	public static ArrayBlockingQueue<Boolean> LOGIN_MARK = new ArrayBlockingQueue<Boolean>(1);
	public static File INIT_OPEN_PATH;
}
