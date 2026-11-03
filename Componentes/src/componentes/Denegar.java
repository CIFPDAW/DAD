/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes;

import java.io.IOException;
import javafx.scene.control.*;
import javafx.fxml.*;

/**
 *
 * @author danoglez
 */
public class Denegar extends Button {
    
    @FXML private Button rechazar;

    public Denegar() {

        FXMLLoader ldr = new FXMLLoader(getClass().getResource("Denegar.fxml"));
        ldr.setRoot(this);
        ldr.setController(this);

        try {

            ldr.load();

        } catch (IOException ex) { throw new RuntimeException(ex); }
   }
    
}
