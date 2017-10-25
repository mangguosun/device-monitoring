package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @Title: SearchReq.java
 * @Package cn.dunn.im.model
 * @Description: 搜索请求
 * @author TangTianXiong
 * @date 2015年6月10日 下午3:26:45
 */
@XmlRootElement
public class SearchReq implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 搜索类型
	 */
	private String type;
	/**
	 * 窗口id
	 */
	private String windKey;
	/**
	 * 搜索关键字
	 */
	private String key;

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement
	public String getWindKey() {
		return windKey;
	}

	public void setWindKey(String windKey) {
		this.windKey = windKey;
	}

	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
