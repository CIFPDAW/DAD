package ut01login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;

/*
 * ============================================================================
 *  CONTROLADOR DE LA VENTANA DE LOGIN  (vinculado a FXML.fxml)
 * ============================================================================
 *
 *  ¿Cómo se une este .java con el .fxml?
 *   1) En FXML.fxml, el nodo raíz tiene:
 *          fx:controller="ut01login.FXMLController"
 *      (paquete + nombre de la clase). Así JavaFX sabe qué clase crear.
 *
 *   2) Cada control del FXML que tenga  fx:id="tf1"  se "inyecta" en el
 *      atributo de esta clase que:
 *        - lleve la anotación @FXML
 *        - se llame EXACTAMENTE igual (tf1)
 *        - sea del mismo tipo (TextField)
 *      Si el nombre no coincide -> el atributo queda a null y al usarlo
 *      salta NullPointerException (error MUY típico en examen).
 *
 *   3) "implements Initializable" obliga a tener initialize(), que JavaFX
 *      llama SOLO, justo después de cargar el FXML e inyectar los @FXML.
 *      Es el sitio para preparar la pantalla (eventos, valores iniciales...).
 */
public class FXMLController implements Initializable {

    // ---- Controles inyectados desde el FXML (fx:id = nombre del atributo) ----
    @FXML
    private AnchorPane ap1;          // panel raíz        (fx:id="ap1")

    @FXML
    private TextField tf1;           // campo usuario     (fx:id="tf1")

    @FXML
    private PasswordField pf1;       // campo contraseña  (fx:id="pf1")

    @FXML
    private Button button1;          // botón "Acceder"   (fx:id="button1")

    @FXML
    private Button button2;          // botón "Nuevo registro" (fx:id="button2")

    @FXML
    private Label label1;            // etiqueta oculta (no se usa ahora; podría
                                     // servir para mostrar mensajes en vez de Alert:
                                     //   label1.setText("Error"); label1.setVisible(true);)

