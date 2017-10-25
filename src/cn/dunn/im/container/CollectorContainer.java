package cn.dunn.im.container;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @Title: CollectorContainer.java
 * @Package cn.dunn.im.container
 * @Description: 数据采集
 * @author TangTianXiong
 * @date 2015年6月9日 下午4:47:25
 */
public class CollectorContainer {

	private Document doc = null;

	public void init(String url) {
		try {
			doc = Jsoup.connect(url).get();
			String title = doc.title();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<HashMap> getlists(String Tag, String LinkTag) {

		ArrayList<HashMap> CollectorList = new ArrayList();

		Element content = doc.select(Tag).first();

		Elements links = content.getElementsByTag(LinkTag);

		for (Element link : links) {

			HashMap hm = new HashMap();

			String linkHref = link.attr("abs:href");
			String linkText = link.text();
			// System.out.println(linkText+":"+linkHref);
			hm.put("id", UUID.randomUUID().toString());
			hm.put("title", linkText);
			hm.put("hrefurl", linkHref);
			CollectorList.add(hm);
		}

		return CollectorList;

		// System.out.println("***************");
		// 另外一种是post方式
		/*
		 * @SuppressWarnings("unused") Document doc_Post = Jsoup.connect(url)
		 * .data("query","Java") .userAgent("I am jsoup") .cookie("auth","token")
		 * .timeout(10000) .post(); Elements links_Post = doc.select("a[href]");
		 * for(Element link:links_Post){ String linkHref = link.attr("abs:href"); String
		 * linkText = link.text(); //System.out.println(linkText+":"+linkHref);
		 * 
		 * //map.put(linkText, linkHref); }
		 */

	}

	public HashMap getContent(HashMap Tag) {

		HashMap hm = new HashMap();
		Iterator iter = Tag.entrySet().iterator();
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			String[] s = val.split("\\|");
			if (s.length == 1) {
				hm.put(key, doc.select(val).text());
			} else {
				if (s[1].equals("last")) {
					hm.put(key, doc.select(s[0]).last().text());
				} else {
					hm.put(key, doc.select(s[0]).first().text());
				}
			}
		}
		return hm;
	}

}
