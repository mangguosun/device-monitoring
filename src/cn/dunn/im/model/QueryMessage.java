package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueryMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String QUREY_MYFRIENDS = "qurey_myfriends";
	private String from;
	private String type;

	@XmlElement
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "QueryMessage [from=" + from + ", type=" + type + "]";
	}

}
