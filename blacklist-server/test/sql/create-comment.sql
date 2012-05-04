/*
-- HSQL
CREATE TABLE Comment (id INTEGER IDENTITY PRIMARY KEY, censured varchar(255), original varchar(255), valid bit(1), createdAt datetime);
*/

-- MySQL
CREATE TABLE if NOT EXISTS Comment (id bigint(20) NOT NULL AUTO_INCREMENT, censured varchar(255), original varchar(255), valid bit(1), createdAt datetime NOT NULL, 
	PRIMARY KEY (id)
);
