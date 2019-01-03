package logic;

import GUI.OBLcontroller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

	final public static int DEFAULT_PORT = 5555;
	public static String host;
	
	public static void main(String args[]) throws Exception { 
		Application.launch(args);		
	} // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    try {
	    	Start.host = getParameters().getRaw().get(0);
	    }
	    catch(Exception e) {
	    	Start.host = "localhost";
	    }
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientGui.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Student Information");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} 	catch(Exception e) {
				e.printStackTrace();
			}
	}
}