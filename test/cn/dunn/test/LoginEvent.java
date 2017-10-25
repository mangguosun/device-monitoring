package cn.dunn.test;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginEvent {
	@FXML
	private TextField username;
	@FXML
	private TextField password;

	@FXML
	private void login() {
		System.out.println(username.getText());
		System.out.println(password.getText());
	}
}
