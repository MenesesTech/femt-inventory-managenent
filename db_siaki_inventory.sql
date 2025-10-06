-- Creacion de Base de Datos
DROP DATABASE IF EXISTS db_siaki_inventory;
CREATE DATABASE IF NOT EXISTS db_siaki_inventory;
USE db_siaki_inventory;

-- Creacion de tablas
DROP TABLE IF EXISTS seguridad_usuario,
					 seguridad_rol;
                     
SET default_storage_engine = InnoDB;

/* =========== SEGUDIDAD =========== */
CREATE TABLE seguridad_rol(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(15) NOT NULL
);
CREATE TABLE seguridad_usuario(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    password_hash VARCHAR(128) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    id_rol INT NOT NULL,
    CONSTRAINT seguridad_rol_usuario_fk
    FOREIGN KEY (id_rol) REFERENCES seguridad_rol(id) ON DELETE CASCADE
);

/* =========== PRODUCCION =========== */
CREATE TABLE produccion_proveedor(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre_razon_social VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NULL,
    direccion VARCHAR(50) NULL,
    telefono VARCHAR(9) NULL,
    ruc VARCHAR(10) NULL
);
CREATE TABLE produccion_compra(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    fecha_compra DATE NOT NULL,
    nro_comprobante VARCHAR(10) NULL,
    tipo_comprobante VARCHAR(10) NULL, -- FACTURA, BOLETA
    monto_total DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(20) NOT NULL, -- YAPE, EFECTIVO, TRANSFERENCIA
    fecha_pago DATE NULL,
    estado VARCHAR(12) NOT NULL, -- PENDIENTE, PAGADO
    id_proveedor INT NOT NULL,
    CONSTRAINT produccion_proveedor_compra_fk
    FOREIGN KEY (id_proveedor) REFERENCES produccion_proveedor(id) ON DELETE CASCADE
);
CREATE TABLE produccion_color(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    rgb_color VARCHAR(6) NOT NULL
);
CREATE TABLE produccion_categoria(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) NOT NULL -- NINOS, NINAS
);
CREATE TABLE produccion_tipo(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) NOT NULL -- Ojota, Mapis, Zapatilla
);
CREATE TABLE produccion_talla(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    min_talla INT NOT NULL,
    max_talla INT NOT NULL,
    id_tipo INT NOT NULL,
    CONSTRAINT produccion_tipo_talla_fk
    FOREIGN KEY (id_tipo) REFERENCES produccion_tipo(id) ON DELETE CASCADE
);
CREATE TABLE produccion_pegatina(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) NOT NULL, -- STICH, CAPIBARA, LABUBU
    cantidad INT NOT NULL,
    id_compra INT NOT NULL,
    CONSTRAINT produccion_compra_pegatina_fk
    FOREIGN KEY (id_compra) REFERENCES produccion_compra(id) ON DELETE CASCADE
);
-- Merma que sobra (insumo restante)
CREATE TABLE produccion_merma(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    descripcion VARCHAR(50) NULL,
    peso DECIMAL(10,2) NOT NULL,
    id_compra INT NOT NULL,
    CONSTRAINT produccion_compra_merma_fk
    FOREIGN KEY (id_compra) REFERENCES produccion_compra(id) ON DELETE CASCADE
);
CREATE TABLE produccion_planta_tira(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL, -- por pares
    id_color INT NOT NULL,
    id_categoria INT NOT NULL,
    id_talla INT NOT NULL,
    id_tipo INT NOT NULL,
    id_compra INT NOT NULL,
    CONSTRAINT produccion_color_planta_tira_fk
    FOREIGN KEY (id_color) REFERENCES produccion_color(id) ON DELETE CASCADE,
    CONSTRAINT produccion_categoria_planta_tira_fk
    FOREIGN KEY (id_categoria) REFERENCES produccion_categoria(id) ON DELETE CASCADE,
    CONSTRAINT produccion_talla_planta_tira_fk
    FOREIGN KEY (id_talla) REFERENCES produccion_talla(id) ON DELETE CASCADE,
    CONSTRAINT produccion_tipo_planta_tira_fk
    FOREIGN KEY (id_tipo) REFERENCES produccion_tipo(id) ON DELETE CASCADE,
    CONSTRAINT produccion_compra_planta_tira_fk
    FOREIGN KEY (id_compra) REFERENCES produccion_compra(id) ON DELETE CASCADE
);
CREATE TABLE produccion_solicitud_de_produccion(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    cantidad INT NOT NULL, -- Cantidad de pares, en la interfaz grafica de mi aplciacion se calulara en docenas, pero se tiene que registrar en cantidad de pares
    id_usuario INT NOT NULL,
    id_planta_tira INT NOT NULL,
    id_pegatina INT NOT NULL,
    fecha_solicitud DATE NOT NULL,
    CONSTRAINT seguridad_usuario_sol_de_produccion_fk
    FOREIGN KEY (id_usuario) REFERENCES seguridad_usuario(id) ON DELETE CASCADE,
    CONSTRAINT produccion_planta_tira_sol_de_produccion_fk
    FOREIGN KEY (id_planta_tira) REFERENCES produccion_planta_tira(id) ON DELETE CASCADE,
     CONSTRAINT produccion_pegatina_sol_de_produccion_fk
    FOREIGN KEY (id_pegatina) REFERENCES produccion_pegatina(id) ON DELETE CASCADE
);

