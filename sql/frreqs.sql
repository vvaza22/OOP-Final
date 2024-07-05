use oop_final;

drop table if exists frreqs;

create table frreqs
(
    id             int auto_increment primary key,
    from_id int not null,
    to_id   int not null,
    status enum('ACCEPTED', 'REJECTED', 'PENDING') not null,
    constraint from_id_fk
        foreign key (from_id) references users (id),
    constraint to_id_fk
            foreign key (to_id) references users (id)
);

INSERT INTO oop_final.frreqs (from_id, to_id, status) VALUES (1, 2, 'PENDING');
INSERT INTO oop_final.frreqs (from_id, to_id, status) VALUES (4, 5, 'PENDING');
INSERT INTO oop_final.frreqs (from_id, to_id, status) VALUES (3, 2, 'PENDING');
INSERT INTO oop_final.frreqs (from_id, to_id, status) VALUES (2, 3, 'PENDING');
INSERT INTO oop_final.frreqs (from_id, to_id, status) VALUES (2, 6, 'PENDING');
INSERT INTO oop_final.frreqs (from_id, to_id, status) VALUES (3, 6, 'ACCEPTED');