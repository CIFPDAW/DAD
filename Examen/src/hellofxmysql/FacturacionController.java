package hellofxmysql;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

public class FacturacionController implements Initializable {

    private static final double PORCENTAJE_IGIC = 0.07;

    private static final String[] NOMBRES_SERVICIOS = {
        "Cambio de aceite",
        "Filtro de aceite",
        "Filtro de aire",
        "Pastillas de freno",
        "Alineación y balanceo",
        "Revisión de neumáticos",
        "Revisión sistema de frenos",
        "Diagnóstico electrónico"
    };

    private static final double[] PRECIOS_SERVICIOS = {
        45.00,
        18.50,
        22.00,
        95.00,
        65.00,
        30.00,
        40.00,
        55.00
    };

    private String empleado = "";
    private int contadorFacturas = 1;

    @FXML
    private Label lblEmpleado;

    @FXML
    private TextField tfNombreCliente;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfTelefono;

    @FXML
    private CheckBox cbCambioAceite;

    @FXML
    private CheckBox cbFiltroAceite;

    @FXML
    private CheckBox cbFiltroAire;

    @FXML
    private CheckBox cbPastillasFreno;

    @FXML
    private CheckBox cbAlineacion;

    @FXML
    private CheckBox cbRevisionNeumaticos;

    @FXML
    private CheckBox cbRevisionFrenos;

    @FXML
    private CheckBox cbDiagnostico;

    @FXML
    private Label lblPrecioAceite;

    @FXML
    private Label lblPrecioFiltroAceite;

    @FXML
    private Label lblPrecioFiltroAire;

    @FXML
    private Label lblPrecioPastillasFreno;

    @FXML
    private Label lblPrecioAlineacion;

    @FXML
    private Label lblPrecioRevisionNeumaticos;

    @FXML
    private Label lblPrecioRevisionFrenos;

    @FXML
    private Label lblPrecioDiagnostico;

    @FXML
    private Label lblTotalServicios;

    @FXML
    private Label lblIGIC;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnGenerar;

    @FXML
    private Button btnLimpiar;

    private CheckBox[] checkBoxes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxes = new CheckBox[]{
            cbCambioAceite,
            cbFiltroAceite,
            cbFiltroAire,
            cbPastillasFreno,
            cbAlineacion,
            cbRevisionNeumaticos,
            cbRevisionFrenos,
            cbDiagnostico
        };

        for (CheckBox cb : checkBoxes) {
            cb.setOnAction(e -> actualizarTotales());
        }

        btnGenerar.setOnAction(e -> generarFactura());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        actualizarTotales();
    }

    public void setUsuario(String nombreUsuario) {
        this.empleado = nombreUsuario;
        if (lblEmpleado != null) {
            lblEmpleado.setText("Empleado: " + nombreUsuario);
        }
    }

    private void actualizarTotales() {
        double totalServicios = 0.0;

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                totalServicios += PRECIOS_SERVICIOS[i];
            }
        }

        double igic = totalServicios * PORCENTAJE_IGIC;
        double total = totalServicios + igic;

        lblTotalServicios.setText(String.format("%.2f €", totalServicios));
        lblIGIC.setText(String.format("%.2f €", igic));
        lblTotal.setText(String.format("%.2f €", total));
    }

    private void generarFactura() {
        String nombre = tfNombreCliente.getText().trim();
        String dni = tfDni.getText().trim();
        String matricula = tfMatricula.getText().trim();
        String telefono = tfTelefono.getText().trim();

        if (nombre.isEmpty() || dni.isEmpty() || matricula.isEmpty()) {
            mostrarDialogo(
                    "Campos incompletos",
                    "Debes indicar al menos nombre, DNI y matrícula del cliente.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        List<String> serviciosSeleccionados = new ArrayList<>();
        double totalServicios = 0.0;

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                serviciosSeleccionados.add(String.format(
                        "- %s (%.2f €)",
                        NOMBRES_SERVICIOS[i],
                        PRECIOS_SERVICIOS[i]
                ));
                totalServicios += PRECIOS_SERVICIOS[i];
            }
        }

        if (serviciosSeleccionados.isEmpty()) {
            mostrarDialogo(
                    "Sin servicios",
                    "Selecciona al menos un servicio para generar la factura.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        double igic = totalServicios * PORCENTAJE_IGIC;
        double total = totalServicios + igic;
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String numero = String.format("FAC-%04d", contadorFacturas++);

        StringBuilder resumen = new StringBuilder();
        resumen.append("Factura: ").append(numero).append("\n");
        resumen.append("Fecha: ").append(fecha).append("\n");
        if (!empleado.isEmpty()) {
            resumen.append("Empleado: ").append(empleado).append("\n");
        }
        resumen.append("\nCliente: ").append(nombre).append("\n");
        resumen.append("DNI/NIF: ").append(dni).append("\n");
        resumen.append("Matrícula: ").append(matricula).append("\n");
        if (!telefono.isEmpty()) {
            resumen.append("Teléfono: ").append(telefono).append("\n");
        }
        resumen.append("\nServicios:\n");
        for (String servicio : serviciosSeleccionados) {
            resumen.append(servicio).append("\n");
        }
        resumen.append("\nTotal servicios: ").append(String.format("%.2f €", totalServicios));
        resumen.append("\nI.G.I.C. (7%): ").append(String.format("%.2f €", igic));
        resumen.append("\nTOTAL: ").append(String.format("%.2f €", total));

        mostrarDialogo("Factura generada", resumen.toString(), Alert.AlertType.INFORMATION);
    }

    private void limpiarFormulario() {
        tfNombreCliente.clear();
        tfDni.clear();
        tfMatricula.clear();
        tfTelefono.clear();

        for (CheckBox cb : checkBoxes) {
            cb.setSelected(false);
        }

        actualizarTotales();
    }

    private void mostrarDialogo(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Stage ventana = (Stage) btnGenerar.getScene().getWindow();
        alerta.initOwner(ventana);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.showAndWait();
    }
}
