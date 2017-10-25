package cn.dunn.im.control;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light.Point;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import cn.dunn.im.common.EventUtil;
import cn.dunn.im.container.ComponentContainer;
import cn.dunn.im.container.WindContainer;
import cn.dunn.im.events.LoginEvent;

public class Login extends Application {
	private Point point = new Point();

	@Override
	public void init() throws Exception {

	}

	@Override
	public void start(final Stage stage) throws IOException {
		Parent root = WindContainer.getLogin();
		ComponentContainer._LOGIN = root;
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("QQ");
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
		LoginEvent.stage = stage;
		EventUtil.setCommonEvent(stage, root, point);
	}

	public static void main(String[] args) {
		launch();
	}

}
