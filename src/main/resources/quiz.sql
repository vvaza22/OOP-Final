use oop_final;

drop table if exists quiz;

create table quiz
(
    quiz_id              int auto_increment,
    name                 VARCHAR(256)                       not null,
    randomize            tinyint(1) default 0               not null,
    practice_mode        tinyint(1) default 0               not null,
    immediate_correction tinyint(1) default 0               not null,
    display_type         enum ('ONE_PAGE', 'MULTIPLE_PAGE') not null,
    quiz_type            tinyint                            not null,
    constraint quiz_pk
        primary key (quiz_id),
    constraint quiz_pk_2
        unique (quiz_id)
);

