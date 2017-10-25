package cn.dunn.im.events;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UserInfoEvent {
	@FXML
	private Button but1;
	@FXML
	private Button but2;
	@FXML
	private Button but3;

	@FXML
	private ImageView headImage;

	@FXML
	private Label headImagePath;
	@FXML
	private Label headImageMD5;

	@FXML
	private void mainButEn() {
		but1.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
		but2.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
		but3.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
	}

	@FXML
	private void mainButEx() {
		but1.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
		but2.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
		but3.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
	}
}
