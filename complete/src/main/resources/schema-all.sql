DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);
DROP TABLE wxfoo IF EXISTS;

CREATE TABLE wxfoo  (
    wxfoo_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    code VARCHAR(20),
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
