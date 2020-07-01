DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
	ID   LONG PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(255)
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
	ID   LONG PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(255)
);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
	ID        LONG PRIMARY KEY AUTO_INCREMENT,
	NAME      VARCHAR(255),
	AUTHOR_ID LONG,
	GENRE_ID  LONG
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
	ID      LONG PRIMARY KEY AUTO_INCREMENT,
	TEXT    VARCHAR(255),
	BOOK_ID LONG
);