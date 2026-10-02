module dev.danoglez.testhelloworld {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.danoglez.testhelloworld to javafx.fxml;
    exports dev.danoglez.testhelloworld;
}