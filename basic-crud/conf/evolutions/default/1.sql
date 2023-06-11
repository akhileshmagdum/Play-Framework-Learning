-- !Ups

CREATE TABLE IF NOT EXISTS employees
(
    id          BIGINT NOT NULL,
    full_name   varchar(255) NOT NULL,
    designation varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs
DROP TABLE IF EXISTS employees;
