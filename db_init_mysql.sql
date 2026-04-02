CREATE DATABASE IF NOT EXISTS spotitube;
USE spotitube;

CREATE TABLE IF NOT EXISTS tracks (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR (100) NOT NULL,
    performer VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    trackType ENUM('SONG', 'VIDEO') NOT NULL,
    album VARCHAR(100),
    playcount INT,
    publicationDate DATE,
    description MEDIUMTEXT,
    CONSTRAINT UN_track UNIQUE (title, performer, trackType)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS playlists (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT FK_users_Playlist FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS playlist_tracks (
    playlist_id INT NOT NULL,
    track_id INT NOT NULL,
    offlineAvailable BOOLEAN NOT NULL, -- 0 false 1 true
    PRIMARY KEY (playlist_id, track_id),
    CONSTRAINT FK_playlist_track_playlist FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    CONSTRAINT FK_playlist_track_track FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE
);

-- ADD SONGS TO THE DB
INSERT INTO tracks (title, performer, duration, trackType, album, playcount, publicationDate, description)
VALUES
    ('Fix You', 'Coldplay', 295, 'SONG', 'X&Y', NULL, NULL, NULL),
    ('Shape of You', 'Ed Sheeran', 233, 'SONG', 'Divide', NULL, NULL, NULL),
    ('Blinding Lights', 'The Weeknd', 200, 'SONG', 'After Hours', NULL, NULL, NULL),
    ('Smells Like Teen Spirit', 'Nirvana', 301, 'SONG', 'Nevermind', NULL, NULL, NULL);

-- ADD VIDEOS TO THE DB
INSERT INTO tracks (title, performer, duration, trackType, album, playcount, publicationDate, description)
VALUES
    ('Fix You as video', 'Coldplay', 295, 'VIDEO', NULL, 1200000, '2005-09-05', 'Official music video'),
    ('Shape of You as video', 'Ed Sheeran', 233, 'VIDEO', NULL, 3500000, '2017-01-06', 'Official music video'),
    ('Blinding Lights as video', 'The Weeknd', 200, 'VIDEO', NULL, 4200000, '2019-11-29', 'Official music video'),
    ('Smells Like Teen Spirit as video', 'Nirvana', 301, 'VIDEO', NULL, 5000000, '1991-09-10', 'Official music video');

-- ADDING USERS TO THE DB
INSERT INTO users (username, password)
    VALUES
        ('meron', 'MySuperSecretPassword12341'),
        ('john', 'password');

INSERT INTO playlists (name, user_id)
VALUES
    ('Favorites', 1),
    ('Workout Mix', 1),
    ('Chill Vibes', 2);

INSERT INTO playlist_tracks (playlist_id, track_id, offlineAvailable)
VALUES
-- Favorites (playlist 1)
(1, 1, 1),  -- song
(1, 2, 0),  -- song
(1, 5, 1),  -- video
(1, 6, 0),  -- video

-- Workout Mix (playlist 2)
(2, 3, 0),  -- song
(2, 7, 1),  -- video
(2, 2, 0),  -- song
(2, 6, 1),  -- video

-- Chill Vibes (playlist 3)
(3, 4, 1),  -- song
(3, 1, 0),  -- song
(3, 8, 1),  -- video
(3, 5, 0);  -- video









