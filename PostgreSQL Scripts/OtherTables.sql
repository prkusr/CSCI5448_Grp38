CREATE TABLE Songs (
	songId SERIAL PRIMARY KEY,
    name	varchar(255),
    format	varchar(255),
    genre	varchar(255),
    releaseDate	Date,
    artistId	int,
    cost	float,
    songPath	text
);

INSERT INTO Songs(name, format, genre, releaseDate, artistId, cost, songPath)
values('Faded', 'mp3', 'Electronic', '2016-05-02', 4, 10, '\electornic\faded_alan_walker.mp3'),
		('Whistle', 'mp3', 'Pop, Hip-hop, rap', '2015-03-02', 4, 8.5, '\electornic\whistle_florida.mp3');

SELECT * FROM Songs;

CREATE TABLE SongsPopularity (
	songId	INT,
    rating FLOAT,
    noOfViews	INT
    
);

INSERT INTO SongsPopularity 
VALUES(1, 4.3, 231093), (2, 4.0, 12100);

CREATE TABLE Playlist (
	playlistId	SERIAL PRIMARY KEY,
    playlistName varchar(255),
    userId	INT,
    creationDate	Date,
    type INT   -- Shared or not
);

CREATE TABLE PlaylistSongsAssoc (
	playlistId INT,
    songsId	INT
);


INSERT INTO Playlist(playlistName, userId, creationDate, type)
VALUES('My Fav', 2, current_date, 0), ('Latest Songs', 2, current_date-2, 0);

INSERT INTO PlaylistSongsAssoc
VALUES(1,1), (2,2);

--DROP TABLE Addresses
CREATE TABLE Addresses (
    addressId SERIAL PRIMARY KEY,
    userId INT REFERENCES Users(userId) ON DELETE CASCADE,
    addressType	INT,				-- Bank/Resiential/Business
	StreetAddr	varchar(255),
    AptNo	varchar(30),
    landmark	varchar(255),
    city	varchar(255),
    state	varchar(255),
    country	varchar(255),
    zip		INT
);

INSERT INTO Addresses(addressType, userId, StreetAddr, AptNo, landmark, city, state, country, zip)
VALUES(2, 2, '2300 Arapahoe Ave', 'Apt 241','','Boulder','CO', 'United States',80302),
	(1, 3, '2343 Arapahoe Ave', 'Suite 2','','Boulder','CO', 'United States',80306);

SELECT * FROM Addresses;

CREATE TABLE PaymentDetails (
	paymentId SERIAL PRIMARY KEY,
    userId INT,
    amount FLOAT,
    paymentDate DATE,
    comments text    
);

CREATE TABLE DirectDepositDetails (
	userId INT REFERENCES Users(userId) ON DELETE CASCADE,
    accountNo	varchar(25),
    routingNo	varchar(25),
    bankName	varchar(255),
    bankAddrId	INT
);

INSERT INTO DirectDepositDetails
VALUES(3,'12874965793','3945628','Wells Fargo',2);

CREATE TABLE CreditCardDetails (
    
	userId INT REFERENCES Users(userId) ON DELETE CASCADE,
	cardNo	varchar(255),
    CVV	varchar(255),
    issuer	varchar(255),
    expiryMonth	smallint,
    expiryYear	smallInt,
    nameOnCard	varchar(255),
    billingAddress	INT
);

INSERT INTO CreditCardDetails
values(2, crypt('123423451627485', gen_salt('md5')), crypt('123', gen_salt('md5')), 'Discover', 12, 2022, 'Vibhor Mishra',1);


