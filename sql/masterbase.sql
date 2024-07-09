use oop_final;

-- Reset the database
drop table if exists user_answers;
drop table if exists attempts;
drop table if exists choices;
drop table if exists text_answers;
drop table if exists questions;
drop table if exists reaction;
drop table if exists anno;
drop table if exists achievements;
drop table if exists challenges;
drop table if exists notes;
drop table if exists frreqs;
drop table if exists friends;
drop table if exists quiz;
drop table if exists achievements;
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
    type enum ('admin', 'user') not null,
    is_deleted    tinyint(1) default 0  not null
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
    is_deleted           tinyint(1) default 0               not null,
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
      is_seen tinyint(1) default 0 not null,
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

-- Reactions
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

-- User Answers Table
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

-- Achievements Table

create table achievements(
                             id int auto_increment primary key,
                             type int not null,
                             user_id int not null,
                             constraint user_id_foreignk
                                 foreign key (user_id) references users (id)
);



-- EXAMPLE Data

-- Sample Users
INSERT INTO users (user_name, first_name, last_name, password_hash, about, type) VALUES ('realtia', 'Tia', 'Alkhazishvili', 'e99a18c428cb38d5f260853678922e03', 'My name is Tia and I am a hacker and vigilante.', 'admin');
INSERT INTO users (user_name, first_name, last_name, password_hash, about, type) VALUES ('vazzu', 'Vasiko', 'Vazagaevi', 'e99a18c428cb38d5f260853678922e03', 'Hello Everyone!', 'user');
INSERT INTO users (user_name, first_name, last_name, password_hash, about, type) VALUES ('bero', 'Gio', 'Beridze', 'e99a18c428cb38d5f260853678922e03', 'Hello Everyone!', 'user');
INSERT INTO users (user_name, first_name, last_name, password_hash, about, type) VALUES ('elene', 'Elene', 'Kvitsiani', 'e99a18c428cb38d5f260853678922e03', 'Hello Everyone!', 'user');

-- Quizzes
INSERT INTO quiz (quiz_id, name, author_id, description, quiz_image, randomize, practice_mode, immediate_correction, display_type, create_time, is_deleted) VALUES (1, 'Random Knowledge Test', 2, 'This is my amazing quiz that tests your random knowledge in every single field there exists.', '/images/sample/1.jpg', 1, 1, 0, 'MULTIPLE_PAGES', '2024-07-09 14:51:01', 0);
INSERT INTO quiz (quiz_id, name, author_id, description, quiz_image, randomize, practice_mode, immediate_correction, display_type, create_time, is_deleted) VALUES (7, 'Cosmos Quiz', 1, 'This is a quiz about cosmos.', '/images/sample/2.jpg', 1, 0, 0, 'ONE_PAGE', '2024-07-09 15:09:08', 0);
INSERT INTO quiz (quiz_id, name, author_id, description, quiz_image, randomize, practice_mode, immediate_correction, display_type, create_time, is_deleted) VALUES (9, 'Geography Quiz', 1, 'geography quiz', '/images/sample/3.jpg', 0, 0, 1, 'MULTIPLE_PAGES', '2024-07-09 15:34:17', 0);
INSERT INTO quiz (quiz_id, name, author_id, description, quiz_image, randomize, practice_mode, immediate_correction, display_type, create_time, is_deleted) VALUES (10, 'Physics Quiz', 1, 'physics quiz', '/images/sample/4.jpg', 0, 1, 0, 'ONE_PAGE', '2024-07-09 15:44:53', 0);
INSERT INTO quiz (quiz_id, name, author_id, description, quiz_image, randomize, practice_mode, immediate_correction, display_type, create_time, is_deleted) VALUES (11, 'Video Games Quiz', 1, 'Rise and Shine Doctor Freeman Rise and shine...', '/images/sample/5.jpg', 1, 0, 0, 'ONE_PAGE', '2024-07-09 16:18:24', 0);

