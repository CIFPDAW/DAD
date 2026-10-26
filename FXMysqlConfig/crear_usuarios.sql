-- Script de creación de base de datos y tabla para FXMysqlConfig
CREATE DATABASE IF NOT EXISTS logins;
USE logins;

CREATE TABLE IF NOT EXISTS users (
    user_name VARCHAR(50) PRIMARY KEY,
    user_password VARCHAR(50) NOT NULL
);

-- Usuarios de prueba iniciales:
INSERT INTO users (user_name, user_password) VALUES
('cesar', 'Cesar1234!'),
('manolo', 'manolito'),
('dan', 'dan1234')
ON DUPLICATE KEY UPDATE user_password = VALUES(user_password);
