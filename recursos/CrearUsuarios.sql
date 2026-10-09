CREACIÓN DE UNA BASE DE DATOS Y USUARIO DE APLICACIÓN PARA PRUEBAS EN MYSQL

Una vez instalado MySQL mediante archivo .msi, ejecutamos como administrador una consola CMD y seguimos los siguientes pasos:

Ejecutamos cmd con privilegios de administrador y modificamos la variable de entorno PATH añadiendo la ruta absoluta a la carpeta bin de MySQL (comprobar ruta en cada caso):

SET PATH =%PATH%;C:\Program Files\Mysql\MySQL Server 8.0\bin

Nota: puede ser conveniente guardar el PATH sin modificar: SET RECOVERY_PATH=%PATH%

Una vez realizado el cambio, desde cmd:

    Ejecutar mysql -u root -p (tras presionar Enter nos pedirá el password que introducimos para MySQL durante la instalación)
    Una vez dentro de MySQL, creamos un usuario ejecutamos: CREATE user 'user_name'@'%' identified by 'user_password'; (OJO al ;)
    Creamos la base de datos y la tabla dentro de la misma que usaremos para validar los logins de nuestra aplicación, añadiendo algunos datos ficticios. Para ello, desde el entorno mysql ejecutamos las siguientes órdenes:

- CREATE DATABASE logins;

- CREATE logins.users (user_name VARCHAR(50), user_password VARCHAR(50));

- INSERT INTO logins.users (user_name, user_password) VALUES ('manolo','manolito');

- INSERT INTO logins.users (user_name, user_password) VALUE ('andres','andresito');

-GRANT ALL PRIVILEGES ON logins.* TO 'my_user'@'%';