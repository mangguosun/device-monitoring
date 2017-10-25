package cn.dunn.im.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 我的群组列表
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
public class MyChatGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ChatGroup> chatGroup;

	public MyChatGroup() {
	}

	public MyChatGroup(List<ChatGroup> chatGroup) {
		this.chatGroup = chatGroup;
	}

	@XmlElement
	public List<ChatGroup> getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(List<ChatGroup> chatGroup) {
		this.chatGroup = chatGroup;
	}

}
