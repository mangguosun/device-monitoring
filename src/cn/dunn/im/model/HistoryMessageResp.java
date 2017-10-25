package cn.dunn.im.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 * @Title: HistoryMessageReq.java
 * @Package cn.dunn.im.model
 * @Description: 历史消息返回报文
 * @author TangTianXiong
 * @date 2015年6月8日 上午10:41:07
 */
@XmlRootElement
public class HistoryMessageResp implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 窗口ID
	 */
	private String windKey;

	/**
	 * 类型 参照Message
	 */
	private String type;
	/**
	 * 历史消息
	 */
	private List<Message> historyMessage = new ArrayList<Message>();

	@XmlElement
	public String getWindKey() {
		return windKey;
	}

	public void setWindKey(String windKey) {
		this.windKey = windKey;
	}

	@XmlElement
	public List<Message> getHistoryMessage() {
		return historyMessage;
	}

	public void setHistoryMessage(List<Message> historyMessage) {
		this.historyMessage = historyMessage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
