-- Creación de Base de Datos
DROP DATABASE IF EXISTS db_siaki_inventory;
CREATE DATABASE IF NOT EXISTS db_siaki_inventory;
USE db_siaki_inventory;
SET default_storage_engine = InnoDB;

----------------------------------------------------------
-- MÓDULO 1: DIMENSIONES
----------------------------------------------------------
CREATE TABLE dim_categoria (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) UNIQUE NOT NULL
);
CREATE TABLE dim_modelo (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) UNIQUE NOT NULL
);
CREATE TABLE dim_talla(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre VARCHAR(20) UNIQUE NOT NULL
);
CREATE TABLE dim_color(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre VARCHAR(20) UNIQUE NOT NULL,
    codergb VARCHAR(10) NOT NULL
);
CREATE TABLE dim_tipo_componente(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre VARCHAR(20) UNIQUE NOT NULL
);
----------------------------------------------------------
-- MÓDULO 2: HERRAMIENTAS
----------------------------------------------------------
CREATE TABLE kit_serie_code(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	serie_code VARCHAR(20) NOT NULL,
    serie_letra VARCHAR(1) NOT NULL,
    orden INT NOT NULL
);
CREATE TABLE kit_serie(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_serie_code INT NOT NULL,
    id_modelo INT NOT NULL,
    id_talla INT NOT NULL,
    id_color INT NOT NULL,
    id_categoria INT NOT NULL,
    id_tipo INT NOT NULL,
    
    CONSTRAINT serie_code_kit_serie_fk FOREIGN KEY (id_serie_code) REFERENCES kit_serie_code(id),
    CONSTRAINT modelo_kit_serie_fk FOREIGN KEY (id_modelo) REFERENCES dim_modelo(id),
    CONSTRAINT talla_kit_serie_fk FOREIGN KEY (id_talla) REFERENCES dim_talla(id),
    CONSTRAINT color_kit_serie_fk FOREIGN KEY (id_color) REFERENCES dim_color(id),
    CONSTRAINT categoria_kit_serie_fk FOREIGN KEY (id_categoria) REFERENCES dim_categoria(id),
    CONSTRAINT tipo_kit_serie_fk FOREIGN KEY (id_tipo) REFERENCES dim_tipo_componente(id)
);

----------------------------------------------------------
-- MÓDULO 3: INVENTARIO
----------------------------------------------------------
CREATE TABLE inv_sandalias(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	id_modelo INT NOT NULL,
	id_serie_tira INT NOT NULL,
	id_serie_planta INT NOT NULL,
    id_categoria INT NOT NULL,
    stock_docenas INT NOT NULL,
    UNIQUE KEY uk_sku_sandalias (id_modelo, id_serie_tira, id_serie_planta,id_categoria),
    CONSTRAINT modelo_inv_sandalias_fk FOREIGN KEY (id_modelo) REFERENCES dim_modelo(id) ON DELETE CASCADE,
    CONSTRAINT serie_tira_inv_sandalias_fk FOREIGN KEY (id_serie_tira) REFERENCES kit_serie(id) ON DELETE CASCADE,
    CONSTRAINT serie_planta_inv_sandalias_fk FOREIGN KEY (id_serie_planta) REFERENCES kit_serie(id) ON DELETE CASCADE,
    CONSTRAINT categoria_inv_sandalias_fk FOREIGN KEY (id_categoria) REFERENCES dim_categoria(id) ON DELETE CASCADE
);
----------------------------------------------------------
-- MÓDULO 4: SEGURIDAD
----------------------------------------------------------
CREATE TABLE seguridad_rol (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(15) NOT NULL
);

CREATE TABLE seguridad_usuario (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    auth0_id VARCHAR(128) UNIQUE NOT NULL,
    id_rol INT NOT NULL,
    activo BOOLEAN NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME NOT NULL,
    CONSTRAINT seguridad_rol_usuario_fk FOREIGN KEY (id_rol) REFERENCES seguridad_rol(id) ON DELETE CASCADE
);
----------------------------------------------------------
-- MÓDULO 5: VENTAS
----------------------------------------------------------
CREATE TABLE ventas_cliente (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre_razon_social VARCHAR(30) NOT NULL,
    apellido VARCHAR(30),
    direccion VARCHAR(50),
    telefono VARCHAR(9),
    ruc VARCHAR(10),
    dni VARCHAR(8)
);
CREATE TABLE ventas_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_cliente INT NOT NULL,
    fecha_creada DATE NOT NULL,
    fecha_entrega DATE NOT NULL,
    id_usuario INT NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    id_inventario INT NOT NULL,
    CONSTRAINT ventas_cliente_pedido_fk
        FOREIGN KEY (id_cliente) REFERENCES ventas_cliente(id) ON DELETE CASCADE,
    CONSTRAINT seguridad_usuario_pedido_fk
        FOREIGN KEY (id_usuario) REFERENCES seguridad_usuario(id) ON DELETE CASCADE,
	CONSTRAINT  inv_sandalias_pedido_fk
		FOREIGN KEY (id_inventario) REFERENCES inv_sandalias(id) ON DELETE CASCADE
);
CREATE TABLE ventas_pedido_detalle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    cantidad_docenas INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT ventas_pedido_detalle_fk
        FOREIGN KEY (id_pedido) REFERENCES ventas_pedido(id) ON DELETE CASCADE
);
CREATE TABLE ventas_venta (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    igv DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2),
    monto_total DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    id_cliente INT NOT NULL,
    id_pedido INT NOT NULL,
    id_usuario INT NOT NULL,
    CONSTRAINT ventas_cliente_venta_fk FOREIGN KEY (id_cliente) REFERENCES ventas_cliente(id) ON DELETE CASCADE,
    CONSTRAINT ventas_pedido_venta_fk FOREIGN KEY (id_pedido) REFERENCES ventas_pedido(id) ON DELETE CASCADE,
    CONSTRAINT seguridad_usuario_venta_fk FOREIGN KEY (id_usuario) REFERENCES seguridad_usuario(id) ON DELETE CASCADE
);
CREATE TABLE ventas_comprobante_pago (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tipo_comprobante VARCHAR(20) NOT NULL,
    serie VARCHAR(20) NOT NULL,
    fecha_emision DATE NOT NULL,
    estado_sunat VARCHAR(20) NOT NULL,
    archivo_xml VARCHAR(100) NOT NULL,
    archivo_cdr VARCHAR(100),
    id_venta INT NOT NULL,
    CONSTRAINT ventas_venta_comprobante_fk FOREIGN KEY (id_venta) REFERENCES ventas_venta(id) ON DELETE CASCADE
);

-- INSERT INTO dim_categoria (nombre) VALUES 
-- 	('Niños'),
--     ('Niñas');
-- INSERT INTO dim_modelo (nombre) VALUES 
-- 	('Capellada'),
--     ('Zapatilla'),
--     ('Ojota');
-- INSERT INTO dim_talla (nombre) VALUES
-- 	('31/30'),
--     ('29/28'),
--     ('27/26'),
--     ('25/24'),
--     ('23/22'),
--     ('21/20'),
--     ('19/18');
-- INSERT INTO dim_color (nombre) VALUES
-- 	('Negro'),
--     ('Azul Marino'),
--     ('Azulino'),
--     ('Rojo');
-- INSERT INTO dim_tipo_componente (nombre) VALUES
-- 	('Planta'),
--     ('Tira');

-- INSERT INTO kit_serie_code (serie_code) VALUES
-- 	('OJOTA_COLORES_A'),
--     ('OJOTA_COLORES_B'),
--     ('OJOTA_COLORES_C'),
--     ('OJOTA_COLORES_D');
