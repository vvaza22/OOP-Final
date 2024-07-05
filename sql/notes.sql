use oop_final;

drop table if exists notes;

create table notes(
    id int auto_increment primary key,
    from_id int not null,
    to_id int not null,
    note text not null,
    constraint from_id_frk
        foreign key (from_id) references users (id),
    constraint to_id_frk
        foreign key (to_id) references users (id)
);

INSERT INTO oop_final.notes (from_id, to_id, note) VALUES (1, 2, 'Hello There');
INSERT INTO oop_final.notes (from_id, to_id, note) VALUES (2, 1, 'General Kenobi');
INSERT INTO oop_final.notes (from_id, to_id, note) VALUES (6, 3, 'Have you seen the next episode of Bleach?');
INSERT INTO oop_final.notes (from_id, to_id, note) VALUES (3, 5, 'Ichigo told me to watch Bleach, you should too');

