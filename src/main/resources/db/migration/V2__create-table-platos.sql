CREATE TABLE platos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    precio INT NOT NULL,
    cantidad_personas INT NOT NULL,
    categoria_plato VARCHAR(50) NOT NULL
);