/* =========== INVENTARIO =========== */
-- Nos indica que usuario pidio recursos para la construccion de docenas de sandalias cada docena se representa como producto
CREATE TABLE inventario_producto(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL, -- CADA DOCENA VARIA ENTRE 90,100 ó 120
    ubicacion VARCHAR(30) NOT NULL, -- REFID o QR
    id_solicitud_produccion INT NOT NULL,
    fecha_registro DATE NOT NULL,
	CONSTRAINT produccion_sol_de_produccion_inv_producto_fk
    FOREIGN KEY (id_solicitud_produccion) REFERENCES produccion_solicitud_de_produccion(id) ON DELETE CASCADE
);
/* =========== VENTAS =========== */
CREATE TABLE ventas_cliente(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre_razon_social VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NULL,
    direccion VARCHAR(50) NULL,
    telefono VARCHAR(9) NULL,
    ruc VARCHAR(10) NULL,
    dni VARCHAR(8) NULL
);

CREATE TABLE ventas_pedido(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_cliente INT NOT NULL,
    id_producto INT NOT NULL,
    fecha_creada DATE NULL, -- <============= CAMBIAR A NOT  NULL (PARA PRODUCCION)
    fecha_entrega DATE NULL, -- <============= CAMBIAR A NOT  NULL (PARA PRODUCCION)
    id_usuario INT NOT NULL,
    CONSTRAINT ventas_cliente_pedido_fk
    FOREIGN KEY (id_cliente) REFERENCES ventas_cliente(id) ON DELETE CASCADE,
    CONSTRAINT seguridad_usuario_pedido_fk
    FOREIGN KEY (id_usuario) REFERENCES seguridad_usuario(id) ON DELETE CASCADE,
    /** FALTA EL PRODUCTO **/
    CONSTRAINT inventario_producto_pedido_fk
    FOREIGN KEY (id_producto) REFERENCES inventario_producto(id) ON DELETE CASCADE
);

CREATE TABLE ventas_venta(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    igv DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    id_cliente INT NOT NULL,
    id_pedido INT NOT NULL,
    id_usuario INT NOT NULL,
    CONSTRAINT ventas_cliente_venta_fk
    FOREIGN KEY (id_cliente) REFERENCES ventas_cliente(id) ON DELETE CASCADE,
    CONSTRAINT ventas_pedido_venta_fk
    FOREIGN KEY (id_pedido) REFERENCES ventas_pedido(id) ON DELETE CASCADE,
    CONSTRAINT seguridad_usuario_venta_fk
    FOREIGN KEY (id_usuario) REFERENCES seguridad_usuario(id) ON DELETE CASCADE
);

CREATE TABLE ventas_comporbante_pago(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tipo_comprobante VARCHAR(20) NOT NULL, -- FACTURA O BOLETA
    serie VARCHAR(20) NOT NULL,
    fecha_emision DATE NOT NULL,
    estado_sunat VARCHAR(20) NOT NULL, -- ACEPTADO, RECHAZADO, EN_PROCESO
    archivo_xml VARCHAR(100) NOT NULL,  -- Ruta al archivo XML firmado
    archivo_cdr VARCHAR(100) NULL, -- Ruta al archivo CDR de SUNAT
    id_venta INT NOT NULL,
    CONSTRAINT ventas_venta_comprobante_fk
    FOREIGN KEY (id_venta) REFERENCES ventas_venta(id) ON DELETE CASCADE
);

