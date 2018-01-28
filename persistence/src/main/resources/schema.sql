create table if not exists userPaw (
	idPlayer SERIAL PRIMARY KEY,
	username VARCHAR (100),
	password VARCHAR (100),
	email VARCHAR(100),
	UNIQUE (username)
);

create table if not exists building (
	id serial PRIMARY KEY,
	x integer not null,
	y integer not null,
	type integer,
	idPlayer integer,
	level integer,
	UNIQUE (x,y),
	CHECK (level >= 0),
	CHECK (type >= 0),
	CHECK (x >= 0),
	CHECK (y >= 0),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

create table if not exists army (
	idArmy SERIAL PRIMARY KEY,
	x integer not null,
	y integer not null,
	idPlayer integer,
	available boolean,
	CHECK (x >= 0), 
	CHECK (y >= 0), 
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

create table if not exists troop (
	id SERIAL PRIMARY KEY ,
	idArmy integer,
	type integer,
	amount integer,
	FOREIGN KEY (idArmy) REFERENCES army ON DELETE CASCADE,
	UNIQUE (idArmy,type)
);

create table if not exists empire (
	idPlayer integer not null,
	lastUpdate timestamp not null,
	PRIMARY KEY (idPlayer),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

create table if not exists resource (
	id SERIAL PRIMARY KEY,
	type integer,
	amount integer,
	idPlayer integer,
	UNIQUE (idPlayer, type),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

create table if not exists commerce (
	ownerId integer,
	tradeId SERIAL PRIMARY KEY,
	offerType integer,
	offerAmount integer,
	receiveType integer,
	receiveAmount integer,
	FOREIGN KEY (ownerId) REFERENCES userPaw ON DELETE CASCADE
);


