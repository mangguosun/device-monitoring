package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 * @Title: HistoryMessageReq.java
 * @Package cn.dunn.im.model
 * @Description: 请求历史消息报文
 * @author TangTianXiong
 * @date 2015年6月8日 上午10:41:07
 */
@XmlRootElement
public class HistoryMessageReq implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 窗口ID
	 */
	private String windKey;
	/**
	 * 用户ID
	 */
	private String userid;
	/**
	 * 最后一条消息的记录
	 */
	private Long lastLoadTime;
	/**
	 * 参照message类型
	 */
	private String type;

	@XmlElement
	public String getWindKey() {
		return windKey;
	}

	public void setWindKey(String windKey) {
		this.windKey = windKey;
	}

	@XmlElement
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@XmlElement
	public Long getLastLoadTime() {
		return lastLoadTime;
	}

	public void setLastLoadTime(Long lastLoadTime) {
		this.lastLoadTime = lastLoadTime;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
