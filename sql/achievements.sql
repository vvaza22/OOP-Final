use oop_final;

drop table if exists achievements;

create table achievements(
    id int auto_increment primary key,
    type int not null,
    user_id int not null,
    constraint user_id_foreignk
        foreign key (user_id) references users (id)
);
