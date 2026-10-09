EJEMPLO DE CÓDIGO BASE A AÑADIR A LA CLASE PRINCIPAL EN NUESTRO PROYECTO JAVAFX EN NETBEANS:

package loginfx; // add package name within our project

// import required libraries:

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* inherit all Application class methods:

start() method: needed to initialize JavaFX runtime environmet

launch() method: needed to lauch our app

*/

public class Loginfx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        primaryStage.setTitle("LOGINFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

public static void main(String[] args) {

launch(args);

} // close main

}