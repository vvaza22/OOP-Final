use oop_final;

drop table if exists challenges;

create table challenges(
    id int auto_increment primary key,
    from_id int not null,
    to_id int not null,
    quiz_id int not null,
    status enum('PENDING', 'CHL_ACCEPTED', 'CHL_REJECTED'),
    constraint from_id_frnk
        foreign key (from_id) references users (id),
    constraint to_id_frnk
        foreign key (to_id) references users (id),
<<<<<<< HEAD
    constraint quiz_id_fk
=======
    constraint quiz_id_frnkg
>>>>>>> origin/master
        foreign key (quiz_id) references quiz (quiz_id)
);

INSERT INTO oop_final.challenges (from_id, to_id, quiz_id, status) VALUES (1, 2, 2, 'PENDING');
INSERT INTO oop_final.challenges (from_id, to_id, quiz_id, status) VALUES (2, 6, 1, 'PENDING');
INSERT INTO oop_final.challenges (from_id, to_id, quiz_id, status) VALUES (2, 3, 2, 'PENDING');
INSERT INTO oop_final.challenges (from_id, to_id, quiz_id, status) VALUES (1, 2, 1, 'PENDING');
INSERT INTO oop_final.challenges (from_id, to_id, quiz_id, status) VALUES (5, 6, 1, 'PENDING');