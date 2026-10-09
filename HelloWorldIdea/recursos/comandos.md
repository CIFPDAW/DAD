COMPILACIÓN Y EJECUCIÓN DE APLICACIONES DESDE LA LÍNEA DE COMANDOS

Para compilar código fuente de aplicaciones JavaFX desde la consola (Windows o Linux):

    Descargamos e instalamos OpenJDK. Una vez finalizada la instalación descargamos  JavaFX-SDK (ver los enlaces para descarga).

    2. Creamos una variable de entorno que contenga la ruta absoluta al directorio lib de JavaFX-SDK. Ejemplo en Linux:

export PATH_TO_FX= '/home/usuario/Descargas/JAVA/openjfx-21.0.8_linux-x64_bin-sdk/javafx-sdk-21.0.8/lib'

3. Creamos un archivo con extensión .java (por ejemplo MyApp.java) y añadimos el código fuente.

4. Si estamos en Windows, desde la consola, utilizamos el comando cd para situamos el el directorio bin de la carpeta OpenJDK (o bien creamos una variable de entorno con la ruta absoluta al directorio) y compilamos el MyApp.java con la orden:

javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml ruta_absoluta_de_MyApp.java

Aplicaciones con conexión a base de datos MySQL

IMPORTANTE: para establecer conexión en una base de datos ubicada en nuestra propia máquina será necesario tener instalado MySQL-server

Una vez instalado el servidor MySQL, será necesario descargar el driver .jar desde la página oficial de MySQL. Conviene tener una copia del archivo mysql-connector-java-9.4.0.jar en el mismo directorio donde realizamos la compilación (en el caso de Windows, sería en el directorio bin de OpenJDK).

Para establecer la conexión a una base de datos instalada en nuestra propia máquina, incluiríamos el siguiente código en la función main de nuestro archivo fuente MyApp.java:

Connection conn = null;

try {
conn =
DriverManager.getConnection("jdbc:mysql://localhost:3306/base_de_datos_a_consultar","mysql_user_name","mysql_user_password");

    // Set all query statements...

} catch (SQLException ex) {
// handle any errors
System.out.println("SQLException: " + ex.getMessage());
System.out.println("SQLState: " + ex.getSQLState());
}

Una vez finalizado el código fuente compilamos con la orden javac. Si se trata de una aplicación JavaFX:

javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml ruta_absoluta_a_MyApp.java

Una vez compilado, para ejecutar la aplicación de manera que se establezca la conexión, debemos incluir el classpath del driver de conexión en la orden de ejecución:

java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -cp .:mysql-connector-java-9.4.0.jar MyApp