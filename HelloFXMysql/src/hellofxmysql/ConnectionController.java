package hellofxmysql;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable {

    @FXML
    private VBox rootPane;

    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtDatabase;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DbConfig config = TryMysql.getConfig();
        txtHost.setText(config.getHost());
        txtPort.setText(config.getPort());
        txtDatabase.setText(config.getDatabase());
        txtUser.setText(config.getUser());
        txtPassword.setText(config.getPassword());
    }

    @FXML
    protected void onSaveClick() {
        if (txtHost.getText() == null || txtHost.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El host no puede estar vacío.");
            return;
        }
        if (txtPort.getText() == null || txtPort.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El puerto no puede estar vacío.");
            return;
        }
        if (txtDatabase.getText() == null || txtDatabase.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "La base de datos no puede estar vacía.");
            return;
        }
        if (txtUser.getText() == null || txtUser.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El usuario no puede estar vacío.");
            return;
        }

        DbConfig config = new DbConfig();
        config.setHost(txtHost.getText().trim());
        config.setPort(txtPort.getText().trim());
        config.setDatabase(txtDatabase.getText().trim());
        config.setUser(txtUser.getText().trim());
        config.setPassword(txtPassword.getText());

        try {
            config.save();
            TryMysql.reloadConfig();
            showAlert(Alert.AlertType.INFORMATION, "Éxito",
                    "Configuración guardada en " + DbConfig.CONFIG_FILE);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No se pudo guardar la configuración: " + e.getMessage());
        }
    }

    @FXML
    protected void onTestClick() {
        DbConfig testConfig = new DbConfig();
        testConfig.setHost(txtHost.getText().trim());
        testConfig.setPort(txtPort.getText().trim());
        testConfig.setDatabase(txtDatabase.getText().trim());
        testConfig.setUser(txtUser.getText().trim());
        testConfig.setPassword(txtPassword.getText());

        if (TryMysql.testConnection(testConfig)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Conexión correcta con MySQL.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No se pudo conectar. Revisa host, puerto, usuario y contraseña.");
        }
    }

    @FXML
    protected void onBackClick() {
        try {
            HelloFXMysql.setRoot("Hello", "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        rootPane.setOpacity(0.5);
        alert.showAndWait();
        rootPane.setOpacity(1.0);
    }
}
