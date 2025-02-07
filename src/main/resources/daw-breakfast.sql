-- CREACION DE TABLAS
DROP DATABASE IF EXISTS daw-breakfast;
CREATE DATABASE daw-breakfast;

USE daw-breakfast;

CREATE TABLE usuario (
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30),
	email VARCHAR(50),
	password VARCHAR(50)
);

CREATE TABLE establecimiento (
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(30),
	descripcion VARCHAR(255),
	ubicacion VARCHAR(255),
	puntuacion DECIMAL(3,2)
);

CREATE TABLE desayuno (
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(30),
	precio DECIMAL(5,2),
	imagen VARCHAR(255),
	puntuacion DECIMAL(3,2),
	id_establecimiento INT,

	CONSTRAINT fk_desayuno_id_establecimiento FOREIGN KEY (id_establecimiento) REFERENCES establecimiento(id)
);

CREATE TABLE review (
	id INT AUTO_INCREMENT,
	fecha DATETIME,
	precio DECIMAL(5,2),
	imagen VARCHAR(255),
	puntuacion INT,
	comentarios TEXT,
	id_usuario INT,
	id_desayuno INT,

	CONSTRAINT pk_review PRIMARY KEY (id, id_usuario, id_desayuno),
	CONSTRAINT fk_review_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	CONSTRAINT fk_review FOREIGN KEY (id_desayuno) REFERENCES desayuno(id)
);

-- INSERTS
INSERT INTO usuario (username, email, password) VALUES 
('juanperez', 'juan.perez@example.com', '1'),
('mariafernandez', 'maria.fernandez@example.com', '12'),
('pedroalvarez', 'pedro.alvarez@example.com', '123'),
('luisa_gomez', 'luisa.gomez@example.com', '1234'),
('carlosmartinez', 'carlos.martinez@example.com', '12345'),
('ana_rodriguez', 'ana.rodriguez@example.com', '123456'),
('luisaramos', 'luisa.ramos@example.com', '1234567'),
('javier_ortiz', 'javier.ortiz@example.com', '12345678'),
('sofia_lopez', 'sofia.lopez@example.com', '123456788'),
('daniel_garcia', 'daniel.garcia@example.com', '1234567890');

INSERT INTO establecimiento (nombre, descripcion, ubicacion, puntuacion) VALUES 
('Café de la Mañana', 'Café tradicional con opciones de desayuno ligero.', 'Madrid, España', 4.20),
('Desayunos & Café', 'Lugar acogedor para comenzar el día con energía.', 'Barcelona, España', 3.90),
('La Taza Dorada', 'Tazas doradas y un ambiente cálido para disfrutar desayunos completos.', 'Valencia, España', 4.50),
('Desayuno Express', 'Desayuno rápido y económico para llevar.', 'Sevilla, España', 3.80),
('Panadería Los Arcos', 'Deliciosas opciones de panadería para desayunar.', 'Bilbao, España', 4.10),
('Desayuno del Sol', 'El desayuno ideal con un toque natural y fresco.', 'Granada, España', 4.60),
('Desayuno Saludable', 'Desayuno saludable con opciones veganas y sin gluten.', 'Madrid, España', 4.30),
('La Olla Feliz', 'Comida casera para un desayuno reconfortante.', 'Murcia, España', 3.95),
('Café Aurora', 'Disfruta un café con opción de desayuno saludable.', 'Málaga, España', 4.40),
('El Buen Desayuno', 'Desayuno completo y sabroso para todas las edades.', 'Palma de Mallorca, España', 4.00);

INSERT INTO desayuno (nombre, precio, imagen, puntuacion, id_establecimiento) VALUES 
('Tostadas con Aguacate', 3.50, 'tostadas_aguacate.jpg', 4.20, 1),
('Café Americano', 1.80, 'cafe_americano.jpg', 4.00, 1),
('Croissant con Mermelada', 2.30, 'croissant_mermelada.jpg', 4.50, 2),
('Bocadillo de Jamón', 3.00, 'bocadillo_jamon.jpg', 3.80, 3),
('Pancakes con Miel', 4.20, 'pancakes_miel.jpg', 4.60, 4),
('Zumo de Naranja Natural', 2.00, 'zumo_naranja.jpg', 4.10, 5),
('Avena con Frutas', 3.00, 'avena_frutas.jpg', 4.30, 6),
('Huevos Revueltos', 3.80, 'huevos_revueltos.jpg', 4.00, 7),
('Sándwich de Queso y Jamón', 2.50, 'sandwich_queso_jamon.jpg', 4.40, 8),
('Smoothie de Fresas', 3.20, 'smoothie_fresas.jpg', 4.10, 9);

INSERT INTO review (fecha, precio, imagen, puntuacion, comentarios, id_usuario, id_desayuno) VALUES 
('2025-01-01 08:00:00', 3.50, 'review_tostadas_aguacate.jpg', 4, 'Muy buena opción, me encantó el aguacate.', 1, 1),
('2025-01-02 09:15:00', 1.80, 'review_cafe_americano.jpg', 5, 'Excelente café, muy buen sabor.', 2, 2),
('2025-01-03 10:30:00', 2.30, 'review_croissant_mermelada.jpg', 4, 'Rico, pero podría tener más mermelada.', 3, 3),
('2025-01-04 07:45:00', 3.00, 'review_bocadillo_jamon.jpg', 3, 'El pan estaba un poco duro, pero el jamón estaba bueno.', 4, 4),
('2025-01-05 08:10:00', 4.20, 'review_pancakes_miel.jpg', 5, 'Increíbles pancakes, con la mejor miel que he probado.', 5, 5),
('2025-01-06 09:00:00', 2.00, 'review_zumo_naranja.jpg', 4, 'Muy fresco, pero podría tener más sabor.', 6, 6),
('2025-01-07 08:25:00', 3.00, 'review_avena_frutas.jpg', 4, 'Deliciosa avena, las frutas frescas son lo mejor.', 7, 7),
('2025-01-08 10:00:00', 3.80, 'review_huevos_revueltos.jpg', 5, 'Los huevos estaban en su punto, con mucho sabor.', 8, 8),
('2025-01-09 07:50:00', 2.50, 'review_sandwich_queso_jamon.jpg', 4, 'Bueno, pero el pan podría estar más crujiente.', 9, 9),
('2025-01-10 09:00:00', 3.20, 'review_smoothie_fresas.jpg', 4, 'Muy refrescante, pero le faltaba un toque más de fresas.', 10, 10);