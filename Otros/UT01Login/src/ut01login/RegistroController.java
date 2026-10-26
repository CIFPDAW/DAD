package ut01login;

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

/*
 * ============================================================================
 *  CONTROLADOR DE LA VENTANA DE REGISTRO  (vinculado a Registro.fxml)
 * ============================================================================
 *
 *  Mismo funcionamiento que FXMLController:
 *   - Registro.fxml tiene  fx:controller="ut01login.RegistroController"
 *   - cada  fx:id="..."  del FXML  <->  atributo @FXML con el MISMO nombre.
 *   - initialize() se ejecuta solo al cargar el FXML.
 *
 *  Esta ventana la abre FXMLController.abrirRegistro() con showAndWait(),
 *  así que cuando aquí se cierra la ventana, el login vuelve a aparecer.
 */
public class RegistroController implements Initializable {

    @FXML
    private TextField tfUsuario;          // fx:id="tfUsuario"

    @FXML
    private PasswordField pfContrasena;   // fx:id="pfContrasena"

    @FXML
    private PasswordField pfConfirmacion; // fx:id="pfConfirmacion"

    @FXML
    private Button btnRegistrar;          // fx:id="btnRegistrar"

    @FXML
    private Button btnVolver;             // fx:id="btnVolver"

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Asociar cada botón con su método (lambda).
        btnRegistrar.setOnAction(e -> registrar());
        btnVolver.setOnAction(e -> cerrarVentana());
    }

    /**
     * Lógica del botón "Registrarse".
     *
     * PATRÓN DE VALIDACIÓN EN CADENA (muy reutilizable):
     *   cada comprobación que falla -> mostrar aviso + return.
     *   Si se pasan todas, se llega al final y se hace la acción.
     */
    private void registrar() {

        // 1) Leer campos
        String usuario = tfUsuario.getText().trim();
        String contrasena = pfContrasena.getText();
        String confirmacion = pfConfirmacion.getText();

        // 2) Validaciones (sin BD todavía)

        // a) Ningún campo vacío
        if (usuario.isEmpty() || contrasena.isEmpty() || confirmacion.isEmpty()) {
            mostrarDialogo(
                    "Campos vacíos",
                    "Debes completar todos los campos.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // b) Longitud mínima del usuario
        if (usuario.length() < 3) {
            mostrarDialogo(
                    "Usuario no válido",
                    "El usuario debe tener al menos 3 caracteres.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // c) Las dos contraseñas iguales.
        //    OJO: los String se comparan con .equals(), NUNCA con ==
        //    (== compara si son el mismo objeto en memoria, no el texto).
        if (!contrasena.equals(confirmacion)) {
            mostrarDialogo(
                    "Contraseñas diferentes",
                    "Las contraseñas no coinciden.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // Otras validaciones que te pueden pedir:
        //   if (contrasena.length() < 8) ...                    // longitud mínima
        //   if (!contrasena.matches(".*\\d.*")) ...              // que tenga un número
        //   if (!contrasena.matches(".*[A-Z].*")) ...            // que tenga una mayúscula
        //   if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) ... // formato de email

        // 3) Operaciones con la base de datos (pueden lanzar SQLException)
        try {
            // d) Que el usuario no exista ya
            if (MySQL.existeUsuario(usuario)) {
                mostrarDialogo(
                        "Usuario existente",
                        "Ese nombre de usuario ya está registrado.",
                        Alert.AlertType.WARNING
                );
                return;
            }

            // e) Insertar
            if (MySQL.registrarUsuario(usuario, contrasena)) {
                mostrarDialogo(
                        "Registro correcto",
                        "El usuario se ha registrado correctamente.",
                        Alert.AlertType.INFORMATION
                );

                // Al cerrar esta ventana, el login (que esperaba en
                // showAndWait) continúa y se vuelve a mostrar.
                cerrarVentana();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

            mostrarDialogo(
                    "Error de registro",
                    "No se ha podido registrar el usuario. "
                    + "Comprueba la conexión y la tabla users.",
                    Alert.AlertType.ERROR
            );
        }
    }

    /**
     * Igual que en FXMLController (método reutilizable).
     * Aquí se usa btnRegistrar para obtener la ventana actual.
     */
    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Stage ventana = (Stage) btnRegistrar.getScene().getWindow();
        Parent contenido = ventana.getScene().getRoot();

        alerta.initOwner(ventana);
        alerta.initModality(Modality.WINDOW_MODAL);

        ventana.setOpacity(0.5);
        contenido.setOpacity(0.5);

        alerta.showAndWait();

        ventana.setOpacity(1.0);
        contenido.setOpacity(1.0);
    }

    /**
     * Cierra ESTA ventana.
     * >>> REUTILIZABLE: control.getScene().getWindow() + close() <<<
     */
    private void cerrarVentana() {
        Stage ventana = (Stage) btnRegistrar.getScene().getWindow();
        ventana.close();
    }
}
