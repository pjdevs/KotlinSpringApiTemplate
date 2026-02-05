CREATE TABLE IF NOT EXISTS video
(
    id            SERIAL PRIMARY KEY NOT NULL,
    platform_name TEXT               NOT NULL,
    platform_id   TEXT               NOT NULL,
    title         TEXT               NOT NULL,
    duration      BIGINT             NOT NULL
);

CREATE TABLE IF NOT EXISTS video_reaction
(
    id            SERIAL PRIMARY KEY NOT NULL,
    video_id      INTEGER            NOT NULL,
    user_identity TEXT               NOT NULL,
    reaction      TEXT               NOT NULL,
    timestamp     BIGINT             NOT NULL,
    date          TEXT               NOT NULL,
    CONSTRAINT fk_reaction_video FOREIGN KEY (video_id) REFERENCES video (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user
(
    id            SERIAL PRIMARY KEY NOT NULL,
    username      TEXT               NOT NULL UNIQUE,
    password_hash TEXT               NOT NULL,
    roles         TEXT               NOT NULL
);

CREATE INDEX idx_video_id ON video (platform_name, platform_id);