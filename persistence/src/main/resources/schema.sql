create table if not exists users (
	idPlayer SERIAL PRIMARY KEY,
	username VARCHAR (100),
	password VARCHAR (100),
	email VARCHAR(100)
);

create table if not exists terrain (
	x integer not null,
	y integer not null,
	power integer,
	type integer,
	primary key (x,y),
	CHECK (power >= 0),
	CHECK (type >= 0),
	CHECK (x >= 0),
	CHECK (y >= 0)
);

create table if not exists buildings (
	x integer not null,
	y integer not null,
	type integer,
	idPlayer integer,
	level integer,
	primary key (x,y),
	CHECK (level >= 0),
	CHECK (type >= 0),
	CHECK (x >= 0),
	CHECK (y >= 0),
	FOREIGN KEY (idPlayer) REFERENCES users ON DELETE SET NULL
);

create table if not exists army (
	idArmy SERIAL PRIMARY KEY,
	x integer not null,
	y integer not null,
	idPlayer integer,
	available boolean,
	CHECK (x >= 0), 
	CHECK (y >= 0) 
);