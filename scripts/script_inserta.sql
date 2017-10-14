USE DatabaseProyecto;

INSERT INTO cuentacorreo (Correo,Contrasena) VALUES ("juanpablovelazquez@gmail.com","hola1234");
INSERT INTO tarjeta (NumeroTarjeta,DigitosReverso,Nip) VALUES (54,576,1884);
INSERT INTO usuario (Username,Contrasena,CorreoId,TarjetaId) VALUES ("JuanPablo56","hola1234",1,1);

INSERT INTO cuentacorreo (Correo,Contrasena) VALUES ("usuario@gmail.com","1234");
INSERT INTO tarjeta (NumeroTarjeta,DigitosReverso,Nip) VALUES ("54367482",576,1884);
INSERT INTO usuario (Username,Contrasena,CorreoId,TarjetaId) VALUES ("usuario","1234",2,2);

INSERT INTO articulo(Nombre,Descripcion,Precio,Imagen) VALUES ("laptop acer","16 gigas de ram",10782.11,"1.png");
INSERT INTO articulo(Nombre,Descripcion,Precio,Imagen) VALUES ("laptop hp","64 gigas de ram",18782.11,"2.png");
INSERT INTO articulo(Nombre,Descripcion,Precio,Imagen) VALUES ("laptop asus","32 gigas de ram",15782.11,"3.png");
INSERT INTO articulo(Nombre,Descripcion,Precio,Imagen) VALUES ("laptop lenovo","32 gigas de ram",25782.11,"4.png");
