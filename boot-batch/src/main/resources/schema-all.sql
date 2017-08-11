DROP TABLE  IF EXISTS people;
CREATE TABLE people  (
    person_id BIGINT NOT NULL AUTO_INCREMENT,
    first_name varchar(20),
    last_name varchar(20),
		PRIMARY KEY (`person_id`)
);
