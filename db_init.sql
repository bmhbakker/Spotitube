CREATE DATABASE IF NOT EXISTS spotitube;
USE spotitube;

CREATE TABLE IF NOT EXISTS song (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    performer VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    album VARCHAR(255) NOT NULL,
    duration INT NOT NULL,
    CONSTRAINT PK_Id PRIMARY KEY(id),
    CONSTRAINT UN_perf_title UNIQUE(performer, title)
);

CREATE TABLE IF NOT EXISTS video(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    performer VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    publicationDate DATE NOT NULL,
    description LONGTEXT NOT NULL,
    duration INT NOT NULL,
    playcount INT NOT NULL,
    CONSTRAINT PK_Id PRIMARY KEY(id),
    CONSTRAINT UN_perf_title UNIQUE(performer, title)
);

CREATE TABLE IF NOT EXISTS owner(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT PK_Id PRIMARY KEY(id),
    CONSTRAINT UN_user_pass UNIQUE(username, password)
);

CREATE TABLE IF NOT EXISTS playlist(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    owner_id INT NOT NULL,
    CONSTRAINT PK_Id PRIMARY KEY(id),
    CONSTRAINT FK_Owner_Playlist FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS playlist_song (
    playlist_id INT NOT NULL,
    song_id INT NOT NULL,
    offlineAvailable INT NOT NULL, -- 0 false 1 true
    CONSTRAINT PK_Playlist_Song PRIMARY KEY (playlist_id, song_id),
    CONSTRAINT FK_Playlist_Song_Playlist FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
    CONSTRAINT FK_Playlist_Song_Song FOREIGN KEY (song_id) REFERENCES song(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS playlist_video (
    playlist_id INT NOT NULL,
    video_id INT NOT NULL,
    offlineAvailable INT NOT NULL, -- 0 false 1 true
    CONSTRAINT PK_Playlist_Video PRIMARY KEY (playlist_id, video_id),
    CONSTRAINT FK_Playlist_Video_Playlist FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
    CONSTRAINT FK_Playlist_Video_Video FOREIGN KEY (video_id) REFERENCES video(id) ON DELETE CASCADE
);

-- ADD SONGS TO THE DB
INSERT INTO song (title, performer, url, album, duration)
VALUES
    ('Shape of You', 'Ed Sheeran', 'https://example.com/shapeofyou', 'Divide', 240),
    ('Blinding Lights', 'The Weeknd', 'https://example.com/blindinglights', 'After Hours', 200),
    ('Bohemian Rhapsody', 'Queen', 'https://example.com/bohemianrhapsody', 'A Night at the Opera', 355),
    ('Rolling in the Deep', 'Adele', 'https://example.com/rollinginthedeep', '21', 228),
    ('Lose Yourself', 'Eminem', 'https://example.com/loseyourself', '8 Mile', 326);

-- ADD VIDEOS TO THE DB
INSERT INTO video (title, performer, url, publicationDate, description, duration, playCount)
VALUES
    ('Blinding Lights', 'The Weeknd', 'https://example.com/blinding-lights', '2020-03-20', 'Official music video for The Weeknd''s hit song Blinding Lights.', 200, 1),
    ('Shape of You', 'Ed Sheeran', 'https://example.com/shape-of-you', '2017-01-06', 'Music video for Ed Sheeran''s chart-topping single Shape of You.', 233, 2),
    ('Bad Guy', 'Billie Eilish', 'https://example.com/bad-guy', '2019-03-29', 'The music video for Billie Eilish''s popular track Bad Guy.', 194, 3),
    ('Levitating', 'Dua Lipa', 'https://example.com/levitating', '2020-10-01', 'Dua Lipa''s Levitating featuring a futuristic music video.', 203, 4),
    ('Uptown Funk', 'Mark Ronson ft. Bruno Mars', 'https://example.com/uptown-funk', '2014-11-10', 'Mark Ronson and Bruno Mars team up for the energetic Uptown Funk music video.', 270, 6);

-- ADDING OWNERS TO THE DB
INSERT INTO owner (username, password)
    VALUES ('meron', 'MySuperSecretPassword12341'),
           ('john', 'password');

-- ADDING A PLAYLIST WITH AN OWNER TO THE DB
INSERT INTO playlist (name, owner_id)
    VALUES
        ('firstPlaylist', 1),
        ('secondPlaylist', 1),
        ('thirdPlaylist', 2),
        ('fourthPlaylist', 2);

-- ADDING A SONG TO A  PLAYLIST IN THE DB
INSERT INTO playlist_song (playlist_id, song_id, offlineAvailable)
    VALUES
        (1, 1, 0), (1, 2, 0), (2, 3, 1), (2, 5, 0),
        (3, 2, 0), (3, 3, 1), (3, 4, 0), (4, 1, 1), (4, 5, 0);

-- ADDING A VIDEO TO A PLAYLIST IN THE DB
INSERT INTO playlist_video (playlist_id, video_id, offlineAvailable)
    VALUES
        (1, 1, 1), (1, 2, 1), (2, 4, 1), (2, 3, 1),
        (4, 1, 1), (4, 5, 1), (3, 3, 1);

