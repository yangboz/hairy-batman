DROP TABLE wxfoo IF EXISTS;

CREATE TABLE wxfoo (
    wxfoo_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    code VARCHAR(20),
    store VARCHAR(20),
    manager VARCHAR(20),
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
    moniterTime VARCHAR(20)
);