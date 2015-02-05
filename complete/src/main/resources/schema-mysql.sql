DROP TABLE IF EXISTS `wxfoo`;

CREATE TABLE wxfoo(
    id BigInt( 20 ) AUTO_INCREMENT NOT NULL,
    code VARCHAR(20),
    store VARCHAR(20),
--    manager VARCHAR(20),
    agency VARCHAR(20),
    unit VARCHAR(20),
    onSubscribe VARCHAR(5),
    subscribeId VARCHAR(20),
    articleTime VARCHAR(10),
    articleUrl VARCHAR(255),
    articleTitle VARCHAR(50),
    articleReadNum VARCHAR(10),
    articleLikeNum VARCHAR(10),
    articleLikeRate VARCHAR(5),
    moniterTime VARCHAR(20),
    PRIMARY KEY ( `id` )
);