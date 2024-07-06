use oop_final;

-- Reset the database
drop table if exists user_answers;
drop table if exists attempts;
drop table if exists choices;
drop table if exists text_answers;
drop table if exists questions;

-- Home Page
drop table if exists anno;

-- Mail
drop table if exists challenges;
drop table if exists notes;
drop table if exists frreqs;

drop table if exists friends;
drop table if exists quiz;
drop table if exists users;


-- Create users table

create table users
(
    id            int auto_increment primary key,
    user_name     varchar(16)  not null unique,
    first_name    VARCHAR(32)  not null,
    last_name     varchar(32)  not null,
    password_hash varchar(256) not null,
    image         varchar(1024) default '/images/profile/default.jpg' null,
    about         text         not null,
    type enum ('admin', 'user') not null
);

-- Create quiz table
create table quiz
(
    quiz_id              int auto_increment,
    name                 nvarchar(256)                      not null,
    author_id            int                                not null,
    description          text                               null,
    quiz_image           text                               null,
    randomize            tinyint(1) default 0               not null,
    practice_mode        tinyint(1) default 0               not null,
    immediate_correction tinyint(1) default 0               not null,
    display_type         enum ('ONE_PAGE', 'MULTIPLE_PAGES') not null,
    create_time          datetime default CURRENT_TIMESTAMP not null,
    constraint quiz_pk
        primary key (quiz_id),
    constraint quiz_pk_2
        unique (quiz_id),
    constraint author_id_fk
        foreign key (author_id) references users (id)
);


-- Create Friends table
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

-- Create Friend Requests table
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

-- Create Notes table
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

-- Create Challenges table
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
   constraint quiz_id_frnk
       foreign key (quiz_id) references quiz (quiz_id)
);


-- Announcements
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


-- Attempts table
create table attempts
(
    attempt_id   int auto_increment,
    quiz_id      int                                not null,
    user_id      int                                not null,
    max_possible int                                not null,
    score        int                                not null,
    attempt_time datetime default CURRENT_TIMESTAMP null,
    constraint attempts_pk
        primary key (attempt_id),
    constraint attempts_pk_2
        unique (attempt_id),
    constraint quiz_id_fk
        foreign key (quiz_id) references quiz (quiz_id),
    constraint user_id_fk
        foreign key (user_id) references users (id)
);

create table user_answers
(
    user_answer_id int auto_increment,
    attempt_id     int                  not null,
    question       text                 not null,
    user_answer    text                 not null,
    correct_answer text                 not null,
    points    int default 0 not null,
    constraint user_answers_pk
        primary key (user_answer_id),
    constraint attempt_id_fk
        foreign key (attempt_id) references attempts (attempt_id)
);



-- EXAMPLE Data

-- Sample Users
INSERT INTO users (user_name, first_name, last_name, password_hash, image, about, type) VALUES ('realtia', 'Tia', 'Alkhazishvili', 'e99a18c428cb38d5f260853678922e03', 'NO_IMAGE', 'My name is Tia and I am a hacker and vigilante.', 'admin');
INSERT INTO users (user_name, first_name, last_name, password_hash, image, about, type) VALUES ('vazzu', 'Vasiko', 'Vazagaevi', 'a141c47927929bc2d1fb6d336a256df4', 'NO_IMAGE', 'Hello Everyone!', 'user');
INSERT INTO users (user_name, first_name, last_name, password_hash, image, about, type) VALUES ('bero', 'Gio', 'Beridze', '7a9470ecb8f55cf9f670f88b0743f9a8', 'NO_IMAGE', 'Hello Everyone!', 'user');
INSERT INTO users (user_name, first_name, last_name, password_hash, image, about, type) VALUES ('elene', 'Elene', 'Kvitsiani', '286876e9fe857d4e8c07c90c2f2de841', 'NO_IMAGE', 'Hello Everyone!', 'user');



-- Example Quiz 1
insert into quiz(
    author_id,
    name,
    description,
    quiz_image,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            2,
            'Random Knowledge Test',
            'This is my amazing quiz that tests your random knowledge in every single field there exists.',
            '/images/sample/1.jpg',
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
    description,
    quiz_image,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            1,
            'Cosmos Quiz',
            'This Quiz tests your knowledge about cool space stuff',
            '/images/sample/2.jpg',
            1,
            0,
            0,
            'ONE_PAGE'
        );

-- Example Quiz 3
insert into quiz(
    author_id,
    name,
    description,
    quiz_image,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            1,
            'Geography Quiz',
            'Do you know maps?',
            '/images/sample/3.jpg',
            1,
            0,
            0,
            'ONE_PAGE'
        );

-- Example Quiz 4
insert into quiz(
    author_id,
    name,
    description,
    quiz_image,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            1,
            'Physics Quiz',
            'Do some multidimensional calculus.',
            '/images/sample/4.jpg',
            1,
            0,
            0,
            'ONE_PAGE'
        );

-- Example Quiz 5
insert into quiz(
    author_id,
    name,
    description,
    quiz_image,
    randomize,
    practice_mode,
    immediate_correction,
    display_type
) values(
            1,
            'Video Games Quiz',
            'Rise and Shine Doctor Freeman Rise and shine...',
            '/images/sample/5.jpg',
            1,
            0,
            0,
            'ONE_PAGE'
        );