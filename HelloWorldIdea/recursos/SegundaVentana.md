Modificación de la clase principal del proyecto para añadir una segunda ventana en la aplicación

public class Loginfx extends Application {

/* Create new boolean property identified by isOpac;

isOpac hast public access type, so it can be accessed from any FXMController to associate events

*/

public static BooleanProperty isOpac = new SimpleBooleanProperty();

// Create a second window for your app. This will be showep up depending on the events...

public static Stage pop_up_stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

       Parent popup = FXMLLoader.load(getClass().getResource("Popup.fxml")); //add a second scene created in SceneBuilder

      // ADD scene to public static pop_up_stage (hidden by default):

       pop_up_state.setScene(new Scene(popup)); // add popup parent to scene

       pop_up_state.setMinWidth(300); // set min width to be displayed

       pop_up_state.setMinHeight(200);  // set min height to be displayed

primaryStage.setTitle("LOGINFX");
primaryStage.setScene(new Scene(root));
primaryStage.show();

                }

public static void main(String[] args) {

launch(args);

} // close main

}