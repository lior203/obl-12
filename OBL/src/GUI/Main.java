package GUI;
	
import GUI.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		OBLcontroller obl=new OBLcontroller();
		obl.start(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
