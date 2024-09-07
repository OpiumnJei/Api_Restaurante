CREATE TABLE mesas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad_max_personas INT NOT NULL,
    descripcion_mesa VARCHAR(255) NOT NULL,
    estado_mesa VARCHAR(50) NOT NULL
);