/* =========== INDICES =========== */
CREATE INDEX idx_comp_fecha ON produccion_compra(fecha_compra);
CREATE INDEX idx_prod_cant ON inventario_producto(cantidad);
CREATE INDEX idx_ped_fecha ON ventas_pedido(fecha_creada);
CREATE INDEX idx_venta_id ON ventas_venta(id_pedido);
CREATE INDEX idx_user_name ON seguridad_usuario(username);

/* =========== VISTAS =========== */
-- Muestra productos disponibles con detalles
CREATE VIEW vista_stock_disponible AS
SELECT 
    ip.id AS producto_id,
    pt.nombre AS producto_nombre,
    ip.cantidad AS stock_disponible,
    ip.precio_unitario,
    ip.ubicacion,
    psdp.fecha_solicitud
FROM inventario_producto ip
JOIN produccion_solicitud_de_produccion psdp ON ip.id_solicitud_produccion = psdp.id
JOIN produccion_planta_tira pt ON psdp.id_planta_tira = pt.id
WHERE ip.cantidad > 0;

-- Resumen de ventas por cliente
CREATE VIEW vista_ventas_resumen AS
SELECT 
    vc.nombre_razon_social,
    COUNT(vv.id) AS total_ventas,
    SUM(vv.monto_total) AS total_monto,
    AVG(vv.igv) AS promedio_igv
FROM ventas_cliente vc
LEFT JOIN ventas_pedido vp ON vc.id = vp.id_cliente
LEFT JOIN ventas_venta vv ON vp.id = vv.id_pedido
GROUP BY vc.nombre_razon_social;

-- Compras no pagadas
CREATE VIEW vista_compras_pendientes AS
SELECT 
    pc.id AS compra_id,
    pp.nombre_razon_social,
    pc.monto_total,
    pc.fecha_compra,
    pc.estado
FROM produccion_compra pc
JOIN produccion_proveedor pp ON pc.id_proveedor = pp.id
WHERE pc.estado = 'PENDIENTE';



/**=========== DATOS DE PRUEBA =========**/
/* ==================== DATOS DE PRUEBA ==================== */
/* Seguridad - Roles */
INSERT INTO seguridad_rol (nombre) VALUES
('Administrador'),
('Empleado');

/* Seguridad - Usuarios */
INSERT INTO seguridad_usuario (username, password_hash, nombre, email, telefono, id_rol) VALUES
('menesesfemt', '1234', 'Frey', 'menesesfrey@gmail.com', '916345123', 1),
('cnavarro', '1234', 'Carlos', 'cnavarro@gmail.com', '987654321', 2),
('mlopez', '1234', 'Maria', 'mlopez@gmail.com', '945612378', 1),
('jrodriguez', '1234', 'Juan', 'jrodriguez@gmail.com', '956784123', 2);

/* Proveedores */
INSERT INTO produccion_proveedor (nombre_razon_social, apellido, direccion, telefono, ruc) VALUES
('Textiles SAC', NULL, 'Av. Grau 123', '987654321', '2012345678'),
('Distribuidora Norte', NULL, 'Jr. Los Olivos 456', '912345678', '2056789123'),
('Materiales EIRL', NULL, 'Av. Perú 789', '934567890', '2098765432');

/* Compras */
INSERT INTO produccion_compra (fecha_compra, nro_comprobante, tipo_comprobante, monto_total, metodo_pago, fecha_pago, estado, id_proveedor) VALUES
('2025-08-01', 'F001-0001', 'FACTURA', 1500.00, 'TRANSFERENCIA', '2025-08-02', 'PAGADO', 1),
('2025-08-10', 'B001-0001', 'BOLETA', 800.00, 'EFECTIVO', NULL, 'PENDIENTE', 2),
('2025-08-15', 'F001-0002', 'FACTURA', 2000.00, 'YAPE', '2025-08-16', 'PAGADO', 3);

