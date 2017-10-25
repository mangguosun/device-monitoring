package cn.dunn.im.container;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class ComponentContainer {
	/**
	 * 主界面
	 */
	public static Pane _MAIN_PARENT;
	/**
	 * 我的分组界面
	 */
	public static Accordion _FRIEND_GROUP;
	/**
	 * 我的设备分组界面
	 */
	public static TabPane _Device_GROUP;
	/**
	 * 登录界面
	 */
	public static Parent _LOGIN;
	/**
	 * 登录登录界面
	 */
	public static Parent _LOGINMAIN;
	
	/**
	 * 群组列表
	 */
	public static ListView<Node> _CHAT_GROUP;
}
