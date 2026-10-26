package hellofxmysql;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML
    private AnchorPane ap1;

    @FXML
    private TextField tf1;

    @FXML
    private PasswordField pf1;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Label label1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button1.setOnAction(e -> acceder());
        button2.setOnAction(e -> abrirRegistro());
        button3.setOnAction(e -> abrirConfigBD());
    }

    private void acceder() {
        if (MySQL.bd == null) {
            mostrarDialogo(
                    "Sin base de datos",
                    "Primero tienes que configurar la base de datos.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        String usuario = tf1.getText().trim();
        String contrasena = pf1.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarDialogo(
                    "Campos vacíos",
                    "Introduce el usuario y la contraseña.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {
            if (MySQL.bd.comprobarLogin(usuario, contrasena)) {
                mostrarDialogo(
                        "Login correcto",
                        "Bienvenido, " + usuario + ".",
                        Alert.AlertType.INFORMATION
                );
            } else {
                mostrarDialogo(
                        "Error de acceso",
                        "El nombre de usuario y/o la contraseña "
                        + "son incorrectos.",
                        Alert.AlertType.ERROR
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

            mostrarDialogo(
                    "Error de conexión",
                    "No se ha podido conectar con la base de datos.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void abrirRegistro() {
        if (MySQL.bd == null) {
            mostrarDialogo(
                    "Sin base de datos",
                    "Primero tienes que configurar la base de datos.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Stage ventanaLogin = (Stage) button2.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
            Parent root = loader.load();

            Stage ventanaRegistro = new Stage();
            ventanaRegistro.setTitle("Nuevo registro");
            ventanaRegistro.setScene(new Scene(root, 600, 400));
            ventanaRegistro.setResizable(false);

            ventanaRegistro.initOwner(ventanaLogin);
            ventanaRegistro.initModality(Modality.WINDOW_MODAL);

            ventanaLogin.hide();
            ventanaRegistro.showAndWait();
            ventanaLogin.show();

        } catch (IOException ex) {
            ex.printStackTrace();

            mostrarDialogo(
                    "Error",
                    "No se ha podido abrir Registro.fxml.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void abrirConfigBD() {
        Stage ventanaLogin = (Stage) button3.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigBD.fxml"));
            Parent root = loader.load();

            Stage ventanaConfig = new Stage();
            ventanaConfig.setTitle("Configurar base de datos");
            ventanaConfig.setScene(new Scene(root, 600, 680));
            ventanaConfig.setResizable(false);

            ventanaConfig.initOwner(ventanaLogin);
            ventanaConfig.initModality(Modality.WINDOW_MODAL);

            ventanaLogin.hide();
            ventanaConfig.showAndWait();
            ventanaLogin.show();

        } catch (IOException ex) {
            ex.printStackTrace();

            mostrarDialogo(
                    "Error",
                    "No se ha podido abrir ConfigBD.fxml.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Stage ventana = (Stage) button1.getScene().getWindow();
        Parent contenido = ventana.getScene().getRoot();

        alerta.initOwner(ventana);
        alerta.initModality(Modality.WINDOW_MODAL);

        ventana.setOpacity(0.5);
        contenido.setOpacity(0.5);

        alerta.showAndWait();

        ventana.setOpacity(1.0);
        contenido.setOpacity(1.0);
    }
}
