// Connect to MySQL databse logins and access to table logins.users 

// Field names in logins.users table in this example are set to user_name and user_password

// Mysql server access credentials user/passwd in this example are root/Linux_1910

// COMPILE AND EXECUTE WITH OPTION -cp full_path_to_mysql_driver_jar_file

import java.sql.*;

public class TryMysql {

static Connection conn = null;

static boolean validate(String user, String password) {

boolean ctrl = false;

try {

conn = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","Linux_1910");

String query = "SELECT user_name, user_password FROM logins.users";
//query = "SELECT user_name, user_password FROM logins.users";
        ResultSet rs  = conn.createStatement().executeQuery(query); 

        while (rs.next()) {

        if (rs.getString("user_name").equals(user) &&  rs.getString("user_password").equals(password)) ctrl = true; 

        } //close while loop

           }  catch(SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());

}

   return ctrl;

    } // close VALIDATE



   static void create_user(String user, String password) {

try {

conn = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","Linux_1910");

Boolean ctrl = false; 


String query = "INSERT INTO logins.users (user_name,user_password) VALUES (?,?)";

 PreparedStatement insertStatement = conn.prepareStatement(query);
    insertStatement.setString(1,user);
    insertStatement.setString(2,password);
    insertStatement.executeUpdate();


} catch(SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());

}

    } // close CREATE_USER;



public static void main(String[] args)  { }

         }
