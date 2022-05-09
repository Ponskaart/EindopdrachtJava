INSERT INTO User (username, password, role)
SELECT 'Admin', 'VeryGoodPassword', ADMIN
    WHERE NOT EXISTS (SELECT * FROM user WHERE username='Admin');