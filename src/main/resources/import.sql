INSERT INTO book (id, title, year, image) VALUES (nextval('book_seq'), 'The Great Gatsby', 1925, 'https://example.com/gatsby.jpg');
INSERT INTO book (id, title, year, image) VALUES (nextval('book_seq'), 'To Kill a Mockingbird', 1960, 'https://example.com/mockingbird.jpg');
INSERT INTO book (id, title, year, image) VALUES (nextval('book_seq'), '1984', 1949, 'https://example.com/1984.jpg');

INSERT INTO author (id, name, surname, birth, death) VALUES (nextval('author_seq'), 'F. Scott', 'Fitzgerald', '1896-09-24', '1940-12-21');
INSERT INTO author (id, name, surname, birth, death) VALUES (nextval('author_seq'), 'Harper', 'Lee', '1926-04-28', NULL);
INSERT INTO author (id, name, surname, birth, death) VALUES (nextval('author_seq'), 'George', 'Orwell', '1903-06-25', '1950-01-21');