package cn.dunn.test;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WebViewTest extends Application {
	@FXML
	private WebView webView;

	@FXML
	private void executeScript() {
		// webView.getEngine().loadContent("-------");
		Document document = webView.getEngine().getDocument();
		System.out.println(document.getElementById("username").getAttribute("value"));
		Element headImg = document.getElementById("headImg");
		headImg.setAttribute("src", WebViewTest.class.getResource("/login/img/headimag.png") + "");
		Element createElement = document.createElement("div");
		createElement.setTextContent("我是新添加的");
		// System.out.println();
		// document.getElementsByTagName("html").item(0).setTextContent("--");
	}

	public static void main(String[] args) throws Exception {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("test/webview/WebView.fxml"));
		WebView webView = (WebView) pane.getChildren().get(0);
		webView.setOnMouseClicked(null);
		// webView.getEngine().load("http://www.baidu.com");

		// webView.getEngine().loadContent("<img alt='' src='/login/img/close_1.png' width='400px' height='300px' >");
		webView.getEngine()
				.loadContent(
						"<html><img id = 'headImg' alt='' src='"
								+ WebViewTest.class.getResource("/login/img/close_1.png")
								+ "'  >sdsdsd<div id='names'>...</div><input type='button' onclick='app.login()' value='登录'><br/> <span style='background-color: #f00; border-radius: 8px;'>sadasd</span> <input id='username'type='text' value='---'></html>");
		JSObject jsObject = (JSObject) webView.getEngine().executeScript("window");
		jsObject.setMember("app", new JavaApp());

		webView.getEngine().load("file:///D:/workroot/workflow/IMServer/WebContent/index.html");
		// WebEngine webEngine = webView.getEngine();
		// webEngine = webView.getEngine();
		// 禁用WebView的弹出菜单，但是我要的是修改啊！
		webView.setContextMenuEnabled(false);

		// file:///C:/Users/Administrator/Desktop/sadasd.html
		stage.setScene(new Scene(pane));
		stage.show();
		// webEngine.executeScript("document.getElementById('names').innerHTML('----------------------')");
	}

	public class JavaApp {
		int i = 0;

		public void login() {
			System.out.println("登录");
		}

		public void event(String str) {
			System.out.println(str);
		}

	}
}
