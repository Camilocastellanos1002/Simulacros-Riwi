CREATE TABLE avion(
	id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR (30) NOT NULL,
    capacidad INT
);
CREATE TABLE vuelo(
	id INT AUTO_INCREMENT PRIMARY KEY,
    destino VARCHAR (15) NOT NULL,
    fecha_salida DATE NOT NULL,
    hora_salida DATE NOT NULL,
    id_avion INT,
    CONSTRAINT fk_id_avion FOREIGN KEY (id_avion) 
    REFERENCES avion (id) ON DELETE CASCADE
);
CREATE TABLE pasajero(
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR (20) NOT NULL,
    apellidos VARCHAR (20) NOT NULL,
    documento_identidad VARCHAR (10) UNIQUE NOT NULL
);
CREATE TABLE reservacion(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_pasajero INT,
    CONSTRAINT fk_id_pasajero FOREIGN KEY (id_pasajero) REFERENCES pasajero (id) ON DELETE CASCADE,
    id_vuelo INT,
    CONSTRAINT fk_id_vuelo FOREIGN KEY (id_vuelo) REFERENCES vuelo (id) ON DELETE CASCADE,
    fecha_reservacion DATE NOT NULL,
    asiento VARCHAR (10) UNIQUE NOT NULL
);