INDICACIONES PARA EL REGISTRO DE USUARIO EN NUESTRA APLICACIÓN LOGIN:

El registro de nuevo usuario contendrá 3 campos:

    Nombre de usuario
    Contraseña
    E-mail

En nuestra base de datos logins.users tendremos que añadir un campo o columna nueva para registro de e-mails. Desde consola MySQL:

ALTER TABLE logins.users ADD e_mail VARCHAR(50);