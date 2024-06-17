use oop_final;

drop table if exists users;

create table users
(
    id            int auto_increment primary key,
    user_name     varchar(16)  not null unique,
    first_name    VARCHAR(32)  not null,
    last_name     varchar(32)  not null,
    password_hash varchar(256) not null,
    image         varchar(256) null,
    about         text null,
    type enum ('admin', 'user') not null
)
    collate = utf8mb4_general_ci;

INSERT INTO oop_final.users (id, user_name, first_name, last_name, password_hash, image, type) VALUES (1, 'tia', 'Tia', 'Mia', 'e99a18c428cb38d5f260853678922e03', 'NO_IMAGE', 'user');
INSERT INTO oop_final.users (id, user_name, first_name, last_name, password_hash, image, type) VALUES (2, 'realtia', 'Tia', 'Alkhazishvili', 'e99a18c428cb38d5f260853678922e03', 'NO_IMAGE', 'user');
INSERT INTO oop_final.users (id, user_name, first_name, last_name, password_hash, image, type) VALUES (3, 'vazzu', 'Vasiko', 'Vazagaevi', 'a141c47927929bc2d1fb6d336a256df4', 'NO_IMAGE', 'user');
INSERT INTO oop_final.users (id, user_name, first_name, last_name, password_hash, image, type) VALUES (4, 'bero', 'Gio', 'Beridze', '7a9470ecb8f55cf9f670f88b0743f9a8', 'NO_IMAGE', 'user');
INSERT INTO oop_final.users (id, user_name, first_name, last_name, password_hash, image, type) VALUES (5, 'elene', 'Elene', 'Kvitsiani', '286876e9fe857d4e8c07c90c2f2de841', 'NO_IMAGE', 'user');
