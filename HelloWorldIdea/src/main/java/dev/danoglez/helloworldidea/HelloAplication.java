package dev.danoglez.helloworldidea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloAplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloAplication.class.getResource("Hello.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setRoot(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloAplication.class.getResource(fxml + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}