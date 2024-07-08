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

create table Tickets(
                        ticket_id int,
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

create table Asignaciones(
                             assignment_id int,
                             constraint assignment_id_pk primary key (assignment_id),
                             ticket_id int,
                             constraint ticket_id_fk foreign key (ticket_id) references Tickets (ticket_id),
                             operator_id int,
                             constraint operator_id_fk foreign key (operator_id) references Usuarios (user_id),
                             assigned_at datetime -- changed to datetime
);
