INSERT INTO students (name, SID, password, klass, major) VALUES
    ('hyf', 12112203, '123', 'class5', (SELECT id FROM major WHERE name = 'Computer Science'));

