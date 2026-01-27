CREATE TABLE IF NOT EXISTS video (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    platform_name TEXT NOT NULL,
    platform_id TEXT NOT NULL,
    title TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS video_reaction (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    video_id INTEGER NOT NULL,
    user_identity TEXT NOT NULL,
    reaction TEXT NOT NULL,
    timestamp INT NOT NULL,
    date DATETIME NOT NULL,
    CONSTRAINT fk_reaction_video FOREIGN KEY (video_id) REFERENCES video(id) ON DELETE CASCADE
);

CREATE INDEX idx_video_id ON video(platform_name, platform_id);