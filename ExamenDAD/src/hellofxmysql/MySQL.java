package hellofxmysql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    public static final String ARCHIVO_CONFIG = "db_config.txt";

    // Parámetros predeterminados según el enunciado del examen:
    // Base de datos: employees
    // Tabla: employees
    // Campo de usuario: first_name
    // Campo de contraseña: last_name
    public static final String RUTA_PREDETERMINADA = "localhost:3306/employees";
    public static final String TABLA_PREDETERMINADA = "employees";
    public static final String COLUMNA_USUARIO_PREDETERMINADA = "first_name";
    public static final String COLUMNA_CONTRASENA_PREDETERMINADA = "last_name";

    public static MySQL bd = null;

    private String ruta;
    private String usuario;
    private String contrasena;
    private String tabla;
    private String columnaUsuario;
    private String columnaContrasena;

    public MySQL(String ruta, String usuario, String contrasena,
                 String tabla, String columnaUsuario, String columnaContrasena) {
        this.ruta = (ruta != null && !ruta.trim().isEmpty()) ? ruta : RUTA_PREDETERMINADA;
        this.usuario = (usuario != null) ? usuario : "root";
        this.contrasena = (contrasena != null) ? contrasena : "";
        this.tabla = (tabla != null && !tabla.trim().isEmpty()) ? tabla : TABLA_PREDETERMINADA;
        this.columnaUsuario = (columnaUsuario != null && !columnaUsuario.trim().isEmpty()) ? columnaUsuario : COLUMNA_USUARIO_PREDETERMINADA;
        this.columnaContrasena = (columnaContrasena != null && !columnaContrasena.trim().isEmpty()) ? columnaContrasena : COLUMNA_CONTRASENA_PREDETERMINADA;
    }

    public static MySQL obtenerPredeterminado() {
        return new MySQL(
                RUTA_PREDETERMINADA,
                "root",
                "",
                TABLA_PREDETERMINADA,
                COLUMNA_USUARIO_PREDETERMINADA,
                COLUMNA_CONTRASENA_PREDETERMINADA
        );
    }

    public String getRuta() {
        return ruta;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getTabla() {
        return tabla;
    }

    public String getColumnaUsuario() {
        return columnaUsuario;
    }

    public String getColumnaContrasena() {
        return columnaContrasena;
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + ruta, usuario, contrasena);
    }

    public boolean probarConexion() {
        String sql = "SELECT " + columnaUsuario + ", " + columnaContrasena
                + " FROM " + tabla + " LIMIT 1";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.executeQuery();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean comprobarLogin(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT " + columnaUsuario + " FROM " + tabla
                + " WHERE " + columnaUsuario + " = ? AND " + columnaContrasena + " = ?";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void guardarEnArchivo() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CONFIG))) {
            writer.write("ruta=" + ruta);
            writer.newLine();
            writer.write("usuario=" + usuario);
            writer.newLine();
            writer.write("contrasena=" + contrasena);
            writer.newLine();
            writer.write("tabla=" + tabla);
            writer.newLine();
            writer.write("columnaUsuario=" + columnaUsuario);
            writer.newLine();
            writer.write("columnaContrasena=" + columnaContrasena);
            writer.newLine();
        }
    }

    public static MySQL cargarDesdeArchivo() {
        String ruta = null;
        String usuario = null;
        String contrasena = "";
        String tabla = null;
        String colUsuario = null;
        String colContrasena = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CONFIG))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || !line.contains("=")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                String key = parts[0].trim();
                String value = parts.length > 1 ? parts[1].trim() : "";

                switch (key) {
                    case "ruta" -> ruta = value;
                    case "usuario" -> usuario = value;
                    case "contrasena" -> contrasena = value;
                    case "tabla" -> tabla = value;
                    case "columnaUsuario" -> colUsuario = value;
                    case "columnaContrasena" -> colContrasena = value;
                    default -> { }
                }
            }
        } catch (IOException e) {
            return null;
        }

        if (ruta == null || ruta.isEmpty()) ruta = RUTA_PREDETERMINADA;
        if (usuario == null) usuario = "root";
        if (tabla == null || tabla.isEmpty()) tabla = TABLA_PREDETERMINADA;
        if (colUsuario == null || colUsuario.isEmpty()) colUsuario = COLUMNA_USUARIO_PREDETERMINADA;
        if (colContrasena == null || colContrasena.isEmpty()) colContrasena = COLUMNA_CONTRASENA_PREDETERMINADA;

        return new MySQL(ruta, usuario, contrasena, tabla, colUsuario, colContrasena);
    }
}
