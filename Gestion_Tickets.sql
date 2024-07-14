create database PseudoJira;

USE PseudoJira;

create table Roles(
                      role_id int,
                      constraint role_id_pk primary key (role_id),
                      role_name varchar (50)
);

CREATE TABLE Usuarios (
                          user_id INT PRIMARY KEY IDENTITY,
                          username VARCHAR(50) NOT NULL,
                          password_hash VARCHAR(255) NOT NULL,
                          CONSTRAINT unique_password UNIQUE (password_hash),
                          email VARCHAR(100),
                          CONSTRAINT unique_email UNIQUE (email),
                          role_id INT,
                          CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES Roles (role_id),
                          created_at DATETIME,
                          updated_at DATETIME
);


create table Estados_Tickets(
                                statud_id int,
                                constraint statud_id_pk primary key (statud_id),
                                status_name varchar (50)
);

CREATE TABLE Tickets(
                        ticket_id int IDENTITY(1,1),
                        constraint ticket_id_pk primary key (ticket_id),
                        user_id int,
                        constraint user_id_fk foreign key (user_id) references Usuarios (user_id),
                        title varchar (100),
                        description_ticket text,
                        status_id int,
                        constraint statud_id_fk foreign key (status_id) references Estados_Tickets (statud_id),
                        create_at_ticket datetime, -- changed to datetime
                        updated_at_ticket datetime -- changed to datetime
);


create table Asignaciones (
                              assignment_id int IDENTITY(1,1) PRIMARY KEY,  -- Auto-increment starting from 1
                              ticket_id int,
                              constraint ticket_id_fk foreign key (ticket_id) references Tickets (ticket_id),
                              operator_id int,
                              constraint operator_id_fk foreign key (operator_id) references Usuarios (user_id),
                              assigned_at datetime
);
--Mock data de roles--
INSERT INTO Roles (role_id, role_name) VALUES (1, 'Administrador');
INSERT INTO Roles (role_id, role_name) VALUES (2, 'Operador');
INSERT INTO Roles (role_id, role_name) VALUES (3, 'Cliente');

--estados--
INSERT INTO Estados_Tickets (statud_id, status_name) VALUES (1, 'Abierto');
INSERT INTO Estados_Tickets (statud_id, status_name) VALUES (2, 'En Progreso');
INSERT INTO Estados_Tickets (statud_id, status_name) VALUES (3, 'Resuelto');
INSERT INTO Estados_Tickets (statud_id, status_name) VALUES (4, 'Cerrado');
-- Add more statuses if your workflow requires them
 --usuaruios--
 INSERT INTO Usuarios (username, password_hash, email, role_id, created_at, updated_at)
VALUES ('admin1', 'hashed_password_1', 'admin1@example.com', 1, GETDATE(), GETDATE());

INSERT INTO Usuarios (username, password_hash, email, role_id, created_at, updated_at)
VALUES ('operator1', 'hashed_password_2', 'operator1@example.com', 2, GETDATE(), GETDATE());

INSERT INTO Usuarios (username, password_hash, email, role_id, created_at, updated_at)
VALUES ('operator2', 'hashed_password_3', 'operator2@example.com', 2, GETDATE(), GETDATE());

-- Add more operators as needed...

INSERT INTO Usuarios (username, password_hash, email, role_id, created_at, updated_at)
VALUES ('cliente1', 'hashed_password_4', 'cliente1@example.com', 3, GETDATE(), GETDATE());

INSERT INTO Usuarios (username, password_hash, email, role_id, created_at, updated_at)
VALUES ('cliente2', 'hashed_password_5', 'cliente2@example.com', 3, GETDATE(), GETDATE());

-- Add more clients as needed...

--tickets--
INSERT INTO Tickets (title, description_ticket, user_id, status_id, create_at_ticket, updated_at_ticket)
VALUES ('Problema de conexión a internet', 'No puedo acceder a internet desde mi ordenador.', 4, 1, GETDATE(), GETDATE());

INSERT INTO Tickets (title, description_ticket, user_id, status_id, create_at_ticket, updated_at_ticket)
VALUES ('Error al iniciar sesión', 'No puedo iniciar sesión en la aplicación.', 5, 2, GETDATE(), GETDATE());

-- Add more tickets as needed, varying the user_id and status_id
