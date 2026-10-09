IMPLEMENTACIÓN DE EVENTOS EN JAVAFX

OPCIÓN 1: utilizando la clase genérica EventHandler y reescribiendo su único método definido:

button.setOnAction(new EventHandler<ActionEvent>() {
@Override public void handle(ActionEvent event) {
System.out.println("Clicked");
}
});

OPCIÓN 2: utilizando la clase genérica EventHandler pero simplificando la sintaxis, ya que cuenta con un único método:
button.setOnAction(e -> {
System.out.println("Clicked");
});

OPCIÓN 3: utilizando los métodos que incorporan todas las clases definidas por el paquete javafx.scene.control.Control (entre las que se encuentra la clase Button):

button.setOnMouseClicked(e -> {
System.out.println("Clicked");
});

Este tipo de métodos nos permiten a) definir de manera más concreta el tipo de evento y b) utilizar notación simplificada.

Nota: el método queda descrito en el manual JavaFX 8.0 de acuerdo a:

setOnMouseClicked(EventHandler<? super MouseEvent> value)

Implementación 1:

button.setOnMouseClicked( new EventHandler<? super MouseEvent>()  {

@Override

public void handle( Event e) {

System.out.println("Clicked");

}

});
Implementación 2:

button.setOnMouseClicked( e -> System.out.println("Cilcked"));