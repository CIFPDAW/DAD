package ut01login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    public static MySQL bd = null;

    private String url;
    private String usuario;
    private String contrasena;
    private String tabla;
    private String columnaUsuario;
    private String columnaContrasena;

    public MySQL(String ruta, String usuario, String contrasena,
                 String tabla, String columnaUsuario, String columnaContrasena) {
        this.url = "jdbc:mysql://" + ruta;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tabla = tabla;
        this.columnaUsuario = columnaUsuario;
        this.columnaContrasena = columnaContrasena;
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, contrasena);
    }

    public boolean probarConexion() {
        String sql = "SELECT " + columnaUsuario + ", " + columnaContrasena
                + " FROM " + tabla;

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

    public boolean existeUsuario(String usuario) throws SQLException {
        String sql = "SELECT " + columnaUsuario + " FROM " + tabla
                + " WHERE " + columnaUsuario + " = ?";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean registrarUsuario(String usuario, String contrasena) throws SQLException {
        String sql = "INSERT INTO " + tabla + " (" + columnaUsuario + ", " + columnaContrasena + ")"
                + " VALUES (?, ?)";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            return ps.executeUpdate() > 0;
        }
    }
}
