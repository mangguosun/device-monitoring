package cn.dunn.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("test/webview/loginTest.fxml"));
		stage.setScene(new Scene(pane));
		stage.show();
	}
}
