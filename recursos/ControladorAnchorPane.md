EJEMPLO DE USO DEL CONTENEDOR AnchorPane

// add all imports

import javafx.controls.*;

.

.

.

import javafx.scene.layout.*; // import layout library classes

public class MyLoginScene {

public static Button btn = new Button();

public static TextField user_tf = new TextField();

public static TextField pass_tf = new TextField();

// Crear escena con un objeto AnchorPane como contenedor principal

/* El tamaño de los elementos añadidos se ajustará a las restricciones del objeto

AnchorPane */

public static Scene add() {

AnchorPane root = new AnchorPane();

// Definir una escena de 400 x 400 (en px);

Scene = new Scene(root,400,400);

// Añadir botón al contenedor principal que diste 30px de la base y centrado;

// El botón tendrá un ancho de 50px;

root.setBottomAnchor(btn,30);

root.setLeftAnchor(btn,175);

root.setRightAnchor(btn,175);

// Añade un panel con color de fondo en el borde superior que ocupe todo el ancho

Pane pane = new Pane(); pane.setStyle("fx-background-color: rgba(0,255,255,0.5);");

root.setTopAnchor(pane,0);

root.setLeftAnchor(pane,0);

root.setRightAnchor(pane,0);

// El pane tendrá una altura de 100px:

root.setBottom(pane,300);

// Añadir en bloque varios elementos contenidos en una VBox

VBox vb_1 = new VBox(10); VBox vb_2 = new VBox(10);

Label user_lb = new Label("Enter your user ID");

Label pass_lb = new Label("Enter your password");

vbox_1.getChildren().addAll(user_lb,user_tf);

vbox_2.getChildren().addAll(pass_lb,pass_tf);

VBox vbox = new VBox(20);

vbox.getChildren().addAll(vbox_1,vbox_2);

// vbox tendrá una anchura de 200px y estará completamente centrada en la escena:

root.setTopAnchor(vbox,100);

root.setBottomAnchor(vbox,100);

root.setLeftAnchor(vbox,150);

root.setRightAnchor(vobx,150);

return scene;

} // close add method

/* Usage from MainClass.java:

Scene login_scene = MyLoginScene.add();

mainStage.setScene(login_scene);

login_scene.btn.setText("Enviar");

login_scene.btn.setOnMouseClicked( e -> {

String user = login_scene.user_tf.getText();

String pass = login_scene.pass_tf.getText();

// do something

});

mainStage.show();

*/

}