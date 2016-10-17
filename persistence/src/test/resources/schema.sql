SET DATABASE SQL SYNTAX PGS TRUE;

create table if not exists userPaw (
	idPlayer SERIAL PRIMARY KEY,
	username VARCHAR (100),
	password VARCHAR (100),
	email VARCHAR(100),
	UNIQUE (username)
);

create table if not exists terrain (
	x integer not null,
	y integer not null,
	power integer,
	type integer,
	idPlayer integer,
	PRIMARY KEY (x,y),
	CHECK (power >= 0),
	CHECK (type >= 0),
	CHECK (x >= 0),
	CHECK (y >= 0),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);



