DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES (
    id INT auto_increment PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    release_year DATE NOT NULL,
    genres VARCHAR(1024),
    poster_url VARCHAR(1024)
);

DROP TABLE IF EXISTS PROFILES;

CREATE TABLE PROFILES (
    id INT auto_increment PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS RATINGS;

CREATE TABLE RATINGS (
    movie_id INT,
    profile_id INT,
    rating INT NOT NULL,
    FOREIGN KEY (movie_id) references MOVIES(id),
    FOREIGN KEY (profile_id) references PROFILES(id),
    PRIMARY KEY (movie_id, profile_id)
);
