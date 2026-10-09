CÓDIGO MUESTRA E INDICACIONES PARA HABILITAR CONEXIÓN A MYSQL

// Download the mysql .jar connector from MySQL website and add it to project libraries

package hellofx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.event.*;
import java.util.regex.Matcher; // use of RegEx to validate user and password
import java.util.regex.Pattern;
import java.sql.*

public class FXMController {

@FXML     private Button send;

@FXML     private TextField user;;

@FXML     private TextField passwd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    // Add eventAction to send button
    send.setOnMouseClicked(e -> {
    
    String usuario = user.getText();
    String passwd = passwd.getText();

// Use Mysql previously defined class:

if (!Mysql.validate(usuario,passwd)) {

System.out.print("Usuario y/o contraseña incorrecta");

} else {

System.out.print("Usuario validado");

}


/*

// Connection configuration:

String driver = "com.mysql.cj.jdbc.Driver";
Connection conn;  // create connection objetc

    try {   
    Class.forName(driver);
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logins?" + "user=my_user&password=my_password");
           
        // Do something with the Connection:

            System.out.println("Conectado a base datos");

stmt =  conn.createStatement();

query = "SELECT user,passwd FROM logins.users WHERE user = usuario AND passwd = 'passwd'" ;

if (conn.createStatement().executeQuery(query)) {

System.out.println("Usuario validado");

} else {

System.out.println("Usuario y/o contraseña incorrecto");

} // close if statement

} catch (SQLException ex) {
// handle any errors
System.out.println("SQLException: " + ex.getMessage());
System.out.println("SQLState: " + ex.getSQLState());
System.out.println("VendorError: " + ex.getErrorCode());
}       catch (ClassNotFoundException ex) {
System.getLogger(LoginController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
}
*/   
});

}    // close initialize

            }