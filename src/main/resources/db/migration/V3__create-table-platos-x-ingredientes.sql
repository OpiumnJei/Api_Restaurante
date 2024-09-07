CREATE TABLE plato_ingredientes (
    plato_id BIGINT NOT NULL,
    ingrediente_id BIGINT NOT NULL,
    PRIMARY KEY (plato_id, ingrediente_id),
    FOREIGN KEY (plato_id) REFERENCES platos(id) ON DELETE CASCADE,
    FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id) ON DELETE CASCADE
);