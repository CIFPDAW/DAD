package hellofxmysql;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportController implements Initializable {

    @FXML
    private TextField tfPresupuestoId;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfDni;

    @FXML
    private CheckBox cb1;

    @FXML
    private CheckBox cb2;

    @FXML
    private CheckBox cb3;

    @FXML
    private CheckBox cb4;

    @FXML
    private CheckBox cb5;

    @FXML
    private CheckBox cb6;

    @FXML
    private CheckBox cb7;

    @FXML
    private Label lblTotalServicios;

    @FXML
    private Label lblIgic;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnImprimir;

    // Array de números flotantes double[] para los precios de los servicios:
    // 1. Cambio de aceite: 79.54 €
    // 2. Revisión neumáticos: 19.95 €
    // 3. Sustitución pastillas de freno eje delantero: 90.23 €
    // 4. Sustitución pastillas de freno eje trasero: 90.23 €
    // 5. Lavado de motor: 49.45 €
    // 6. Revisión y cambio de filtros: 19.95 €
    // 7. Comprobación batería: 19.95 €
    private final double[] precios = {
        79.54,
        19.95,
        90.23,
        90.23,
        49.45,
        19.95,
        19.95
    };

    private CheckBox[] checkBoxes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxes = new CheckBox[]{cb1, cb2, cb3, cb4, cb5, cb6, cb7};

        // Asignación de manejador de eventos a cada CheckBox:
        // Se configuran tanto setOnMouseClicked como setOnAction para asegurar
        // la captura de eventos por clic y teclado.
        for (CheckBox cb : checkBoxes) {
            cb.setOnMouseClicked(e -> actualizarTotales());
            cb.setOnAction(e -> actualizarTotales());
        }

        if (btnImprimir != null) {
            btnImprimir.setOnAction(e -> imprimirPresupuesto());
        }
    }

    /**
     * Calcula los importes acumulados y actualiza el contenido de las etiquetas:
     * - "Total servicios de mantemiento"
     * - "I.G.I.C."
     * - "TOTAL"
     */
    private void actualizarTotales() {
        double totalServicios = 0.0;
        boolean algunSeleccionado = false;

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                totalServicios = totalServicios + precios[i];
                algunSeleccionado = true;
            }
        }

        if (!algunSeleccionado) {
            lblTotalServicios.setText("Total servicios de mantemiento: 0.00 €");
            lblIgic.setText("I.G.I.C.: 0.00 €");
            lblTotal.setText("TOTAL: 0.00 €");
        } else {
            // IGIC general en Canarias (7%)
            double igic = totalServicios * 0.07;
            double total = totalServicios + igic;

            lblTotalServicios.setText(String.format(Locale.US, "Total servicios de mantemiento: %.2f €", totalServicios));
            lblIgic.setText(String.format(Locale.US, "I.G.I.C.: %.2f €", igic));
            lblTotal.setText(String.format(Locale.US, "TOTAL: %.2f €", total));
        }
    }

    private void imprimirPresupuesto() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Impresión");
        alert.setHeaderText(null);
        alert.setContentText("Generando e imprimiendo factura/presupuesto de taller...");
        Stage ventana = (Stage) btnImprimir.getScene().getWindow();
        if (ventana != null) {
            alert.initOwner(ventana);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        alert.showAndWait();
    }
}
