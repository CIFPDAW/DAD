package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private static final String URL_BD = "jdbc:mysql://localhost:3306/logins";
    private static final String USUARIO_BD = "cesar";
    private static final String CONTRASENA_BD = "Cesar1234!";

    private MySQL() {
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL_BD, USUARIO_BD, CONTRASENA_BD);
    }

    public static boolean comprobarLogin(String usuario, String contrasena) throws SQLException {
        String sql = """
                SELECT user_name
                FROM users
                WHERE user_name = ?
                AND user_password = ?
                """;

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static boolean existeUsuario(String usuario) throws SQLException {
        String sql = """
                SELECT user_name
                FROM users
                WHERE user_name = ?
                """;

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static boolean registrarUsuario(String usuario, String contrasena) throws SQLException {
        String sql = """
                INSERT INTO users (user_name, user_password)
                VALUES (?, ?)
                """;

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            return ps.executeUpdate() > 0;
        }
    }
}
