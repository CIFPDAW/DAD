package fxmysqlconfig;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMysqlConfig extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MySQL.bd = MySQL.cargarDesdeArchivo();
        if (MySQL.bd == null) {
            MySQL.bd = MySQL.obtenerPorDefecto();
        }

        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        stage.setTitle("FXMysqlConfig - Login y Registro");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
