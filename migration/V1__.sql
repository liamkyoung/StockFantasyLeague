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

CREATE TABLE league_users
(
    league_user_id UUID NOT NULL,
    league_id      UUID,
    user_id        UUID,
    joined_at      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_league_users PRIMARY KEY (league_user_id)
);

CREATE TABLE leagues
(
    league_id  UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    start_time TIMESTAMP WITHOUT TIME ZONE,
    end_time   TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255),
    user_limit INTEGER NOT NULL,
    CONSTRAINT pk_leagues PRIMARY KEY (league_id)
);

CREATE TABLE orders
(
    order_id          UUID NOT NULL,
    client_order_id   VARCHAR(255),
    league_id         UUID,
    user_id           UUID,
    symbol            VARCHAR(255),
    side              VARCHAR(255),
    type              VARCHAR(255),
    limit_price_cents BIGINT,
    state             VARCHAR(255),
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

ALTER TABLE league_users
    ADD CONSTRAINT uc_9c2e5fc5678f20c6f4088b62f UNIQUE (league_id, user_id);

CREATE INDEX idx_orders_user_time ON orders (league_id, user_id, created_at DESC);

ALTER TABLE league_users
    ADD CONSTRAINT FK_LEAGUE_USERS_ON_LEAGUE FOREIGN KEY (league_id) REFERENCES leagues (league_id);