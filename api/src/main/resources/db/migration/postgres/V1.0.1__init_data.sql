INSERT INTO video (id, platform_name, platform_id, title, duration)
VALUES (0, 'youtube', 'dQw4w9WgXcQ', 'Rickroll 4K', 300),
       (1, 'youtube', '9bZkp7q19f0', 'Gangnam Style', 300);

INSERT INTO video_reaction (reaction, timestamp, user_identity, video_id, date)
VALUES ('sad', 120, 'unknown', 0, '2026-01-26T16:00Z'),
       ('cringe', 0, 'pj', 0, '2026-01-26T16:00Z'),
       ('fun', 60, 'pj', 0, '2026-01-26T16:00Z'),
       ('cringe', 0, 'pj', 1, '2026-01-26T16:00Z');

INSERT INTO user (username, password_hash, roles)
VALUES ('user', '$2a$10$W7XHDJoxVOGNKQ8A/W5l9ePJWX.YrCrSNt2t5PrYDDd0uC8K3BAcO', 'user'),
       ('admin', '$2a$10$n1ySFuoNaH6OauaxtiYeYO3KD1yfYNlBI49G9IC/yH7e4iUYQp2wC', 'user,admin');
