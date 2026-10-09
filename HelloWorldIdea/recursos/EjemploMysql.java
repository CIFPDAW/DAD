import java.sql.*;

public class Mysql {

    static  Connection conn = null;

    static boolean validate(String field_1, String field_2) {

        boolean ctrl=false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306","app_user","app_password");

            String query =  "SELECT user_name,user_password FROM logins.users WHERE user_name = ? AND user_password = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,field_1); ps.setString(2,field_2);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) ctrl = true;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
        }

        return ctrl;

    } // CLOSE VALIDATE

    public static boolean register_user (String field_1, String field_2) {

        boolean ctrl = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306","app_user","app_password");

            String query =  "SELECT user_name,user_password FROM logins.users WHERE user_name = ? OR user_password = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,field_1); ps.setString(2,field_2);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) { // usuario y contraseña no deben estar replicados en la DB

                String sql ="INSERT INTO logins.users (user_name,user_password) VALUES (?,?)";

                ps = conn.prepareStatement(sql);

                ps.setString(1,field_1);ps.setString(2,field_2);

                if (ps.executeUpdate() == 1) ctrl = true;

            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
        }

        return ctrl;

    } // CLOSE REGISTER_USER

}