INSERT INTO video (id, platform_name, platform_id, title)
VALUES
    ('8e12bb03-fd5f-4c89-97a4-620c3d5ee24a', 'youtube', 'dQw4w9WgXcQ', 'Rickroll 4K'),
    ('8e12bb03-fd5f-4c89-97a4-620c3d5ee24b', 'youtube', '9bZkp7q19f0', 'Gangnam Style');

INSERT INTO video_reaction (reaction, timestamp, user_identity, video_id, date)
VALUES
    ('sad', 120, 'unknownUser', '8e12bb03-fd5f-4c89-97a4-620c3d5ee24a', datetime('now'))
    ('cringe', 0, 'pj', '8e12bb03-fd5f-4c89-97a4-620c3d5ee24a', datetime('now'))
    ('fun', 60, 'pj', '8e12bb03-fd5f-4c89-97a4-620c3d5ee24a', datetime('now'))
    ('cringe', 0, 'pj', '8e12bb03-fd5f-4c89-97a4-620c3d5ee24b', datetime('now'));