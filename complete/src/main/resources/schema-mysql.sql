DROP TABLE IF EXISTS `wxfoo`;

CREATE TABLE wxfoo(
    wxfoo_id BigInt( 20 ) AUTO_INCREMENT NOT NULL,
    id VARCHAR(20),
    code VARCHAR(20),
    store VARCHAR(20),
	manager VARCHAR(20),
    agency VARCHAR(20),
    unit VARCHAR(20),
    onSubscribe VARCHAR(10),
    subscribeId VARCHAR(100),
    onService VARCHAR(10),
    serviceId VARCHAR(100),
    openId VARCHAR(100),
    articleTitle VARCHAR(255),
    articleUrl VARCHAR(255),
    articleTime VARCHAR(20),
    articleReadNum VARCHAR(20),
    articleLikeNum VARCHAR(20),
    articleLikeRate VARCHAR(20),
    moniterTime VARCHAR(20),
--    isValid VARCHAR(1),
-- 	UNIQUE KEY `code` (`code`),
    PRIMARY KEY ( `wxfoo_id` )
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
DROP TABLE IF EXISTS `wxArticle`;
CREATE TABLE wxArticle(
    id BigInt( 20 ) AUTO_INCREMENT NOT NULL,
    openId VARCHAR(100),
    articleTitle VARCHAR(255),
    articleUrl VARCHAR(255),
    articleTime VARCHAR(20),
    articleReadNum INTEGER,
    articleLikeNum INTEGER,
    moniterTime VARCHAR(20),
    PRIMARY KEY ( `id` )
);


