DROP DATABASE DatabaseProyecto;
CREATE DATABASE DatabaseProyecto;
USE DatabaseProyecto;

CREATE TABLE cuentacorreo(
 Id INT      AUTO_INCREMENT  UNIQUE NOT NULL,
 Correo      varchar(255)    NOT NULL       , 
 Contrasena  varchar(255)    NOT NULL       ,
 PRIMARY KEY(Id)	          	
);


CREATE TABLE tarjeta( 
  Id             int(11) UNIQUE NOT NULL AUTO_INCREMENT, 
  NumeroTarjeta  varchar(255) NOT NULL                      ,
  DigitosReverso int(11) NOT NULL                      ,
  Nip            int(11) NOT NULL	               ,
  PRIMARY KEY(Id)
);


CREATE TABLE articulo(
 Id 	     int(11)       UNIQUE NOT NULL AUTO_INCREMENT,
 Nombre      varchar(255)  NOT NULL			 ,
 Descripcion varchar(3000) NOT NULL                      ,
 Precio      float(50,2)   NOT NULL                      ,
 Imagen      varchar(255)  NOT NULL                      ,
 PRIMARY KEY(Id) 
);

CREATE TABLE usuario(
 Id         	int(11)      NOT NULL UNIQUE AUTO_INCREMENT,
 Username   	varchar(255) NOT NULL     		   ,
 Contrasena 	varchar(255) NOT NULL			   ,
 CorreoId       int(11)      NOT NULL    		   ,
 TarjetaId      int(11)      NOT NULL                      ,
 FOREIGN KEY (CorreoId)  REFERENCES cuentacorreo(Id)       
 ON DELETE CASCADE
 ON UPDATE CASCADE
,
 FOREIGN KEY (TarjetaId) REFERENCES tarjeta(Id)		   
 ON DELETE CASCADE
 ON UPDATE CASCADE
,				
 PRIMARY KEY (Id)				
);


CREATE TABLE carrito(
  IdUsuario  int(11) NOT NULL,
  IdArticulo int(11) NOT NULL, 
  FOREIGN KEY (IdUsuario)  REFERENCES usuario(Id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
,
  FOREIGN KEY (IdArticulo) REFERENCES articulo(Id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
 	
);

CREATE TABLE comprado(
  IdUsuario  int(11) NOT NULL,
  IdArticulo int(11) NOT NULL, 
  FOREIGN KEY (IdUsuario)  REFERENCES usuario(Id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
,
  FOREIGN KEY (IdArticulo) REFERENCES articulo(Id) 	
  ON DELETE CASCADE
  ON UPDATE CASCADE
);
