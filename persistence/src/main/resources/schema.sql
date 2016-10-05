drop table terrain;
drop table building;
drop table empire;

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

create table if not exists building (
	x integer not null,
	y integer not null,
	type integer,
	idPlayer integer,
	level integer,
	PRIMARY KEY (x,y),
	CHECK (level >= 0),
	CHECK (type >= 0),
	CHECK (x >= 0),
	CHECK (y >= 0),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

create table if not exists ARMY (
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
	idArmy integer,
	type integer,
	amount integer,
	FOREIGN KEY (idArmy) REFERENCES army ON DELETE CASCADE,
	PRIMARY KEY (idArmy,type)
);

create table if not exists empire (
	idPlayer integer,
	lastUpdate timestamp,
	PRIMARY KEY (idPlayer),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

create table if not exists resource (
	type integer,
	amount integer,
	idPlayer integer,
	PRIMARY KEY (idPlayer, type),
	FOREIGN KEY (idPlayer) REFERENCES userPaw ON DELETE CASCADE
);

