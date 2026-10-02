package dev.danoglez.helloworldidea;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloAplication extends Application {

    @Override
    public void start(Stage stage) {

        // --- 1. ETIQUETAS PARA MOSTRAR RESULTADOS ---
        Label labelClick = new Label("¡Has hecho click!");
        labelClick.setVisible(false);

        Label respuestaDb = new Label();
        respuestaDb.setVisible(false);

        Label respuestaDb2 = new Label();
        respuestaDb2.setVisible(false);

        Label respuestaCrear = new Label();
        respuestaCrear.setVisible(false);

        // --- 2. BOTONES ---
        Button btnMensaje = new Button("Mostrar Mensaje");
        Button btnValidar = new Button("Validar Usuarios");
        Button btnCrtUser = new Button("Crear Usuario");

        // --- 3. DISEÑO (LAYOUTS) ---
        // Caja vertical para centrar los botones
        VBox cajaBotones = new VBox(15); // 15 píxeles de separación
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.getChildren().addAll(btnMensaje, btnValidar, btnCrtUser);

        // Caja vertical para agrupar los mensajes abajo
        VBox cajaResultados = new VBox(10);
        cajaResultados.setAlignment(Pos.CENTER);
        cajaResultados.getChildren().addAll(labelClick, respuestaDb, respuestaDb2, respuestaCrear);

        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(cajaBotones);
        borderpane.setBottom(cajaResultados);

        // --- 4. EVENTOS DE LOS BOTONES ---

        // Opción 1: Mensaje simple
        btnMensaje.setOnAction(e -> {
            labelClick.setVisible(true);
        });

        // Opción 2: Validar en Base de Datos
        btnValidar.setOnAction(e -> {
            boolean existe = TryMysql.validate("admin", "AdminPass2026!");
            respuestaDb.setText("Admin 1 existe: " + existe);
            respuestaDb.setVisible(true);

            boolean existe2 = TryMysql.validate("admin", "admin");
            respuestaDb2.setText("Admin 2 existe: " + existe2);
            respuestaDb2.setVisible(true);
        });

        // Opción 3: Crear usuario en Base de Datos
        btnCrtUser.setOnAction(e -> {
            // Le pasamos un usuario de prueba para crearlo
            TryMysql.create_user("nuevo_user", "pass1234");
            respuestaCrear.setText("Usuario 'nuevo_user' insertado en la BD.");
            respuestaCrear.setVisible(true);
        });

        Scene scene = new Scene(borderpane, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}