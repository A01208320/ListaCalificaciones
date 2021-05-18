package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    	@Override
	public void start(Stage stage) throws Exception {
            Parent root=FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Lista de Calificaciones");
            stage.setScene(scene);
            stage.show();
	}
	
	public static void main(String[] args) {
            launch(args);	
	}
}
