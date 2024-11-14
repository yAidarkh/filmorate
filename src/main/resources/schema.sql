drop table if exists films cascade;
drop table if exists users cascade;
drop table if exists genres cascade;
drop table if exists likes cascade;
drop table if exists friends cascade;
drop table if exists mpa cascade;
drop table if exists films_genres cascade;

create table mpa (
                     id serial primary key,
                     name varchar(255)
);

create table films (
                       id serial primary key,
                       name varchar(255) not null,
                       description varchar(200) not null,
                       release_date date not null,
                       duration int not null,
                       mpa_id int not null,
                       foreign key (mpa_id) references mpa(id)
);

create table users (
                       id serial primary key,
                       email varchar(255) not null unique,
                       login varchar(255) not null,
                       name varchar(255),
                       birthday date not null
);

create table genres (
                        id serial primary key,
                        name varchar(255)
);

create table likes (
                       film_id int not null,
                       user_id int not null,
                       foreign key (film_id) references films(id),
                       foreign key (user_id) references users(id)
);

create table friends (
                         user_id int not null,
                         friend_id int not null,
                         accepted int not null default (-1),
                         foreign key (user_id) references users(id),
                         foreign key (friend_id) references users(id)
);


create table films_genres (
                              film_id int not null,
                              genre_id int not null,
                              foreign key (film_id) references films(id),
                              foreign key (genre_id) references genres(id)
);
