module dev.danoglez.helloworldidea {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens dev.danoglez.helloworldidea to javafx.fxml;
    exports dev.danoglez.helloworldidea;
}
