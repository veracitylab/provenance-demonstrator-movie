DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES (
    id INT auto_increment PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    release_year DATE NOT NULL,
    genres VARCHAR(1024),
    poster_url VARCHAR(1024)
);