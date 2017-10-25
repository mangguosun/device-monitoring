import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HBoxExample extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("VBox Example!");
		Button oneBtn = new Button("Button one");
		oneBtn.setPrefSize(100, 20);
		Button twoBtn = new Button("Button two");
		twoBtn.setPrefSize(100, 20);
		Button threeBtn = new Button("Button three");
		threeBtn.setPrefSize(100, 20);

		VBox hbox = new VBox();
//		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setPadding(new Insets(0.1));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		hbox.getChildren().addAll(oneBtn, twoBtn, threeBtn);
		Pane pane = new Pane();
		pane.getChildren().add(new Button("1"));
		pane.getChildren().add(new Button("2"));
		pane.getChildren().add(new Button("3"));
		hbox.getChildren().add(pane);
		primaryStage.setScene(new Scene(hbox, 500, 250));
		primaryStage.show();
	}
}