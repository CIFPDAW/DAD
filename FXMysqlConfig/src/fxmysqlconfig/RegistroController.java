package fxmysqlconfig;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistroController implements Initializable {

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private PasswordField pfConfirmacion;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnVolver;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setOnAction(e -> registrar());
        btnVolver.setOnAction(e -> cerrarVentana());
    }

    private void registrar() {
        if (MySQL.bd == null) {
            MySQL.bd = MySQL.cargarDesdeArchivo();
            if (MySQL.bd == null) {
                mostrarDialogo(
                        "Sin base de datos",
                        "Primero tienes que configurar la base de datos.",
                        Alert.AlertType.WARNING
                );
                return;
            }
        }

        String usuario = tfUsuario.getText().trim();
        String contrasena = pfContrasena.getText();
        String confirmacion = pfConfirmacion.getText();

        if (usuario.isEmpty() || contrasena.isEmpty() || confirmacion.isEmpty()) {
            mostrarDialogo(
                    "Campos vacíos",
                    "Debes completar todos los campos.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (usuario.length() < 3) {
            mostrarDialogo(
                    "Usuario no válido",
                    "El nombre de usuario debe tener al menos 3 caracteres.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (!contrasena.equals(confirmacion)) {
            mostrarDialogo(
                    "Contraseñas diferentes",
                    "Las contraseñas introducidas no coinciden.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {
            if (MySQL.bd.existeUsuario(usuario)) {
                mostrarDialogo(
                        "Usuario existente",
                        "El nombre de usuario '" + usuario + "' ya se encuentra registrado.",
                        Alert.AlertType.WARNING
                );
                return;
            }

            if (MySQL.bd.registrarUsuario(usuario, contrasena)) {
                mostrarDialogo(
                        "Registro correcto",
                        "El usuario '" + usuario + "' se ha registrado correctamente en la base de datos.",
                        Alert.AlertType.INFORMATION
                );
                cerrarVentana();
            } else {
                mostrarDialogo(
                        "Error de inserción",
                        "No se ha podido insertar el registro.",
                        Alert.AlertType.ERROR
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarDialogo(
                    "Error de base de datos",
                    "No se ha podido registrar el usuario:\n" + ex.getMessage(),
                    Alert.AlertType.ERROR
            );
        }
    }

    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Stage ventana = (Stage) btnRegistrar.getScene().getWindow();
        if (ventana != null && ventana.getScene() != null) {
            alerta.initOwner(ventana);
            alerta.initModality(Modality.WINDOW_MODAL);

            Parent contenido = ventana.getScene().getRoot();
            ventana.setOpacity(0.5);
            contenido.setOpacity(0.5);

            alerta.showAndWait();

            ventana.setOpacity(1.0);
            contenido.setOpacity(1.0);
        } else {
            alerta.showAndWait();
        }
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) btnRegistrar.getScene().getWindow();
        ventana.close();
    }
}
