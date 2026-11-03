/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package probarcomponentes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author danoglez
 */
public class ProbarComponentes extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Instanciar el componente principal (tu VBox)
        Formulario formulario = new Formulario();

        // Crear la escena con el formulario
        Scene scene = new Scene(formulario, 400, 300);
        
        primaryStage.setTitle("Formulario de Contacto");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
