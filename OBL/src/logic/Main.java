package logic;

import GUI.OBLcontroller;
import GUI.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

	final public static int DEFAULT_PORT = 5555;
	public static String host;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.host = getParameters().getRaw().get(0);
		}
		catch(Exception e) {
			this.host = "localhost";
		}
		OBLcontroller obl=new OBLcontroller();
		obl.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
