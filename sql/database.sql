use oop_final;

-- Reset the database
drop table if exists choices;
drop table if exists text_answers;
drop table if exists questions;
drop table if exists quiz;

-- Create quiz table
create table quiz
(
    quiz_id              int auto_increment,
    name                 nvarchar(256)                      not null,
    author_id            int                                not null,
    description          text                               null,
    randomize            tinyint(1) default 0               not null,
    practice_mode        tinyint(1) default 0               not null,
    immediate_correction tinyint(1) default 0               not null,
    display_type         enum ('ONE_PAGE', 'MULTIPLE_PAGES') not null,
    constraint quiz_pk
        primary key (quiz_id),
    constraint quiz_pk_2
        unique (quiz_id),
    constraint author_id_fk
        foreign key (author_id) references users (id)
);

-- Create questions table
create table questions
(
    question_id   int auto_increment,
    quiz_id       int  not null,
    question_text text not null,
    question_type int  not null,
    picture nvarchar(512) null,
    question_order int not null,
    constraint questions_pk
        primary key (question_id),
    constraint questions_pk_2
        unique (question_id),
    constraint questions_quiz_quiz_id_fk
        foreign key (quiz_id) references quiz (quiz_id)
);

-- Create text answers database
create table text_answers
(
    text_answer_id int auto_increment,
    question_id int not null,
    answer_value text not null,
    constraint text_answers_pk
        primary key (text_answer_id),
    constraint text_answers_pk_2
        unique (text_answer_id),
    constraint questions_text_answers_question_id_fk
        foreign key (question_id) references questions (question_id)
);

-- Create choices database
create table choices
(
    choice_id int auto_increment,
    question_id int not null,
    choice_text text not null,
    is_correct tinyint(1) default 0 not null,
    constraint choice_id_pk
        primary key (choice_id),
    constraint choice_id_pk2
        unique (choice_id),
    constraint questions_choices_question_id_fk
        foreign key (question_id) references questions (question_id)
);


-- Example Quiz 1
insert into quiz(
    author_id,
    name,
    description,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            2,
            'Random Knowledge Test',
            'This is my amazing quiz that tests your random knowledge in every single field there exists.',
            1,
            1,
            0,
            'MULTIPLE_PAGES'
        );

-- Question 1
insert into questions(
    quiz_id,
    question_text,
    question_type,
    question_order
) values(
            1,
            'Who is the creator of the C++ Programming Language?',
            1,
            1
        );

-- Possible Answer 1
insert into text_answers(
    question_id,
    answer_value
) values (
             1,
             'Bjarne Stroustrup'
         );

-- Possible Answer 2
insert into text_answers(
    question_id,
    answer_value
) values (
             1,
             'Stroustrup'
         );

-- Question 2
insert into questions(
    quiz_id,
    question_text,
    question_type,
    question_order
) values(
            1,
            'In 2020 {?} created the best heavy metal OST for DOOM Eternal and shocked the world.',
            2,
            3
        );

-- Possible Answer 1
insert into text_answers(
    question_id,
    answer_value
) values (
             2,
             'Mick Gordon'
         );

-- Possible Answer 2
insert into text_answers(
    question_id,
    answer_value
) values (
             2,
             'Mick'
         );

-- Possible Answer 3
insert into text_answers(
    question_id,
    answer_value
) values (
             2,
             'Gordon'
         );

-- Question 3
insert into questions(
    quiz_id,
    question_text,
    question_type,
    question_order
) values(
            1,
            'Mark the correct sentence.',
            3,
            2
        );

-- Choice 1(Correct)
insert into choices(
    question_id,
    choice_text,
    is_correct
) values(
            3,
            'If P=NP, everything in NP is NP-HARD',
            1
        );

-- Choice 2
insert into choices(
    question_id,
    choice_text,
    is_correct
) values(
            3,
            'P != Co-NP',
            0
        );

-- Choice 3
insert into choices(
    question_id,
    choice_text,
    is_correct
) values(
            3,
            'It is possible that P=EXPTIME',
            0
        );

-- Question 4
insert into questions(
    quiz_id,
    question_text,
    question_type,
    picture,
    question_order
) values(
            1,
            'What is depicted in the picture?',
            4,
            'https://upload.wikimedia.org/wikipedia/commons/2/21/Mandel_zoom_00_mandelbrot_set.jpg',
            4
        );

-- Possible Answer 1
insert into text_answers(
    question_id,
    answer_value
) values (
             4,
             'Mandelbrot Set'
         );

-- Example Quiz 2
insert into quiz(
    author_id,
    name,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            1,
            'Theoretical Computer Science',
            1,
            0,
            0,
            'ONE_PAGE'
        );