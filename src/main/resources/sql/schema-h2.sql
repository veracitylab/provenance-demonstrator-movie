DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES (
    id INT auto_increment PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    release_year DATE NOT NULL,
    genres VARCHAR(1024),
    poster_url VARCHAR(1024)
);

DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (username)
);

DROP TABLE IF EXISTS AUTHORITIES;

CREATE TABLE AUTHORITIES (
    username VARCHAR(250) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES USERS(username)
);

CREATE UNIQUE INDEX ix_auth_email on AUTHORITIES (username, authority);

DROP TABLE IF EXISTS RATINGS;

CREATE TABLE RATINGS (
    movie_id INT,
    user VARCHAR(250),
    rating INT NOT NULL,
    FOREIGN KEY (movie_id) references MOVIES(id),
    FOREIGN KEY (user) references USERS(username),
    PRIMARY KEY (movie_id, user)
);
