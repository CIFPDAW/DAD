package ut01login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * ============================================================================
 *  CLASE DE ACCESO A LA BASE DE DATOS (JDBC + MySQL)
 * ============================================================================
 *
 *  Idea: TODO lo que toca la base de datos va aquí. Los controladores
 *  (FXMLController, RegistroController) solo llaman a estos métodos
 *  estáticos: MySQL.comprobarLogin(...), MySQL.registrarUsuario(...), etc.
 *
 *  REQUISITOS para que funcione:
 *   - Tener el driver mysql-connector-j-X.jar añadido a las librerías del
 *     proyecto (en NetBeans: clic derecho en Libraries > Add JAR/Folder).
 *     Si falta -> SQLException "No suitable driver found for jdbc:mysql..."
 *   - MySQL arrancado y la base de datos + tabla creadas. Script de ejemplo:
 *
 *       CREATE DATABASE logins;
 *       USE logins;
 *       CREATE TABLE users (
 *           id            INT AUTO_INCREMENT PRIMARY KEY,
 *           user_name     VARCHAR(50)  NOT NULL UNIQUE,
 *           user_password VARCHAR(100) NOT NULL
 *       );
 *       INSERT INTO users (user_name, user_password) VALUES ('admin', '1234');
 *
 *  >>> REUTILIZABLE: para otra BD/tabla solo cambias URL_BD, usuario,
 *      contraseña y las sentencias SQL. El "esqueleto" de cada método
 *      (try-with-resources + PreparedStatement) es siempre igual. <<<
 */
public class MySQL {

    // Datos de conexión. Formato de la URL:
    //   jdbc:mysql://SERVIDOR:PUERTO/NOMBRE_BASE_DE_DATOS
    // "static final" = constantes de clase (no cambian nunca).
    private static final String URL_BD = "jdbc:mysql://localhost:3306/logins";
    private static final String USUARIO_BD = "cesar";
    private static final String CONTRASENA_BD = "Cesar1234!";

    // Constructor privado: nadie puede hacer "new MySQL()".
    // Es una clase de utilidades: solo se usan sus métodos static.
    private MySQL() {
    }

    /**
     * Abre una conexión nueva con la base de datos.
     * "throws SQLException" = si falla, el error se lanza hacia quien llame
     * (el controlador lo captura con try/catch y muestra un Alert).
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL_BD, USUARIO_BD, CONTRASENA_BD);
    }

    /**
     * SELECT: devuelve true si existe un usuario con ese nombre Y contraseña.
     *
     * PLANTILLA PARA CONSULTAS (SELECT):
     *   1. Escribir el SQL con ? donde van los valores.
     *   2. try (conexión; PreparedStatement) { ... }
     *   3. ps.setString(posición, valor)  -> las posiciones empiezan en 1
     *   4. ps.executeQuery()  -> devuelve un ResultSet (las filas)
     *   5. rs.next()  -> avanza a la siguiente fila; true si había fila.
     */
    public static boolean comprobarLogin(String usuario, String contrasena) throws SQLException {

        // """ ... """ = text block (Java 15+): un String de varias líneas.
        // Los ? son "huecos" que se rellenan después con setString.
        // ¿Por qué ? y no concatenar "...WHERE user_name='" + usuario + "'"?
        //   -> Por SEGURIDAD: evita la INYECCIÓN SQL (pregunta de examen).
        String sql = """
                SELECT user_name
                FROM users
                WHERE user_name = ?
                AND user_password = ?
                """;

        // try-with-resources: lo que se abre entre los paréntesis del try
        // se CIERRA AUTOMÁTICAMENTE al terminar (aunque haya error).
        // Así no hace falta escribir conexion.close(), ps.close()...
        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);     // primer  ?  -> usuario
            ps.setString(2, contrasena);  // segundo ?  -> contraseña

            try (ResultSet rs = ps.executeQuery()) {
                // Si hay al menos una fila, el login es correcto.
                return rs.next();

                // Si quisieras LEER datos de la fila:
                //   if (rs.next()) {
                //       String nombre = rs.getString("user_name");
                //       int id = rs.getInt("id");
                //   }
                // Y para recorrer TODAS las filas:  while (rs.next()) { ... }
            }
        }
    }

    /**
     * SELECT: devuelve true si ya existe un usuario con ese nombre.
     * Se usa en el registro para no repetir nombres.
     * (Misma plantilla que el anterior, con un solo ?)
     */
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

    /**
     * INSERT: inserta un usuario nuevo. Devuelve true si se insertó.
     *
     * PLANTILLA PARA INSERT / UPDATE / DELETE:
     *   igual que la de SELECT, pero se usa ps.executeUpdate(),
     *   que devuelve un int = número de filas afectadas.
     *   > 0  significa que se ha hecho el cambio.
     *
     *   Ejemplos que podrías necesitar:
     *     UPDATE users SET user_password = ? WHERE user_name = ?
     *     DELETE FROM users WHERE user_name = ?
     */
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
