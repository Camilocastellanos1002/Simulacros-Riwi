# Pasos Para Realizar un proyecto Completo en SpringBoot

## * SpringBoot con las 5 dependencias
* ![Dependencias base api Rest](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/22b06758-dab7-4187-9c40-1d25241fbaa6)
## * Agregar dependencias de swagger
	<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.5.0</version>
		</dependency>

## * Agregar dependencias de notificacion por correo

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-mail</artifactId>
	</dependency>

## * Generar Arquitectura hexagonal para el encarpetado
## * ![Arquitectura hexagonal](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/3ac65bdb-46ce-42b2-8109-36ee68db36ca)

## * Flujo basico de una API Rest
* ![Modelo de flujo Spring Boot Api Rest](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/ded5aad1-3fc1-4126-86f9-d2ec252fd21b)

## * Flujo Completo para una API
![Flujo paso a paso](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/e781169d-ead1-45b4-8204-c5d5c505c9e3)

# A continuacion realizamos el paso a paso

## * 1. Descripcion del proyecto

![image](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/d8d80a4d-7be8-4b80-a2d8-5fbe6e18328b)

### 1.1 conexion de la base de datos
#Configuraciones de la base de datos
	
	spring.datasource.url=jdbc:mysql://localhost:3306/bd_01_spring
	spring.datasource.username=root
	spring.datasource.password=
	spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
	
	#Configuraciones de JPA
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.show-sql=true
	
	#Dialecto
	spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

### 1.2 conexion de los path

	#Configuración PathVariable
	server.servlet.context-path=/api/v1

## 2. Crear entidades
![image](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/12585623-9497-4002-9783-648feaf47736)

### 2.1 crear entidades de forma manual
### 2.2 crear relacion entre entidades

## 3. Creacion de dto

![image](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/7c8f033a-96fe-4f7c-997e-495ca49db6e1)

### 3.1 se crean los request con los atributos nesesarios (formulario ), se deben especificar los requisitos de cada atributo

### 3.2 se crean los response, con los mismos atributos de cada entidad

### 3.3 crear los basic response, para no generar bucles

### 3.4 crear los response de las relaciones entre entidades

## 4. Creacion de repositorios

![4 paso](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/45168626-fd11-4879-97eb-483ad85d24a0)

## 5. Creacion de servicios E interfaces

![image](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/5f97e2ee-d02a-44cf-a385-28aa8bcdd6f5)

## 6. Creacion de controladores 

![image](https://github.com/Camilocastellanos1002/Simulacros-Riwi/assets/69378105/cec60007-8651-451f-b539-30297365ffda)




