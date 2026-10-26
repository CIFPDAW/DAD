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
            actual = MySQL.obtenerPredeterminado();
        }

        tfUsuario.setText(actual.getUsuario());
        pfContrasena.setText(actual.getContrasena());

        // Conforme al apartado b del examen:
        // Los parámetros (nombre de la base de datos, tabla y campos a comprobar)
        // quedan predeterminados de acuerdo al enunciado y son de sólo lectura.
        tfRuta.setText(MySQL.RUTA_PREDETERMINADA);
        tfTabla.setText(MySQL.TABLA_PREDETERMINADA);
        tfColUsuario.setText(MySQL.COLUMNA_USUARIO_PREDETERMINADA);
        tfColContrasena.setText(MySQL.COLUMNA_CONTRASENA_PREDETERMINADA);

        tfRuta.setEditable(false);
        tfTabla.setEditable(false);
        tfColUsuario.setEditable(false);
        tfColContrasena.setEditable(false);

        btnAceptar.setOnAction(e -> aceptar());
        btnCancelar.setOnAction(e -> cerrarVentana());
    }

    private void aceptar() {
        String usuario = tfUsuario.getText().trim();
        String contrasena = pfContrasena.getText();

        if (usuario.isEmpty()) {
            mostrarDialogo("Campo vacío",
                    "Debes introducir el nombre del usuario administrador de MySQL.",
                    Alert.AlertType.WARNING);
            return;
        }

        // Parámetros predeterminados según el enunciado:
        String ruta = MySQL.RUTA_PREDETERMINADA;
        String tabla = MySQL.TABLA_PREDETERMINADA;
        String colUsuario = MySQL.COLUMNA_USUARIO_PREDETERMINADA;
        String colContrasena = MySQL.COLUMNA_CONTRASENA_PREDETERMINADA;

        MySQL nuevaBD = new MySQL(ruta, usuario, contrasena, tabla, colUsuario, colContrasena);

        if (nuevaBD.probarConexion()) {
            try {
                nuevaBD.guardarEnArchivo();
                MySQL.bd = nuevaBD;
                mostrarDialogo("Conexión correcta",
                        "La conexión con MySQL se ha establecido correctamente y se ha guardado en "
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
            // Guardar configuración para permitir su persistencia aunque MySQL no esté ejecutándose en este momento
            try {
                nuevaBD.guardarEnArchivo();
                MySQL.bd = nuevaBD;
                mostrarDialogo("Aviso de conexión",
                        "No se ha podido verificar la conexión con MySQL en 'localhost:3306/employees'.\n"
                        + "Asegúrese de que el servidor MySQL está iniciado.\n"
                        + "La configuración de usuario y contraseña se ha guardado en "
                        + MySQL.ARCHIVO_CONFIG + ".",
                        Alert.AlertType.WARNING);
                cerrarVentana();
            } catch (IOException ex) {
                mostrarDialogo("Error de conexión",
                        "No se ha podido conectar con la base de datos ni guardar el archivo.",
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
        if (ventana != null) {
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
