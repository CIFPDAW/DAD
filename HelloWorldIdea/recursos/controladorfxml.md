CONFIGURACIÓN DEL CONTROLADOR FXML

package javafxlogin // Indicar el nombre del paquete en nuestro proyecto jafafx

/* Importar las librerías necesarias para la integración de la interfaz descrita mediante el arhcivo .fxml y la asignación de eventos */

/* IMPORTANTE:

-En el editor visual SceneBuilder debemos asignar un fx-id a todos y cada uno de los elementos de interfaz gráfica que permita declararlos como objetos en el contorlador

-En nuestro archivo .fxml debemos comprobar que la etiqueta XML del contenedor principal está correctamente configurada, haciendo referencia a su respectivo controlador, siguiendo la sintaxis fx:controller="nombrepaquete.nombrecontrolador" (sin incluir la extensión .java). Ejemplo:

fx:controller="javafxlogin.FXMLController"

*/

import javafx.fxml.FXML;

import javafx.scene.*;

importa javafx.scene.text.*;


public class FXMLController {

/* Añadir la keyword @FXML para declarar todos y cada uno de los objetos
agregados a la interfaz en SceneBuilder

Tendremos que añadir también el keyword @FXML para implementar cada evento
*/

    @FXML private Text actiontarget;
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }

}