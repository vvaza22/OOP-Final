drop table if exists reaction;

create table reaction
(
    reaction_id   int auto_increment,
    anno_id       int                      not null,
    user_id       int                      not null,
    reaction_type enum ('LIKE', 'DISLIKE') not null,
    constraint reaction_pk
        primary key (reaction_id),
    constraint react_anno_id_fk
        foreign key (anno_id) references anno (anno_id),
    constraint react_users_id_fk
        foreign key (user_id) references users (id)
);


