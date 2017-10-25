package cn.dunn.im.container;

import java.util.HashMap;
import java.util.Map;

import cn.dunn.im.constant.ConnectConstants;
import cn.dunn.im.model.ChatGroupMemberReq;
import cn.dunn.im.wind.ChatGroupWind;
import cn.dunn.im.wind.ChatWind;
import cn.dunn.im.wind.DeviceWind;

public class CurrentWinContainer {
	private static Map<String, ChatWind> chatWinds = new HashMap<String, ChatWind>();
	private static Map<String, ChatGroupWind> chatGroupWinds = new HashMap<String, ChatGroupWind>();
	private static Map<String, DeviceWind> deviceWinds = new HashMap<String, DeviceWind>();
	
	
	public static void closeChatWind(String key) {
		chatWinds.remove(key);
	}

	public static void openChatWind(String key, ChatWind wind) {
		if (!chatWinds.containsKey(key)) {
			chatWinds.put(key, wind);
			wind.show();
		} else {
			// TODO 激活窗口
		}
	}

	public static ChatWind getChatWind(String key) {
		return chatWinds.get(key);
	}

	
	
	public static void closeGroupWind(String key) {
		chatGroupWinds.remove(key);
	}

	public static void openGroupWind(String key, ChatGroupWind groupWind) {
		if (!chatGroupWinds.containsKey(key)) {
			chatGroupWinds.put(key, groupWind);
			groupWind.show();
			// 发送加载群成员消息
			ChatGroupMemberReq groupMemberReq = new ChatGroupMemberReq();
			groupMemberReq.setWindKey(key);
			ConnectConstants.CHANNEL.writeAndFlush(groupMemberReq);
		}
	}

	public static ChatGroupWind getGroupWind(String key) {
		return chatGroupWinds.get(key);
	}
	
	
	public static void closeDeviceWind(String key) {
		deviceWinds.remove(key);
	}

	public static void openDeviceWind(String key, DeviceWind devicewind) {
		if (!deviceWinds.containsKey(key)) {
			deviceWinds.put(key, devicewind);
			devicewind.show();
		} else {
			// TODO 激活窗口
		}
	}

	public static DeviceWind getDeviceWind(String key) {
		return deviceWinds.get(key);
	}
	
}