/* Colores */
INSERT INTO produccion_color (nombre, rgb_color) VALUES
('Rojo', 'FF0000'),
('Azul', '0000FF'),
('Verde', '00FF00'),
('Negro', '000000');

/* Categorías */
INSERT INTO produccion_categoria (nombre) VALUES
('Niños'),
('Niñas'),
('Unisex');

/* Tipos de Calzado */
INSERT INTO produccion_tipo (nombre) VALUES
('Ojota'),
('Mapis'),
('Zapatilla');

/* Tallas */
INSERT INTO produccion_talla (min_talla, max_talla, id_tipo) VALUES
(30, 35, 1), -- Ojota
(28, 34, 2), -- Mapis
(36, 42, 3); -- Zapatilla

/* Pegatinas */
INSERT INTO produccion_pegatina (nombre, cantidad, id_compra) VALUES
('Stich', 100, 1),
('Capibara', 150, 2),
('Labubu', 200, 3);

/* Merma */
INSERT INTO produccion_merma (descripcion, peso, id_compra) VALUES
('Excedente de goma', 5.25, 1),
('Restos de tela', 3.10, 2),
('Cortes sobrantes', 4.50, 3);

/* Planta Tira */
INSERT INTO produccion_planta_tira (nombre, cantidad, id_color, id_categoria, id_talla, id_tipo, id_compra) VALUES
('Ojota Roja Niños', 50, 1, 1, 1, 1, 1),
('Mapis Azul Niñas', 40, 2, 2, 2, 2, 2),
('Zapatilla Negra Unisex', 30, 4, 3, 3, 3, 3);

/* Solicitud de Producción */
INSERT INTO produccion_solicitud_de_produccion (cantidad, id_usuario, id_planta_tira, id_pegatina, fecha_solicitud) VALUES
(120, 1, 1, 1, '2025-09-01'),
(240, 2, 2, 2, '2025-09-02'),
(180, 3, 3, 3, '2025-09-03');

/* Inventario - Producto */
INSERT INTO inventario_producto (cantidad, precio_unitario, ubicacion, id_solicitud_produccion, fecha_registro) VALUES
(10, 90.00, 'QR001', 1, '2025-09-05'),
(15, 100.00, 'QR002', 2, '2025-09-06'),
(20, 120.00, 'QR003', 3, '2025-09-07');

/* Clientes */
INSERT INTO ventas_cliente (nombre_razon_social, apellido, direccion, telefono, ruc, dni) VALUES
('Supermercado Lima', NULL, 'Av. Arequipa 1000', '921234567', '2065432109', NULL),
('Juan Pérez', 'Pérez', 'Jr. Lampa 222', '954321678', NULL, '45678912'),
('Boutique Kids', NULL, 'Av. Universitaria 345', '987612345', '2011122233', NULL);

/* Pedidos */
INSERT INTO ventas_pedido (id_cliente, id_producto, fecha_creada, fecha_entrega, id_usuario) VALUES
(1, 1, '2025-09-08', '2025-09-15', 1),
(2, 2, '2025-09-09', '2025-09-18', 2),
(3, 3, '2025-09-10', '2025-09-20', 3);

/* Ventas */
INSERT INTO ventas_venta (igv, descuento, monto_total, fecha_pago, id_cliente, id_pedido, id_usuario) VALUES
(270.00, 0.00, 1770.00, '2025-09-16', 1, 1, 1),
(144.00, 20.00, 924.00, '2025-09-19', 2, 2, 2),
(216.00, 0.00, 1416.00, '2025-09-21', 3, 3, 3);

/* Comprobantes de Pago */
INSERT INTO ventas_comporbante_pago 
(tipo_comprobante, serie, fecha_emision, estado_sunat, archivo_xml, archivo_cdr, id_venta) VALUES
('FACTURA', 'F001-0001', '2025-09-16', 'ACEPTADO', '/xml/f001-0001.xml', '/cdr/f001-0001.cdr', 1),
('BOLETA', 'B001-0001', '2025-09-19', 'ACEPTADO', '/xml/b001-0001.xml', '/cdr/b001-0001.cdr', 2),
('FACTURA', 'F001-0002', '2025-09-21', 'EN_PROCESO', '/xml/f001-0002.xml', NULL, 3);




