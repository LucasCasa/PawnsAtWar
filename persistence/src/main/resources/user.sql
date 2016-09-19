INSERT INTO users VALUES (0,'kuyum','42069','mvega@itba.edu.ar');
INSERT INTO empire VALUES (0,now());
INSERT INTO resources VALUES (5,100,0);
/*create table if not exists resources (
	type integer,
	amount integer,
	idPlayer integer,
	PRIMARY KEY (idPlayer, type),
	FOREIGN KEY (idPlayer) REFERENCES users ON DELETE SET NULL
);*/