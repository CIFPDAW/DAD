package fxmysqlconfig;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfigBDController implements Initializable {

    @FXML
    private TextField tfRuta;

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private TextField tfTabla;

    @FXML
    private TextField tfColUsuario;

    @FXML
    private TextField tfColContrasena;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MySQL actual = MySQL.bd;
        if (actual == null) {
            actual = MySQL.cargarDesdeArchivo();
        }
        if (actual == null) {
            actual = MySQL.obtenerPorDefecto();
        }

        tfRuta.setText(actual.getRuta());
        tfUsuario.setText(actual.getUsuario());
        pfContrasena.setText(actual.getContrasena());
        tfTabla.setText(actual.getTabla());
        tfColUsuario.setText(actual.getColumnaUsuario());
        tfColContrasena.setText(actual.getColumnaContrasena());

        btnAceptar.setOnAction(e -> aceptar());
        btnCancelar.setOnAction(e -> cerrarVentana());
    }

    private void aceptar() {
        String ruta = tfRuta.getText().trim();
        String usuario = tfUsuario.getText().trim();
        String contrasena = pfContrasena.getText();
        String tabla = tfTabla.getText().trim();
        String colUsuario = tfColUsuario.getText().trim();
        String colContrasena = tfColContrasena.getText().trim();

        if (ruta.isEmpty() || usuario.isEmpty() || tabla.isEmpty()
                || colUsuario.isEmpty() || colContrasena.isEmpty()) {
            mostrarDialogo("Campos vacíos",
                    "Debes completar todos los campos obligatorios.",
                    Alert.AlertType.WARNING);
            return;
        }

        MySQL nuevaBD = new MySQL(ruta, usuario, contrasena, tabla, colUsuario, colContrasena);

        if (nuevaBD.probarConexion()) {
            try {
                nuevaBD.guardarEnArchivo();
                MySQL.bd = nuevaBD;
                mostrarDialogo("Conexión correcta",
                        "La conexión con MySQL se ha establecido correctamente y guardado en "
                        + MySQL.ARCHIVO_CONFIG + ".",
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarDialogo("Error al guardar",
                        "La conexión funciona, pero no se pudo guardar en "
                        + MySQL.ARCHIVO_CONFIG + ".",
                        Alert.AlertType.ERROR);
            }
        } else {
            // Permitir guardar por si el servidor MySQL no está activo en este momento pero las credenciales son correctas
            try {
                nuevaBD.guardarEnArchivo();
                MySQL.bd = nuevaBD;
                mostrarDialogo("Aviso de conexión",
                        "No se ha podido verificar la conexión con MySQL en '" + ruta + "'.\n"
                        + "Asegúrese de que el servidor MySQL está iniciado y la base de datos existe.\n"
                        + "La configuración se ha guardado en " + MySQL.ARCHIVO_CONFIG + ".",
                        Alert.AlertType.WARNING);
                cerrarVentana();
            } catch (IOException ex) {
                mostrarDialogo("Error de conexión",
                        "No se ha podido conectar ni guardar la configuración.",
                        Alert.AlertType.ERROR);
            }
        }
    }

    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        Stage ventana = (Stage) btnAceptar.getScene().getWindow();
        if (ventana != null && ventana.getScene() != null) {
            alerta.initOwner(ventana);
            alerta.initModality(Modality.WINDOW_MODAL);
        }
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) btnAceptar.getScene().getWindow();
        ventana.close();
    }
}
