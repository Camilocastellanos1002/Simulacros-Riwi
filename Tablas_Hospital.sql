-- CREANDO TABLAS --
CREATE TABLE especialidad(
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR (30) NOT NULL,
    descripcion TEXT
);

CREATE TABLE medico(
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR (15) NOT NULL,
    apellidos VARCHAR (20) NOT NULL,
    id_especialidad INT,
    CONSTRAINT fk_id_especialidad FOREIGN KEY (id_especialidad) 
    REFERENCES especialidad (id) ON DELETE CASCADE
);
CREATE TABLE paciente(
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR (20) NOT NULL,
    apellidos VARCHAR (20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    documento_identidad VARCHAR (10) UNIQUE NOT NULL
);
CREATE TABLE cita(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT,
    CONSTRAINT fk_id_paciente FOREIGN KEY (id_paciente) REFERENCES paciente (id) ON DELETE CASCADE,
    id_medico INT,
    CONSTRAINT fk_id_medico FOREIGN KEY (id_medico) REFERENCES medico (id) ON DELETE CASCADE,
    fecha_cita DATE NOT NULL,
    hora_cita TIME NOT NULL,
    motivo VARCHAR (100) NOT NULL
);

#insercion de datos en tablas
INSERT INTO especialidad (nombre,descripcion) VALUES ("Ortopedista", "especialista en arreglo de rodillas"), ("Oncologo", "infantil");
SELECT * FROM especialidad;

INSERT INTO medico (nombre,apellidos,id_especialidad) VALUES ("Cristian","Manco",1),("Manuela","Restrepo",2),("Luisa","Cardenas",1);
SELECT * FROM medico;
DELETE FROM medico WHERE medico.id=3 ;

INSERT INTO paciente (nombre,apellidos,fecha_nacimiento,documento_identidad) 
VALUES ("Juan Camilo","Chaparro Castellanos","1996-02-10","1039466438"),("Luciana","Montoya","2010-09-28","1020405020");
SELECT * FROM paciente; 


