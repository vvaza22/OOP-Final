use oop_final;

drop table if exists anno;

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

insert into anno(author_id, title, body, likes, dislikes, create_time)
values (1, 'There are now more than 10 quizzes on this website!', 'This is such a huge achievement! I want thank everybody who made the quizzes or whatever! And please do not make anymore quizzes or our server won''t be able to handle that much content and will crash...',
        10, 5, STR_TO_DATE('2077-04-05', '%Y-%m-%d %H:%i:%s'));


insert into anno(author_id, title, body, likes, dislikes, create_time)
values (2, 'Why is nobody making quizzes???', 'Since the website started operating, only one guy has made a quiz and that was one guy was me. C''mon guys, can''t you see the website is very fun and creative. Taking quizzes is such a productive way to waste your finite life.',
        15, 3, STR_TO_DATE('2024-03-12', '%Y-%m-%d %H:%i:%s'));

insert into anno(author_id, title, body, likes, dislikes, create_time)
values (1, 'Welcome to MyCoolQuiz!', 'Welcome. Welcome to MyCoolQuiz. You have chosen or been chosen to take and make quizzes. I thought so much of MyCoolQuiz, that I elected my administration here, in the Tomcat server so thoughfully provided by our benefactors(Apache). I have been proud to call MyCoolQuiz my home. And so, whether you are here to stay, or passing through on your way to parts unknown, welcome to MyCoolQuiz. It''s safer here.',
        7, 9, STR_TO_DATE('2004-01-25', '%Y-%m-%d %H:%i:%s'));
