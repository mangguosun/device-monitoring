package cn.dunn.im.wind;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import cn.dunn.im.common.AbstractDesktop;
import cn.dunn.im.container.WindContainer;

public class LoginWind extends AbstractDesktop {

	private Stage stage = new Stage(StageStyle.TRANSPARENT);;
	private Parent root = WindContainer.getLogin();
	private Scene scene = new Scene(getRoot());

	@Override
	protected Scene getScene() {
		return scene;
	}

	@Override
	protected Stage getStage() {
		return stage;
	}

	@Override
	protected Parent getRoot() {
		return root;
	}

	@Override
	protected int minIndex() {
		return 0;
	}

	@Override
	protected int closeIndex() {
		return 0;
	}

	@Override
	protected void handle() {
		stage.setTitle("登录");
	}

	@Override
	protected void before() {

	}
}
