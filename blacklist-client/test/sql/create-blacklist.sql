/*
-- HSQL
CREATE TABLE Blacklist (id INTEGER IDENTITY PRIMARY KEY, word varchar(255));
*/

-- MySQL
CREATE TABLE if NOT EXISTS Blacklist (id bigint(20) NOT NULL AUTO_INCREMENT, word varchar(255),
	PRIMARY KEY (id)
);
