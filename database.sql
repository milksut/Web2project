-- Populate the User table
INSERT INTO User (id, username, email) VALUES
(1, 'Selcuk Oz', 'selcuk@example.com'),
(2, 'Maria Garcia', 'maria@example.com'),
(3, 'John Doe', 'john@example.com');

-- Populate the Book table
INSERT INTO Book (id, title, cover_image_url, author, language, audio_url, rating, text_content, description, progress)
VALUES
(1, 'Fahrenheit 451', '/images/fahrenheit.png', 'Ray Bradbury', 'English', '/audio/fahrenheit.mp3', 4, 'Full text of Fahrenheit 451', 'A dystopian novel about censorship.', 50),
(2, '1984', '/images/1984.png', 'George Orwell', 'English', '/audio/1984.mp3', 5, 'Full text of 1984', 'A dystopian novel about surveillance and totalitarianism.', 30),
(3, 'Brave New World', '/images/brave_new_world.png', 'Aldous Huxley', 'English', '/audio/brave_new_world.mp3', 4, 'Full text of Brave New World', 'A dystopian novel about a futuristic society.', 70),
(4, 'The Great Gatsby', '/images/gatsby.png', 'F. Scott Fitzgerald', 'English', '/audio/gatsby.mp3', 5, 'Full text of The Great Gatsby', 'A novel about the American dream.', 20),
(5, 'To Kill a Mockingbird', '/images/mockingbird.png', 'Harper Lee', 'English', '/audio/mockingbird.mp3', 4, 'Full text of To Kill a Mockingbird', 'A novel about racial injustice.', 0),
(6, 'The Catcher in the Rye', '/images/catcher.png', 'J.D. Salinger', 'English', '/audio/catcher.mp3', 3, 'Full text of The Catcher in the Rye', 'A novel about teenage rebellion.', 0),
(7, 'Moby Dick', '/images/moby_dick.png', 'Herman Melville', 'English', '/audio/moby_dick.mp3', 4, 'Full text of Moby Dick', 'A novel about the pursuit of a great white whale.', 0);

-- Populate the Translation table
INSERT INTO Translation (id, language, upvotes, downvotes, translated_audio_url, text_content, book_id, translator_name)
VALUES
(1, 'Spanish', 10, 2, '/audio/fahrenheit_es.mp3', 'Texto completo de Fahrenheit 451 en español', 1, 'Carlos Lopez'),
(2, 'French', 8, 1, '/audio/fahrenheit_fr.mp3', 'Texte complet de Fahrenheit 451 en français', 1, 'Jean Dupont'),
(3, 'Spanish', 15, 3, '/audio/1984_es.mp3', 'Texto completo de 1984 en español', 2, 'Ana Martinez'),
(4, 'German', 5, 0, '/audio/1984_de.mp3', 'Vollständiger Text von 1984 auf Deutsch', 2, 'Hans Müller'),
(5, 'Italian', 7, 1, '/audio/brave_new_world_it.mp3', 'Testo completo di Brave New World in italiano', 3, 'Giovanni Rossi');

-- Populate the Comment table
INSERT INTO Comment (id, book_id, user_id, content, timestamp, likes)
VALUES
(1, 1, 2, 'This translation really helped me understand the nuances of the story better.', '2025-05-11 14:30:00', 5),
(2, 1, 3, 'The narration was excellent!', '2025-05-11 15:00:00', 3),
(3, 2, 1, 'A must-read for everyone.', '2025-05-12 10:00:00', 10),
(4, 3, 2, 'A fascinating look at a dystopian future.', '2025-05-12 11:00:00', 8),
(5, 4, 3, 'A timeless classic.', '2025-05-12 12:00:00', 6);

-- Populate the categories for books (using a join table for many-to-many relationships)
INSERT INTO Book_categories (Book_id, categories) VALUES
(1, 'Dystopian'),
(1, 'Science Fiction'),
(2, 'Dystopian'),
(2, 'Political Fiction'),
(3, 'Dystopian'),
(3, 'Science Fiction'),
(4, 'Classic'),
(5, 'Classic'),
(5, 'Historical Fiction'),
(6, 'Classic'),
(6, 'Young Adult'),
(7, 'Adventure'),
(7, 'Classic');
