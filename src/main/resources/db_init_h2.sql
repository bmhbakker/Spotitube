CREATE TABLE tracks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    performer VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    trackType VARCHAR(10) NOT NULL,
    album VARCHAR(100),
    playcount INT,
    publicationDate DATE,
    description TEXT
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255)
);

CREATE TABLE playlists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE playlist_tracks (
    playlist_id INT NOT NULL,
    track_id INT NOT NULL,
    offlineAvailable BOOLEAN NOT NULL,
    PRIMARY KEY (playlist_id, track_id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE
);

-- DATA (zelfde als MySQL, werkt ook in H2)

INSERT INTO tracks (title, performer, duration, trackType, album, playcount, publicationDate, description)
VALUES
    ('Bad Guy', 'Billie Eilish', 194, 'SONG', 'When We All Fall Asleep, Where Do We Go?', NULL, NULL, NULL),
    ('Levitating', 'Dua Lipa', 203, 'SONG', 'Future Nostalgia', NULL, NULL, NULL),
    ('Rolling in the Deep', 'Adele', 228, 'SONG', '21', NULL, NULL, NULL),
    ('Uptown Funk', 'Mark Ronson ft. Bruno Mars', 269, 'SONG', 'Uptown Special', NULL, NULL, NULL),
    ('Happy', 'Pharrell Williams', 233, 'SONG', 'G I R L', NULL, NULL, NULL);

INSERT INTO tracks (title, performer, duration, trackType, album, playcount, publicationDate, description)
VALUES
    ('Bad Guy as video', 'Billie Eilish', 194, 'VIDEO', NULL, 2200000, '2019-03-29', 'Official music video'),
    ('Levitating as video', 'Dua Lipa', 203, 'VIDEO', NULL, 1800000, '2020-03-27', 'Official music video'),
    ('Rolling in the Deep as video', 'Adele', 228, 'VIDEO', NULL, 3100000, '2010-11-29', 'Official music video'),
    ('Uptown Funk as video', 'Mark Ronson ft. Bruno Mars', 269, 'VIDEO', NULL, 2700000, '2014-11-19', 'Official music video'),
    ('Happy as video', 'Pharrell Williams', 233, 'VIDEO', NULL, 3500000, '2013-11-21', 'Official music video');

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
    (1, 1, TRUE),
    (1, 2, FALSE),
    (1, 5, TRUE),
    (1, 6, FALSE),
    (2, 3, FALSE),
    (2, 7, TRUE),
    (2, 2, FALSE),
    (2, 6, TRUE),
    (3, 4, TRUE),
    (3, 1, FALSE),
    (3, 8, TRUE),
    (3, 5, FALSE);