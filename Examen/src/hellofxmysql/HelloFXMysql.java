package hellofxmysql;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloFXMysql extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MySQL.bd = MySQL.cargarDesdeArchivo();

        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        stage.setTitle("LOGINFX");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
