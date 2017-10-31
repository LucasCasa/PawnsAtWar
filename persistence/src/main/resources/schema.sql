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
	idPlayer integer not null,
	lastUpdate timestamp not null,
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

create table if not exists commerce (
	ownerId integer,
	tradeId SERIAL PRIMARY KEY,
	offerType integer,
	offerAmount integer,
	receiveType integer,
	receiveAmount integer,
	FOREIGN KEY (ownerId) REFERENCES userPaw ON DELETE CASCADE
);

INSERT INTO building
	SELECT x,y,type,idPlayer,0 as level
	FROM terrain t
	WHERE NOT EXISTS (SELECT *
						FROM building b
						WHERE t.x = b.x AND t.y = b.y );

ALTER TABLE resource ADD id SERIAL;
ALTER TABLE resource DROP CONSTRAINT resource_pkey;
ALTER TABLE resource ADD PRIMARY KEY(id);
ALTER TABLE resource ADD UNIQUE(idPlayer,type);

ALTER TABLE troop ADD id SERIAL;
ALTER TABLE troop DROP CONSTRAINT troop_pkey;
ALTER TABLE troop ADD PRIMARY KEY(id);
ALTER TABLE troop ADD UNIQUE(idArmy,type);

ALTER TABLE building ADD id SERIAL;
ALTER TABLE building DROP CONSTRAINT building_pkey;
ALTER TABLE building ADD PRIMARY KEY(id);
ALTER TABLE building ADD UNIQUE(x,y);

DROP TABLE terrain;



