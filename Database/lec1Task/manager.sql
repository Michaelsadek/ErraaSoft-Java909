CREATE TABLE Manager (id INT, name VARCHAR(100), age INT, birth_date DATE, address VARCHAR(255));

ALTER TABLE Manager;
DROP COLUMN address;

ALTER TABLE Manager;
ADD city_address VARCHAR(255);
ADD street VARCHAR(255);

CREATE TABLE Owner as SELECT id, full_name as name, birth_date from Manager WHERE 1 = 0;

RENAME TABLE Manager TO Master;

DROP TABLE Owner;
DROP TABLE Master;