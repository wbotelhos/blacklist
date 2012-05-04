CREATE SCHEMA if NOT EXISTS r7;

CREATE DATABASE IF NOT EXISTS r7;

USE r7;

DROP TABLE IF EXISTS Blacklist;

CREATE TABLE Blacklist (
	id		bigint(20)		NOT NULL	AUTO_INCREMENT,
	word	varchar(255),

	PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

INSERT INTO Blacklist VALUES (1, 'arma');
INSERT INTO Blacklist VALUES (2, 'bumbum');
INSERT INTO Blacklist VALUES (3, 'BBB');
INSERT INTO Blacklist VALUES (4, 'bunda');
INSERT INTO Blacklist VALUES (5, 'vagabundo');
INSERT INTO Blacklist VALUES (6, 'vagabunda');
INSERT INTO Blacklist VALUES (7, 'safada');

DROP TABLE IF EXISTS Comment;

CREATE TABLE Comment (
	id			bigint(20)		NOT NULL	AUTO_INCREMENT,
	censured	varchar(255),
	original	varchar(255),
	valid		bit(1),
	createdAt	datetime		NOT NULL,
 
	PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO Comment VALUES (1, 'meu coment�rio fofo!', 'meu coment�rio fofo!', 0x01, '2011-01-22 00:00:01');
INSERT INTO Comment VALUES (2, 'coment�rio xxxx para censura!', 'coment�rio armado para censura!', 0x00, '2011-01-22 00:00:02');
INSERT INTO Comment VALUES (3, 'A minha xxxx � para atirar no xxxx dos � toas do xxxx. Tudo xxxx!', 'A minha �rm� � para atirar no bumbum dos � toas do BBB. Tudo armado!', 0x00, '2011-01-22 00:00:03');
