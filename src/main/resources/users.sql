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
    type enum ('admin', 'user') not null
)
    collate = utf8mb4_general_ci;
