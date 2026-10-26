package hellofxmysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TryMysql {

    private static DbConfig config = DbConfig.load();

    /** Recarga la configuración desde db_config.txt */
    public static void reloadConfig() {
        config = DbConfig.load();
    }

    public static DbConfig getConfig() {
        return config;
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                config.getJdbcUrl(),
                config.getUser(),
                config.getPassword()
        );
    }

    public static boolean validate(String user, String password) {
        boolean ctrl = false;
        try (Connection conn = connect()) {
            String query = "SELECT user_name, user_password FROM users WHERE user_name = ? AND user_password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ctrl = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException en validate: " + ex.getMessage());
        }
        return ctrl;
    }

    public static boolean create_user(String user, String password, String email) {
        boolean ctrl = false;
        try (Connection conn = connect()) {
            String checkQuery = "SELECT user_name FROM users WHERE user_name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, user);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                String query = "INSERT INTO users (user_name, user_password, e_mail) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(query);
                insertStatement.setString(1, user);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);

                if (insertStatement.executeUpdate() == 1) {
                    ctrl = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException en create_user: " + ex.getMessage());
        }
        return ctrl;
    }

    /** Prueba si se puede abrir conexión con la config indicada */
    public static boolean testConnection(DbConfig testConfig) {
        try (Connection conn = DriverManager.getConnection(
                testConfig.getJdbcUrl(),
                testConfig.getUser(),
                testConfig.getPassword())) {
            return conn != null && !conn.isClosed();
        } catch (SQLException ex) {
            System.out.println("SQLException en testConnection: " + ex.getMessage());
            return false;
        }
    }
}
