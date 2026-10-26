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
    private Button button3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button1.setOnAction(e -> acceder());
        button3.setOnAction(e -> abrirConfigBD());

        tf1.setOnAction(e -> acceder());
        pf1.setOnAction(e -> acceder());
    }

    private void acceder() {
        if (MySQL.bd == null) {
            MySQL.bd = MySQL.cargarDesdeArchivo();
            if (MySQL.bd == null) {
                MySQL.bd = MySQL.obtenerPredeterminado();
            }
        }

        String usuario = tf1.getText().trim();
        String contrasena = pf1.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarDialogo(
                    "Campos vacíos",
                    "Introduce el usuario (first_name) y la contraseña (last_name).",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {
            boolean loginCorrecto = MySQL.bd.comprobarLogin(usuario, contrasena);

            if (loginCorrecto) {
                // Apartado c:
                // Si un usuario realiza el login y resulta validado, se cerrará el diálogo de login
                // y aparecerá una nueva ventana de aspecto y diseño similar al que muestra la imagen.
                Stage ventanaLogin = (Stage) button1.getScene().getWindow();
                ventanaLogin.close();

                abrirReport();
            } else {
                // Apartado c:
                // Si el intento de login es fallido se mostrará un diálogo modal informando del problema.
                mostrarDialogo(
                        "Error de acceso",
                        "El usuario y/o la contraseña son incorrectos.\n"
                        + "El usuario no figura como empleado de la empresa en la base de datos.",
                        Alert.AlertType.ERROR
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarDialogo(
                    "Error de conexión",
                    "No se ha podido conectar con la base de datos MySQL 'employees':\n"
                    + ex.getMessage(),
                    Alert.AlertType.ERROR
            );
        }
    }

    private void abrirReport() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
            Parent root = loader.load();

            Stage stageReport = new Stage();
            stageReport.setTitle("Report");
            stageReport.setScene(new Scene(root, 650, 775));
            stageReport.setMinWidth(650);
            stageReport.setMinHeight(775);
            stageReport.setResizable(true);
            stageReport.show();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarDialogo(
                    "Error",
                    "No se ha podido abrir Report.fxml:\n" + ex.getMessage(),
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
            ventanaConfig.setTitle("Configurar acceso MySQL (Administrador)");
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
                    "No se ha podido abrir ConfigBD.fxml:\n" + ex.getMessage(),
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
}
