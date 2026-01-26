CREATE TABLE video (
    id UUID PRIMARY KEY AUTOINCREMENT,
    platform_name TEXT NOT NULL,
    platform_id TEXT NOT NULL,
    title TEXT NOT NULL
);

CREATE TABLE video_reaction (
    id UUID PRIMARY KEY AUTOINCREMENT,
    video_id UUID NOT NULL,
    reaction TEXT NOT NULL,
    timestamp INT NOT NULL,
    date DATETIME NOT NULL,
    CONSTRAINT fk_reaction_video FOREIGN KEY (video_id) REFERENCES video(id) ON DELETE CASCADE
);

CREATE INDEX idx_video_id ON video(platform_name, platform_id);