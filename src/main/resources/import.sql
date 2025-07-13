INSERT INTO book (id, title, year, image) VALUES (nextval('book_seq'), 'The Great Gatsby', 1925, 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/The_Great_Gatsby_Cover_1925_Retouched.jpg/960px-The_Great_Gatsby_Cover_1925_Retouched.jpg');
INSERT INTO book (id, title, year, image) VALUES (nextval('book_seq'), 'To Kill a Mockingbird', 1960, 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg/1200px-To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg');
INSERT INTO book (id, title, year, image) VALUES (nextval('book_seq'), '1984', 1949, 'https://www.einaudi.it/content/uploads/2021/01/978885843549HIG.JPG');

INSERT INTO author (id, name, surname, birth, death) VALUES (nextval('author_seq'), 'F. Scott', 'Fitzgerald', '1896-09-24', '1940-12-21');
INSERT INTO author (id, name, surname, birth, death) VALUES (nextval('author_seq'), 'Harper', 'Lee', '1926-04-28', NULL);
INSERT INTO author (id, name, surname, birth, death) VALUES (nextval('author_seq'), 'George', 'Orwell', '1903-06-25', '1950-01-21');