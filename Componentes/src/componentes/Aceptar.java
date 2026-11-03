/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package componentes;


import java.io.IOException;
import javafx.scene.control.*;
import javafx.fxml.*;
/**
 *
 * @author danoglez
 */
public class Aceptar extends Button {

    @FXML private Button aceptar;

    public Aceptar() {

        FXMLLoader ldr = new FXMLLoader(getClass().getResource("Aceptar.fxml"));
        ldr.setRoot(this);
        ldr.setController(this);

        try {

            ldr.load();

        } catch (IOException ex) { throw new RuntimeException(ex); }
   }
    
}