    /**
     * Se ejecuta automáticamente al cargar el FXML.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Asignar qué hace cada botón al pulsarlo.
        // "e -> acceder()" es una LAMBDA: "cuando ocurra el evento e, llama a acceder()".
        button1.setOnAction(e -> acceder());
        button2.setOnAction(e -> abrirRegistro());

        // ---------------------------------------------------------------
        //  FORMA ALTERNATIVA (también vale en el examen):
        //  En el FXML poner   onAction="#acceder"   en el botón,
        //  y aquí declarar el método con @FXML:
        //
        //      @FXML
        //      private void acceder() { ... }
        //
        //  Entonces NO hace falta el setOnAction. Usa una forma u otra, no las dos.
        // ---------------------------------------------------------------

        // Extra opcional: que la tecla ENTER pulse "Acceder".
        // button1.setDefaultButton(true);
    }

    /**
     * Lógica del botón "Acceder".
     * Patrón típico: 1) leer campos  2) validar  3) consultar BD  4) avisar.
     */
    private void acceder() {

        // 1) LEER los campos. getText() devuelve lo escrito.
        //    trim() quita espacios al principio y al final.
        String usuario = tf1.getText().trim();
        String contrasena = pf1.getText();

        // 2) VALIDAR: si falta algo, avisamos y "return" corta el método.
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarDialogo(
                    "Campos vacíos",
                    "Introduce el usuario y la contraseña.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // 3) CONSULTAR la base de datos. Puede fallar (servidor apagado,
        //    contraseña de BD mal...) -> por eso va dentro de try/catch.
        try {
            if (MySQL.comprobarLogin(usuario, contrasena)) {
                // 4a) Login correcto
                mostrarDialogo(
                        "Login correcto",
                        "Bienvenido, " + usuario + ".",
                        Alert.AlertType.INFORMATION
                );
                // Aquí podrías abrir otra ventana (misma idea que abrirRegistro()).
            } else {
                // 4b) Usuario o contraseña incorrectos
                mostrarDialogo(
                        "Error de acceso",
                        "El nombre de usuario y/o la contraseña "
                        + "son incorrectos.",
                        Alert.AlertType.ERROR
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // muestra el error real en la consola (útil para depurar)

            mostrarDialogo(
                    "Error de conexión",
                    "No se ha podido conectar con la base de datos.",
                    Alert.AlertType.ERROR
            );
        }
    }

    /**
     * Abre la ventana de registro (Registro.fxml) como ventana MODAL.
     *
     * >>> PLANTILLA REUTILIZABLE PARA ABRIR OTRA VENTANA <<<
     *   1. Obtener la ventana actual (a partir de cualquier control).
     *   2. FXMLLoader -> load() del nuevo .fxml.
     *   3. new Stage() + setScene(new Scene(root)).
     *   4. (opcional) initOwner + initModality para que sea modal.
     *   5. show() o showAndWait().
     */
    private void abrirRegistro() {

        // 1) Ventana actual. Truco: control -> getScene() -> getWindow()
        //    devuelve un Window; se hace cast a Stage.
        Stage ventanaLogin = (Stage) button2.getScene().getWindow();

        try {
            // 2) Cargar el FXML de la nueva ventana.
            //    Se usa "new FXMLLoader(...)" + load() (en vez de FXMLLoader.load(...)
            //    estático) porque así, si hiciera falta, podríamos pedir el
            //    controlador de la otra ventana para pasarle datos:
            //       RegistroController c = loader.getController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
            Parent root = loader.load();

            // 3) Crear la nueva ventana con su escena (600 x 400 píxeles).
            Stage ventanaRegistro = new Stage();
            ventanaRegistro.setTitle("Nuevo registro");
            ventanaRegistro.setScene(new Scene(root, 600, 400));
            ventanaRegistro.setResizable(false);

            // 4) MODAL: la ventana "pertenece" al login y bloquea su uso
            //    mientras esté abierta.
            //    - initOwner(...)       -> quién es la ventana "padre"
            //    - Modality.WINDOW_MODAL -> bloquea solo la ventana padre
            //      (APPLICATION_MODAL bloquearía todas las ventanas de la app)
            ventanaRegistro.initOwner(ventanaLogin);
            ventanaRegistro.initModality(Modality.WINDOW_MODAL);

            // Escondemos el login mientras se registra.
            ventanaLogin.hide();

            // 5) showAndWait() = muestra y ESPERA aquí hasta que se cierre.
            //    (show() mostraría y seguiría ejecutando sin esperar.)
            ventanaRegistro.showAndWait();

            // Cuando se cierra el registro, volvemos a mostrar el login.
            ventanaLogin.show();

        } catch (IOException ex) {
            // load() lanza IOException si no encuentra / no puede leer el FXML.
            ex.printStackTrace();

            mostrarDialogo(
                    "Error",
                    "No se ha podido abrir Registro.fxml.",
                    Alert.AlertType.ERROR
            );
        }
    }

    /**
     * Muestra un cuadro de diálogo (Alert) y oscurece la ventana mientras tanto.
     *
     * >>> MÉTODO REUTILIZABLE: cópialo a cualquier controlador.
     *     Solo cambia "button1" por cualquier control que exista en esa ventana. <<<
     *
     * Tipos de Alert.AlertType:
     *   INFORMATION (i azul), WARNING (triángulo), ERROR (X roja),
     *   CONFIRMATION (con Aceptar/Cancelar), NONE.
     *
     * Para una CONFIRMACIÓN con respuesta:
     *   Alert a = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro?");
     *   Optional<ButtonType> r = a.showAndWait();
     *   if (r.isPresent() && r.get() == ButtonType.OK) { ... }
     */
    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);          // título de la ventanita
        alerta.setHeaderText(null);       // null = sin cabecera grande
        alerta.setContentText(mensaje);   // el texto del mensaje

        // Ventana actual y su contenido raíz.
        Stage ventana = (Stage) button1.getScene().getWindow();
        Parent contenido = ventana.getScene().getRoot();

        // El Alert es modal respecto a esta ventana (sale encima y la bloquea).
        alerta.initOwner(ventana);
        alerta.initModality(Modality.WINDOW_MODAL);

        // Efecto visual: semitransparente mientras se ve el diálogo.
        ventana.setOpacity(0.5);
        contenido.setOpacity(0.5);

        alerta.showAndWait();             // espera a que el usuario pulse Aceptar

        // Restaurar opacidad normal.
        ventana.setOpacity(1.0);
        contenido.setOpacity(1.0);
    }
}
