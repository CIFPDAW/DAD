package dev.danoglez.helloworldidea;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class HelloController {

    @FXML
    private VBox rootPane;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onLoginClick() {
        String user = txtUser.getText();
        String pass = txtPassword.getText();

        if (TryMysql.validate(user, pass)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Login correcto.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Usuario y/o contraseña incorrectos.");
        }
    }

    @FXML
    protected void onRegisterClick() {
        try {
            HelloAplication.setRoot("Register", "Registro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        // Hacer el fondo semi-transparente
        rootPane.setOpacity(0.5);
        alert.showAndWait();
        rootPane.setOpacity(1.0);
    }
}
