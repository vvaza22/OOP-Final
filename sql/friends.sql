use oop_final;

drop table if exists friends;

create table friends
(
    id             int auto_increment primary key,
    friend_A int not null,
    friend_B   int not null,
    constraint from_id_fr
        foreign key (friend_A) references users (id),
    constraint to_id_fr
        foreign key (friend_B) references users (id)
);

INSERT INTO oop_final.friends (friend_A, friend_B) VALUES (1, 2);
INSERT INTO oop_final.friends (friend_A, friend_B) VALUES (4, 5);
INSERT INTO oop_final.friends (friend_A, friend_B) VALUES (3, 2);
INSERT INTO oop_final.friends (friend_A, friend_B) VALUES (2, 3);
INSERT INTO oop_final.friends (friend_A, friend_B) VALUES (2, 6);
INSERT INTO oop_final.friends (friend_A, friend_B) VALUES (3, 6);