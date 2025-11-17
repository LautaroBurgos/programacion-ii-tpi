CREATE TABLE IF NOT EXISTS envio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN DEFAULT FALSE,
    tracking VARCHAR(50),
    empresa VARCHAR(20),
    tipo VARCHAR(20),
    costo DECIMAL(10,2),
    fecha_despacho DATE,
    fecha_estimada DATE,
    estado VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN DEFAULT FALSE,
    numero VARCHAR(20),
    fecha DATE,
    cliente_nombre VARCHAR(100),
    total DECIMAL(10,2),
    estado VARCHAR(20),
    envio_id BIGINT UNIQUE,
    CONSTRAINT fk_envio FOREIGN KEY (envio_id) REFERENCES envio(id)
);