-- Questions
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (1, 1, 'Who is the creator of the C++ Programming Language?', 1, null, 1);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (2, 1, 'In 2020 {?} created the best heavy metal OST for DOOM Eternal and shocked the world.', 2, null, 3);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (3, 1, 'Mark the correct sentence.', 3, null, 2);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (4, 1, 'What is depicted in the picture?', 4, 'https://upload.wikimedia.org/wikipedia/commons/2/21/Mandel_zoom_00_mandelbrot_set.jpg', 4);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (7, 7, 'On which planet do humans live?', 3, null, 1);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (8, 7, 'Which planet is the closest to the sun?', 1, null, 2);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (9, 7, 'The name of our galaxy is {?}', 2, null, 3);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (14, 9, 'What is the capital of Georgia?', 1, null, 1);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (15, 9, 'The capital of France is {?}', 2, null, 2);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (16, 9, 'Which is the smallest country?', 3, null, 3);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (17, 10, 'Who is the author of the theory of relativity?', 1, null, 1);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (18, 10, 'What is the name of smallest particle? ', 3, null, 2);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (19, 11, 'Who published the game Red Dead Redemption? ', 1, null, 1);
INSERT INTO questions (question_id, quiz_id, question_text, question_type, picture, question_order) VALUES (20, 11, 'When was Minecraft released? ', 1, null, 2);


-- Answers
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (1, 1, 'Bjarne Stroustrup');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (2, 1, 'Stroustrup');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (3, 2, 'Mick Gordon');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (4, 2, 'Mick');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (5, 2, 'Gordon');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (6, 4, 'Mandelbrot Set');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (10, 8, 'mercury');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (11, 14, 'tbilisi');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (12, 9, 'milky way');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (13, 9, 'not snickers');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (18, 15, 'paris');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (19, 17, 'albert einstein');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (20, 19, 'Rockstar games');
INSERT INTO text_answers (text_answer_id, question_id, answer_value) VALUES (21, 20, '2011');


-- Choices
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (1, 3, 'If P=NP, everything in NP is NP-HARD', 1);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (2, 3, 'P != Co-NP', 0);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (3, 3, 'It is possible that P=EXPTIME', 0);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (7, 7, 'Venus', 0);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (8, 7, 'Earth', 1);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (9, 7, 'Pluto', 0);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (12, 16, 'Georgia', 0);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (13, 16, 'Vatican', 1);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (14, 16, 'England', 0);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (15, 18, 'Atom', 1);
INSERT INTO choices (choice_id, question_id, choice_text, is_correct) VALUES (16, 18, 'Molecule', 0);


-- Anno 1
insert into anno(author_id, title, body, likes, dislikes, create_time)
values (1, 'There are now more than 10 quizzes on this website!', 'This is such a huge achievement! I want thank everybody who made the quizzes or whatever! And please do not make anymore quizzes or our server won''t be able to handle that much content and will crash...',
        10, 5, STR_TO_DATE('2077-04-05', '%Y-%m-%d %H:%i:%s'));

-- Anno 2
insert into anno(author_id, title, body, likes, dislikes, create_time)
values (2, 'Why is nobody making quizzes???', 'Since the website started operating, only one guy has made a quiz and that was one guy was me. C''mon guys, can''t you see the website is very fun and creative. Taking quizzes is such a productive way to waste your finite life.',
        15, 3, STR_TO_DATE('2024-03-12', '%Y-%m-%d %H:%i:%s'));

-- Anno 3
insert into anno(author_id, title, body, likes, dislikes, create_time)
values (1, 'Welcome to MyCoolQuiz!', 'Welcome. Welcome to MyCoolQuiz. You have chosen or been chosen to take and make quizzes. I thought so much of MyCoolQuiz, that I elected my administration here, in the Tomcat server so thoughfully provided by our benefactors(Apache). I have been proud to call MyCoolQuiz my home. And so, whether you are here to stay, or passing through on your way to parts unknown, welcome to MyCoolQuiz. It''s safer here.',
        7, 9, STR_TO_DATE('2004-01-25', '%Y-%m-%d %H:%i:%s'));









