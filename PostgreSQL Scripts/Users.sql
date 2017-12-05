-- Database: melody
CREATE EXTENSION pgcrypto;

CREATE TABLE Users (
	userId SERIAL PRIMARY KEY,
    userName varchar(255),
    password text,
    email varchar(255)
)

INSERT INTO Users(userName, password, email)
values('admin', crypt('ooad', gen_salt('md5')), 'admin@admin.com')

SELECT crypt('ooad', gen_salt('md5')),* FROM Users

SELECT * FROM Users WHERE password = crypt('ooad', password)