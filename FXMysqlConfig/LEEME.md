# FXMysqlConfig

## Aplicación Login + Registro de Usuarios con Configuración de Parámetros MySQL

Esta aplicación implementa una solución completa en JavaFX con arquitectura modular FXML, control de eventos, persistencia en base de datos MySQL y diálogo modal para configurar los parámetros de conexión dinámicamente.

### Funcionalidades Implementadas

1. **Pantalla de Inicio de Sesión (`FXML.fxml` / `FXMLController.java`)**:
   - Inicio de sesión con validación de credenciales en tiempo real contra la tabla de usuarios en MySQL.
   - Botón **"Acceder"** (o tecla Enter): valida usuario y contraseña.
   - Botón **"Registrarse"**: abre la ventana modal de registro de usuario.
   - Botón **"Configurar BD"**: abre la ventana modal de configuración de la conexión MySQL.

2. **Pantalla de Registro de Usuarios (`Registro.fxml` / `RegistroController.java`)**:
   - Formulario para crear un nuevo usuario con campos:
     - Nombre de usuario (mínimo 3 caracteres).
     - Contraseña.
     - Confirmación de contraseña.
   - Verificación previa de si el usuario ya existe en la base de datos para evitar duplicados.
   - Inserción segura mediante `PreparedStatement` para prevenir inyección SQL.

3. **Configuración de Parámetros de Acceso MySQL (`ConfigBD.fxml` / `ConfigBDController.java`)**:
   - Permite al usuario/administrador ajustar todos los parámetros de conexión a la base de datos:
     - **Ruta del servidor y BD:** ej. `localhost:3306/logins`
     - **Usuario MySQL:** ej. `root`
     - **Contraseña MySQL**
     - **Tabla de usuarios:** ej. `users`
     - **Columna del nombre de usuario:** ej. `user_name`
     - **Columna de la contraseña:** ej. `user_password`
   - Prueba la conexión al pulsar **"Aceptar"** y guarda los datos en [`db_config.txt`](db_config.txt) para que se mantengan entre sesiones.

4. **Gestión de Base de Datos (`MySQL.java`)**:
   - Conexión JDBC con driver `mysql-connector-j-9.7.0.jar`.
   - Métodos parametrizados: `comprobarLogin()`, `existeUsuario()`, `registrarUsuario()`, `probarConexion()`.
   - Carga y guardado automático de parámetros desde archivo de texto.

---

### Cómo Ejecutar

1. **Doble clic (Windows):** Ejecuta el archivo `ejecutar.bat`.
2. **Desde NetBeans:** Abre la carpeta del proyecto `FXMysqlConfig` y pulsa `F6` (Run).
3. **Desde la consola:**
   ```cmd
   java "-Djava.library.path=dist/lib;dist" --module-path "dist/lib" --add-modules javafx.controls,javafx.fxml -jar "dist/FXMysqlConfig.jar"
   ```

---

### Base de Datos de Prueba

Se incluye el script `crear_usuarios.sql` con una base de datos y usuarios de prueba iniciales:
- **Base de datos:** `logins`
- **Tabla:** `users` (columnas `user_name` y `user_password`)
- **Usuarios de prueba:**
  - Usuario: `cesar` | Contraseña: `Cesar1234!`
  - Usuario: `manolo` | Contraseña: `manolito`
  - Usuario: `dan` | Contraseña: `dan1234`
