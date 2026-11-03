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

/**
 * Controlador para la configuración de conexión a MySQL.
 * Permite al administrador modificar solo usuario y contraseña.
 * La BD (employees), tabla (employees) y campos (first_name/last_name) son fijos.
 */
public class ConfigBDController implements Initializable {

    // Parámetros fijos según enunciado
    private static final String RUTA_FIJA = "localhost:3306/employees";
    private static final String TABLA_FIJA = "employees";
    private static final String COLUMNA_USUARIO_FIJA = "first_name";
    private static final String COLUMNA_CONTRASENA_FIJA = "last_name";

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar valores actuales si existen
        MySQL actual = MySQL.bd;
        if (actual == null) {
            actual = MySQL.cargarDesdeArchivo();
        }
        if (actual != null) {
            tfUsuario.setText(actual.getUsuario());
            pfContrasena.setText(actual.getContrasena());
        }

        btnAceptar.setOnAction(e -> aceptar());
        btnCancelar.setOnAction(e -> cerrarVentana());
    }

    /**
     * Procesa la configuración y prueba la conexión a MySQL.
     */
    private void aceptar() {
        String usuario = tfUsuario.getText().trim();
        String contrasena = pfContrasena.getText();

        if (usuario.isEmpty()) {
            mostrarDialogo("Campo vacío",
                    "Debes ingresar el usuario de MySQL.",
                    Alert.AlertType.WARNING);
            return;
        }

        // Crear objeto MySQL con parámetros fijos
        MySQL nuevaBD = new MySQL(RUTA_FIJA, usuario, contrasena, 
                                  TABLA_FIJA, COLUMNA_USUARIO_FIJA, COLUMNA_CONTRASENA_FIJA);

        // Probar la conexión
        if (nuevaBD.probarConexion()) {
            try {
                nuevaBD.guardarEnArchivo();
                MySQL.bd = nuevaBD;
                mostrarDialogo("Conexión correcta",
                        "La configuración MySQL se ha guardado correctamente en "
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
                    "No se pudo conectar a MySQL. Verifica usuario, contraseña y que "
                    + "la BD 'employees' exista con la tabla 'employees'.",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Muestra un diálogo modal con un mensaje.
     */
    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Cierra la ventana de configuración.
     */
    private void cerrarVentana() {
        Stage ventana = (Stage) btnAceptar.getScene().getWindow();
        ventana.close();
    }
}
