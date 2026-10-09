EJEMPLO DE CÓDIGO DEL CONTROLADOR FXMLController para que emerja una segunda ventana


package hellofx;

/* ADD ALL NECCESSARY IMPORTS

import ...

*/

public class FXMLController {
// Declaración de objetos creados con SceneBuilder:

@FXML private AnchorPane anchorpane;
@FXML private PasswordField password_field;
@FXML private TextField user_field;
@FXML private Button send;

    public void initialize(URL url, ResourceBundle resources) {
        // Initialization code can go here.
        // The parameters url and resources can be omitted if they are not needed

/* Configuración de la propiedad Loginfx.isOpac para que la escena de la ventana principal se muestre opaca
mientras la ventaja emergente permanece visible */

Loginfx.isOpac.setValue(false); //valor inicial para la propiedad isOpac

Loginfx.isOpac.addListener(obs, oldvalue, newValue) {

if (newValue) { anchorpane.setOpacity(0.3) } else { anchorpane.setOpacity(0.4) }

}

send.setOnMouseClicked(e -> {

String user = user_field.getText();
String password = password_field.getText();

if (!Mysql.validate_user(user,password)) { // Definir qué pasa si la validación de usuario no tiene éxito:

Loginfx.isOpac.setValue(true); //haz que la ventaja principal se muestre opaca

// Resetea los campos introducidos por el usuario:
user_field.setText("");
password_field.setText(" ");

// Haz que la segunda ventana emerja:
Loginfx.pop_up_stage.show();

}
});

    }
}

CONTROLADOR DE LA VENTANA EMERGENTE

package hellofx;

/* ADD ALL NECCESSARY IMPORTS

import ...

*/

public class POPUPWIMNDOWController {
// Declaración de objetos creados con SceneBuilder:

@FXML private AnchorPane anchorpane;
@FXML private Label label;
@FXML private Button aceptar;

    public void initialize(URL url, ResourceBundle resources) {
        // Initialization code can go here.
        // The parameters url and resources can be omitted if they are not needed

/* Configuración de la propiedad MainClass.isOpac para que la escena de la ventana principal se muestre opaca
mientras la ventaja emergente permanece visible */

aceptar.setOnMouseClicked(e -> {

Loginfx.pop_up_state.close(); // cierra la ventaja emergente al hacer click en el botón "Aceptar";

Loginfx.isOpac.setValue(false); //haz que la ventaja principal se muestre nítida de nuevo


        });

    }
}