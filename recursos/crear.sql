Crear una nueva base de datos en un Servidor MySQL llamada login:

CREATE DATABASE login;

Añadir una tabla llamada users con 2 campos o columnas la base de datos login:

CREATE TABLE login.users(user_name VARCHAR(50), user_password VARCHAR(50)) ;

Insertar registros en la tabla login.users:

INSERT INTO login.users (user_name, user_password) VALUES ('nombre',,'contraseña', ...)