create database Gestion_Tickets;

USE Gestion_Tickets;

create table Roles(
	role_id int,
    constraint role_id_pk primary key (role_id),
    role_name varchar (50)
);

create table Usuarios(
	user_id int,
    constraint user_id_pk primary key (user_id),
    username varchar (50) not null,
    password_hash varchar (255) not null,
    email varchar (100),
    constraint unique_email unique (email),
    role_id int,
    constraint role_id_fk foreign key (role_id) references Roles (role_id),
    created_at timestamp, -- fecha de creacion de usuario
    updated_at timestamp -- fecha de ultima actualizacion
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
    create_at_ticket timestamp, -- fecha de creacion del ticket
    updated_at_ticket timestamp -- fecha de la ultima actualizacion del ticket
);

create table Asignaciones(
	assignment_id int,
    constraint assignment_id_pk primary key (assignment_id),
    ticket_id int,
    constraint ticket_id_fk foreign key (ticket_id) references Tickets (ticket_id),
    operator_id int,
    constraint operator_id_fk foreign key (operator_id) references Usuarios (user_id),
    assigned_at timestamp -- fecha de asignacion
);