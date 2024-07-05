use oop_final;

drop table if exists anno;

create table anno
(
    anno_id     int auto_increment,
    author_id   int                                null,
    title       text                               not null,
    body        text                               not null,
    likes       int      default 0                 not null,
    dislikes    int      default 0                 not null,
    create_time DATETIME default current_timestamp not null,
    constraint anno_pk
        primary key (anno_id),
    constraint anno_pk_2
        unique (anno_id),
    constraint anno_users_id_fk
        foreign key (author_id) references users (id)
);

