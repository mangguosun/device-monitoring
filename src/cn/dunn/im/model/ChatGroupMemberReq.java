package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @Title: ChatGroupMemberReq.java
 * @Package cn.dunn.im.model
 * @Description: 请求获取群组成员
 * @author TangTianXiong
 * @date 2015年6月9日 上午9:35:47
 */
@XmlRootElement
public class ChatGroupMemberReq implements Serializable{

	private static final long serialVersionUID = 1L;
	private String windKey;

	@XmlElement
	public String getWindKey() {
		return windKey;
	}

	public void setWindKey(String windKey) {
		this.windKey = windKey;
	}

}
