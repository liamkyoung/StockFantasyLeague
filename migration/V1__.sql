CREATE SEQUENCE IF NOT EXISTS _user_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE _user
(
    id         INTEGER NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255),
    password   VARCHAR(255),
    is_expired BOOLEAN NOT NULL,
    is_locked  BOOLEAN NOT NULL,
    is_enabled BOOLEAN NOT NULL,
    role       VARCHAR(255),
    CONSTRAINT pk__user PRIMARY KEY (id)
);