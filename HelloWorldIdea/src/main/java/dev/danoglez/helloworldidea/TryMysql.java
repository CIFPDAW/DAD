package dev.danoglez.helloworldidea;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TryMysql {

    private static String JDBC_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    static Connection conn = null;

    // Carga directa y sencilla del archivo properties
    static {
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fis);

            JDBC_URL = props.getProperty("DB_URL");
            DB_USER = props.getProperty("DB_USER");
            DB_PASSWORD = props.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            System.out.println("Error cargando db.properties: " + e.getMessage());
        }
    }

    static boolean validate(String user, String password) {
        boolean ctrl = false;
        try {
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT user_name, user_password FROM users";
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                if (rs.getString("user_name").equals(user) && rs.getString("user_password").equals(password))
                    ctrl = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return ctrl;
    }

    static void create_user(String user, String password) {
        try {
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            String query = "INSERT INTO users (user_name,user_password) VALUES (?,?)";
            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.setString(1, user);
            insertStatement.setString(2, password);
            insertStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}