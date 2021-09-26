CREATE TABLE estado (
    id int,
    estado varchar(255),
    PRIMARY KEY(id)
);

INSERT INTO estado VALUES(1, 'Nuevo');
INSERT INTO estado VALUES(2, 'Usado');
INSERT INTO estado VALUES(3, 'otro');

CREATE TABLE marca (
    id int,
    marca varchar(255),
    PRIMARY KEY(id)
);

INSERT INTO marca VALUES(1, 'HTC');
INSERT INTO marca VALUES(2, 'Huawey');
INSERT INTO marca VALUES(3, 'Apple');
INSERT INTO marca VALUES(4, 'motorola');
INSERT INTO marca VALUES(5, 'Samsung');
INSERT INTO marca VALUES(6, 'LG');
INSERT INTO marca VALUES(7, 'otro');


CREATE TABLE inventario (
    id bigint auto_increment,
    nombre varchar(255),
	marca_id int,
	precio int,
	stock int,
	estado_id int,
	porcentaje_descuento int,	
    PRIMARY KEY(id),
	FOREIGN KEY (marca_id) REFERENCES marca(marca_id),
	FOREIGN KEY (estado_id) REFERENCES estado(estado_id)
);

CREATE TABLE pedido (
    id bigint auto_increment,
	inventario_id int,
	numeroPedido int,	
	nombre varchar(255),	
	marca varchar(255),
	precio int,
	estado varchar(255)
    PRIMARY KEY(id),
	FOREIGN KEY (inventario_id) REFERENCES inventario(id)
);
