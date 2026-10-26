package hellofxmysql;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        if (actual != null) {
            tfRuta.setText(actual.getRuta());
            tfUsuario.setText(actual.getUsuario());
            pfContrasena.setText(actual.getContrasena());
            tfTabla.setText(actual.getTabla());
            tfColUsuario.setText(actual.getColumnaUsuario());
            tfColContrasena.setText(actual.getColumnaContrasena());
        }

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
                    "Debes completar todos los campos.",
                    Alert.AlertType.WARNING);
            return;
        }

        MySQL nuevaBD = new MySQL(ruta, usuario, contrasena, tabla, colUsuario, colContrasena);

        if (nuevaBD.probarConexion()) {
            try {
                nuevaBD.guardarEnArchivo();
                MySQL.bd = nuevaBD;
                mostrarDialogo("Conexión correcta",
                        "La base de datos se ha configurado y guardado en "
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
            mostrarDialogo("Error de conexión",
                    "No se ha podido conectar. Revisa los datos, la tabla y las columnas.",
                    Alert.AlertType.ERROR);
        }
    }

    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) btnAceptar.getScene().getWindow();
        ventana.close();
    }
}
