/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package probarcomponentes;


import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import java.io.IOException;
import componentes.Aceptar;
import componentes.Denegar;


/**
 * FXML Controller class
 *
 * @author danoglez
 */
public class Formulario extends VBox {
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField surname;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField phone;
    
    @FXML 
    private Aceptar btnAceptar;
    @FXML 
    private Denegar btnDenegar;

    public Formulario(){
        FXMLLoader ldr = new FXMLLoader(getClass().getResource("Formulario.fxml"));
        ldr.setRoot(this);
        ldr.setController(this);

        try {

            ldr.load();

        } catch (IOException ex) { throw new RuntimeException(ex); }
        
        
        if (btnDenegar != null) {
            btnDenegar.setOnAction(event -> {
                Platform.exit(); // Cierra la ventana y la aplicación
            });
        }
   }
}
