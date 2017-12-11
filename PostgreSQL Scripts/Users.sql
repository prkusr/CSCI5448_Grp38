-- Database: melody
--CREATE EXTENSION pgcrypto;

CREATE TABLE Users (
	userId SERIAL PRIMARY KEY,
    userName varchar(255),
    password text,
    email varchar(255),
    cellNo	bigint,
    type	integer
);

INSERT INTO Users(userName, password, email, cellNo, type)
values('admin', crypt('ooad', gen_salt('md5')), 'admin@admin.com', 1234323413, 1),
('Vibhor_usr', crypt('vibhor', gen_salt('md5')), 'vibhor@admin.com', 7203478689, 2),
('Prsr_artist', crypt('prsr', gen_salt('md5')), 'prsr@admin.com',2635428799, 3);

SELECT crypt('ooad', gen_salt('md5')),* FROM Users;

SELECT * FROM Users WHERE password = crypt('ooad', password);