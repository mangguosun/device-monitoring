package cn.dunn.im.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ConvertUtil {

	public static String obj2Xml(Object obj) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(obj.getClass());
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter writer = new StringWriter();
			mar.marshal(obj, writer);
			return writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T xml2Object(String xmlContent, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			InputStream inputStream;
			try {
				inputStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
				Unmarshaller um = context.createUnmarshaller();
				return (T) um.unmarshal(inputStream);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object xml2Object(String className, String xmlContent) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Class.forName(className));
		InputStream in = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(in);
	}

}
