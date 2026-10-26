-- Base de datos y datos de prueba para la Prueba Objetiva UT01
-- Base de datos: employees
-- Tabla: employees
-- Campo usuario: first_name
-- Campo contrasea: last_name

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

-- Insercin de empleados de prueba:
-- Para iniciar sesin:
-- Usuario: Georgi | Contrasea: Facello
-- Usuario: Bezalel | Contrasea: Simmel
-- Usuario: Dan | Contrasea: Golez
-- Usuario: Paco | Contrasea: Talleres
INSERT INTO employees (first_name, last_name, hire_date) VALUES
('Georgi', 'Facello', '2020-01-01'),
('Bezalel', 'Simmel', '2021-02-15'),
('Dan', 'Golez', '2022-03-20'),
('Paco', 'Talleres', '2019-05-10');
