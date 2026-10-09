package dev.danoglez.helloworldidea;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class RegisterController {

    @FXML
    private VBox rootPane;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    protected void onRegisterClick() {
        String user = txtUser.getText();
        String pass = txtPassword.getText();
        String email = txtEmail.getText();

        if (user == null || user.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Error", "El nombre de usuario debe contener al menos 3 caracteres.");
            return;
        }

        if (pass == null || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "La contraseña no puede estar vacía.");
            return;
        }

        if (TryMysql.create_user(user, pass, email)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario creado correctamente.");
            onBackClick();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo crear el usuario. Es posible que el nombre de usuario ya exista.");
        }
    }

    @FXML
    protected void onBackClick() {
        try {
            HelloAplication.setRoot("Hello", "Login");
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
