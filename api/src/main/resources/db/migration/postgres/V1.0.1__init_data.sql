INSERT INTO video (id, platform_name, platform_id, title)
VALUES (0, 'youtube', 'dQw4w9WgXcQ', 'Rickroll 4K'),
       (1, 'youtube', '9bZkp7q19f0', 'Gangnam Style');

INSERT INTO video_reaction (reaction, timestamp, user_identity, video_id, date)
VALUES ('sad', 120, 'unknown', 0, '2026-01-26T16:00Z'),
       ('cringe', 0, 'pj', 0, '2026-01-26T16:00Z'),
       ('fun', 60, 'pj', 0, '2026-01-26T16:00Z'),
       ('cringe', 0, 'pj', 1, '2026-01-26T16:00Z');