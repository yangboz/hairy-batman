DROP TABLE IF EXISTS `wxfoo`;

CREATE TABLE wxfoo(
    id BigInt( 20 ) AUTO_INCREMENT NOT NULL,
    code VARCHAR(20),
    store VARCHAR(20),
	manager VARCHAR(20),
    agency VARCHAR(20),
    unit VARCHAR(20),
    onSubscribe VARCHAR(5),
    subscribeId VARCHAR(20),
    onService VARCHAR(5),
    serviceId VARCHAR(20),
    openId VARCHAR(100),
    PRIMARY KEY ( `id` )
);

--CREATE TABLE Tmpwxfoo(
--    id BigInt( 20 ) AUTO_INCREMENT NOT NULL,
--    code VARCHAR(20),
--    store VARCHAR(20),
--	manager VARCHAR(20),
--    agency VARCHAR(20),
--    unit VARCHAR(20),
--    onSubscribe VARCHAR(5),
--    subscribeId VARCHAR(20),
--    onService VARCHAR(5),
--    serviceId VARCHAR(20),
--    PRIMARY KEY ( `id` )
--);
--
--CREATE TABLE wxfooArt(
--    id BigInt( 20 ),
--    code VARCHAR(20),
--    articleId VARCHAR(20),
--    PRIMARY KEY ( `id,articleId` )
--);
--
--CREATE TABLE wxArticle(
--    id BigInt( 20 ) AUTO_INCREMENT NOT NULL,
--    articleTitle VARCHAR(50),
--    articleUrl VARCHAR(255),
--    articleTime VARCHAR(10),
--    articleReadNum VARCHAR(10),
--    articleLikeNum VARCHAR(10),
--    moniterTime VARCHAR(20),
--    PRIMARY KEY ( `id` )
--);


