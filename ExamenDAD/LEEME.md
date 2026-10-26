# Examen UT01 - DAD (Desarrollo de Aplicaciones con Interfaz)

## Red de Talleres de Mecánica Paco S.L. - Sistema de Facturación y Presupuestos

Este proyecto contiene la solución completa de la Prueba Objetiva UT01 conforme a todos los requisitos del enunciado y la rúbrica de evaluación:

### Apartados Desarrollados

1. **Apartado a) (0.5p)**:
   - Se ha retirado la opción "Registrar nuevo usuario" de la pantalla de inicio (`FXML.fxml`). La interfaz de inicio ahora únicamente ofrece inicio de sesión ("Acceder") y configuración de base de datos ("Configurar BD").

2. **Apartado b) (3.0p)**:
   - Se ha implementado en la pantalla de inicio el botón **"Configurar BD"** que abre una ventana modal (`ConfigBD.fxml` y `ConfigBDController.java`).
   - Permite al administrador de MySQL modificar interactivamente el **usuario** y la **contraseña** de conexión.
   - Los parámetros fijados por el enunciado quedan **predeterminados y de sólo lectura**:
     - Base de datos: `localhost:3306/employees`
     - Tabla de empleados: `employees`
     - Campo de usuario: `first_name`
     - Campo de contraseña: `last_name`
   - Los datos se guardan y cargan automáticamente desde `db_config.txt`.

3. **Apartado c) (3.0p)**:
   - Al pulsar "Acceder" o pulsar Enter:
     - Si las credenciales coinciden con un empleado de la tabla `employees`, se cierra la ventana de login y se abre la ventana de facturación/presupuesto (`Report.fxml`) con dimensiones aproximadas de **650 x 775 píxeles** y título **"Report"**.
     - Si las credenciales no son válidas o falla la conexión, se muestra un **diálogo modal** (`Alert`) informando del error específico.

4. **Apartado d) (3.5p)**:
   - En la ventana de facturación interactiva (`ReportController.java`), se han programado los eventos para los 7 servicios básicos mediante un array de números flotantes `double[] precios`:
     - Cambio de aceite (79.54 €)
     - Revisión neumáticos (19.95 €)
     - Sustitución pastillas de freno eje delantero (90.23 €)
     - Sustitución pastillas de freno eje trasero (90.23 €)
     - Lavado de motor (49.45 €)
     - Revisión y cambio de filtros (19.95 €)
     - Comprobación batería (19.95 €)
   - Al hacer check en cualquiera de los checkboxes, se calculan dinámicamente y se actualizan las etiquetas:
     - **“Total servicios de mantemiento”** (subtotal de servicios marcados)
     - **“I.G.I.C.”** (impuesto canario del 7%)
     - **“TOTAL”** (suma de servicios + IGIC)

---

### Datos de Prueba (MySQL)

Se incluye el script `crear_employees.sql` para crear la base de datos y la tabla con empleados de prueba:

```sql
CREATE DATABASE IF NOT EXISTS employees;
USE employees;

CREATE TABLE IF NOT EXISTS employees (
    emp_no INT AUTO_INCREMENT PRIMARY KEY,
    birth_date DATE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender ENUM('M','F') DEFAULT 'M',
    hire_date DATE
);

INSERT INTO employees (first_name, last_name, hire_date) VALUES 
('Georgi', 'Facello', '2020-01-01'),
('Dan', 'Golez', '2022-03-20'),
('Paco', 'Talleres', '2019-05-10');
```

**Credenciales de ejemplo para iniciar sesión:**
- Usuario: `Georgi` | Contraseña: `Facello`
- Usuario: `Dan` | Contraseña: `Golez`
- Usuario: `Paco` | Contraseña: `Talleres`

---

### Archivo para entrega

Conforme a la rúbrica ("Archivo zip con carpeta dist con .jar + carpeta src"):
- Se encuentra generado el archivo **`entrega_examen.zip`** que incluye la carpeta `dist/` (con los ejecutables `.jar` y sus librerías en `dist/lib/`) y la carpeta `src/`.
