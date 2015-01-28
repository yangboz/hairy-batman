DROP TABLE wxfoo IF EXISTS;

CREATE TABLE wxfoo  (
    wxfoo_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    code VARCHAR(20),
    store VARCHAR(20),
    manager VARCHAR(20),
    agency VARCHAR(20),
    unit VARCHAR(20),
    onSubscribe VARCHAR(5),
    subscribe VARCHAR(20),
    followSubscribe VARCHAR(10),
    onService VARCHAR(5),
    service VARCHAR(20),
    followService VARCHAR(10)
);
