--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables
drop table if exists account;
drop table if exists movie;

create table account (
    account_id int primary key auto_increment,
    username varchar(255) unique,
    password varchar(255)
);

create table movie(
    movie_id int primary key auto_increment,
    movie_name varchar(255),
    genre varchar(255),
    rating int,
    posted_by int,
    foreign key (posted_by) references  account(account_id)
);