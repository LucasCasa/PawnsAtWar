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

ALTER TABLE terrain ADD id SERIAL;
ALTER TABLE terrain DROP CONSTRAINT terrain_pkey;
ALTER TABLE terrain ADD PRIMARY KEY(id);
ALTER TABLE terrain ADD UNIQUE(x,y);

DROP TABLE terrain;




