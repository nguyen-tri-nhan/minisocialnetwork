CREATE DATABASE miniSocialMedia

USE master
go

USE miniSocialMedia
Go

CREATE TABLE tblRoles(
	roleID int NOT NULL PRIMARY KEY ,
	roleName varchar(20),
)

CREATE TABLE tblStatus(
	statusID int NOT NULL PRIMARY KEY,
	statusName varchar(20),
)

CREATE TABLE tblUsers(
	email varchar(50) NOT NULL PRIMARY KEY,
	firstname nvarchar(18) NOT NULL,
	lastname nvarchar(18) NOT NULL,
	password varchar(64) NOT NULL,
	registerdate date,
	roleID int NOT NULL FOREIGN KEY REFERENCES tblRoles(roleID),
	statusID int NOT NULL FOREIGN KEY REFERENCES tblStatus(statusID),
)

CREATE TABLE tblArticles(
	id varchar(20) NOT NULL PRIMARY KEY,
	usermail varchar(50) FOREIGN KEY REFERENCES tblUsers(email),
	title nvarchar(100),
	descripton nvarchar(1000),
	img varchar(100),
	createdate datetime,
	upvote bigint,
	downvote bigint,
	isVisible bit,
)
 
CREATE TABLE tblComment(
	id varchar(20) NOT NULL PRIMARY KEY,
	articleID varchar(20) NOT NULL FOREIGN KEY REFERENCES tblArticles(id),
	usermail varchar(50) NOT NULL FOREIGN KEY REFERENCES tblUsers(email),
	descripton nvarchar(200),
	commentdate datetime,
	isVisible bit,
	isSeen bit,
)

CREATE TABLE tblVote(
	articleID varchar(20) NOT NULL FOREIGN KEY REFERENCES tblArticles(id),
	usermail varchar(50) NOT NULL FOREIGN KEY REFERENCES tblUsers(email),
	vote int,
	votedate datetime,
	isSeen bit,
	PRIMARY KEY(articleID, usermail),
)

CREATE TABLE tblNotification(
	frommail varchar(50) NOT NULL FOREIGN KEY REFERENCES tblUsers(email),
	receivermail varchar(50) NOT NULL FOREIGN KEY REFERENCES tblUsers(email),
	notitype bit,
	notidate datetime,
	isSeen bit,
	articleID varchar(20) NOT NULL FOREIGN KEY REFERENCES tblArticles(id),
)


ALTER TABLE tblArticles ALTER COLUMN createdate DATETIME
ALTER TABLE tblArticles ALTER COLUMN img varchar(1000)
ALTER TABLE tblVote ALTER COLUMN votedate DATETIME
ALTER TABLE tblComment ALTER COLUMN commentdate DATETIME


DROP TABLE tblComment
DROP TABLE tblVote
DROP TABLE tblArticles 
EXEC sp_fkeys 'tblArticles'


INSERT INTO tblRoles VALUES (-1, 'admin')
INSERT INTO tblRoles VALUES (0, 'member')
INSERT INTO tblStatus VALUES (-1, 'old')
INSERT INTO tblStatus VALUES (0, 'new')
INSERT INTO tblUsers(email,firstname,lastname,password,registerdate,roleID,statusID) VALUES ('admin','admin','nhan','1','09/16/2020',-1,-1)
INSERT INTO tblUsers(email,firstname,lastname,password,registerdate,roleID,statusID) VALUES ('admin@abc.cc','admin','nhan','1','09/16/2020',0,0)

INSERT INTO tblArticles(id,usermail,title,descripton,img,createdate,upvote, downvote, isVisible) VALUES ('BETA/0001/0000003','nhan@gmail.com','I will set it layout to be easy to read!','In this scope, new update will bring us another UI, it will be easier to look, you will love it. That is my pleasure','images/0000003.png',GETDATE(),9990, 0, 'True')
--USE master 
--DROP DATABASE miniSocialMedia
SELECT * FROM tblUsers
SELECT * FROM tblArticles
SELECT * FROM tblRoles
SELECT * FROM tblStatus
SELECT * from tblUsers WHERE email = 'nhan@gmail.com' AND password = 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'
UPDATE tblUsers
SET password = '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'
WHERE email = 'admin'
SELECT email, firstname, lastname, registerdate, roleID, statusID FROM tblUsers

SELECT GETDATE();

SELECT * FROM tblArticles WHERE isVisible = 'True' ORDER BY createdate DESC

SELECT id, usermail, title, descripton, img, createdate, upvote,downvote, isVisible, firstname, lastname FROM tblArticles, tblUsers u WHERE isVisible = 'True' AND usermail = email ORDER BY createdate DESC

select * from tblComment

select max(id) from tblArticles

select COUNT(*) from tblArticles WHERE usermail = 'nhan1@gmail.com'