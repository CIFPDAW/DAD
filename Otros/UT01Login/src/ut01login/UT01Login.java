package ut01login;
// "package" = la carpeta en la que está la clase (src/ut01login).
// Todos los .java, .fxml y .css de este proyecto están en el MISMO paquete,
// por eso desde aquí basta con poner "FXML.fxml" sin ruta.

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.fxml.*;

/*
 * ============================================================================
 *  CLASE PRINCIPAL (punto de entrada de la aplicación JavaFX)
 * ============================================================================
 *
 *  JERARQUÍA DE JAVAFX (muy de examen):
 *
 *      Stage   -> la VENTANA (el marco con la barra de título, cerrar, etc.)
 *        └─ Scene  -> el CONTENIDO de la ventana (aquí se cargan los CSS)
 *             └─ Parent / root -> el nodo raíz del FXML (AnchorPane,
 *                  StackPane, VBox...) y dentro todos los controles.
 *
 *  Toda app JavaFX:
 *    1) extiende Application
 *    2) sobrescribe start(Stage stage)
 *    3) en main() llama a launch(args)  -> launch() acaba llamando a start()
 *
 *  >>> PLANTILLA REUTILIZABLE: copia esta clase tal cual y cambia solo
 *      el nombre del .fxml y el título. <<<
 */
public class UT01Login extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // 1) Cargar el FXML.
        //    getClass().getResource("FXML.fxml") busca el archivo en el mismo
        //    paquete que esta clase. Si devuelve null => el nombre está mal
        //    escrito o el fxml no está en la misma carpeta (error típico:
        //    "Location is not set").
        //    Al cargar el FXML, JavaFX crea automáticamente el controlador
        //    indicado en fx:controller (FXMLController) y llama a su initialize().
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        // 2) Crear la escena con ese contenido.
        Scene scene = new Scene(root);

        // ---------------------------------------------------------------
        //  FORMA ALTERNATIVA DE VINCULAR EL CSS (desde Java en vez del FXML)
        //  Aquí NO hace falta porque el FXML ya tiene stylesheets="@estilos.css",
        //  pero si en el examen el FXML no lo tiene, descomenta esta línea:
        //
        //  scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        //
        //  Ventaja: el CSS se aplica a TODA la escena, sea cual sea el FXML.
        // ---------------------------------------------------------------

        // 3) Configurar la ventana y mostrarla.
        stage.setTitle("LOGINFX");     // texto de la barra de título
        stage.setScene(scene);         // la ventana muestra esta escena
        // stage.setResizable(false);  // (opcional) impedir redimensionar
        stage.show();                  // ¡sin esto no se ve nada!
    }

    public static void main(String[] args) {
        launch(args); // arranca JavaFX -> llama a start()
    }
}
