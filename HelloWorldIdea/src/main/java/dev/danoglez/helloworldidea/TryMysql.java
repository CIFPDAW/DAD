package dev.danoglez.helloworldidea;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TryMysql {

    private static String JDBC_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    // Carga directa y sencilla del archivo properties
    static {
        try (java.io.InputStreamReader reader = new java.io.InputStreamReader(new FileInputStream("db.properties"), java.nio.charset.StandardCharsets.UTF_8)) {
            Properties props = new Properties();
            props.load(reader);

            JDBC_URL = props.getProperty("DB_URL");
            DB_USER = props.getProperty("DB_USER");
            DB_PASSWORD = props.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            System.out.println("Error cargando db.properties: " + e.getMessage());
        }
    }

    public static boolean validate(String user, String password) {
        boolean ctrl = false;
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
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
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            // Verificar primero si el usuario ya existe
            String checkQuery = "SELECT user_name FROM users WHERE user_name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, user);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) { // El usuario no existe, procedemos a crear
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
}