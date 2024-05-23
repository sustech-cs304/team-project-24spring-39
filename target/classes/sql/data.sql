-- department
INSERT INTO department (id, name) VALUES (1, 'Computer Science and Technology');
INSERT INTO department (id, name) VALUES (2, 'Electrical Engineering');
INSERT INTO department (id, name) VALUES (3, 'Mechanical Engineering');
INSERT INTO department (id, name) VALUES (4, 'Chemical Engineering');
INSERT INTO department (id, name) VALUES (5, 'Mathematics');
-- major
INSERT INTO major (id, name, department) VALUES (1, 'Computer Science and Technology', 'Computer Science and Technology');
INSERT INTO major (id, name, department) VALUES (2, 'Software Engineering', 'Computer Science and Technology');
INSERT INTO major (id, name, department) VALUES (3, 'Communication Engineering', 'Electrical Engineering');
INSERT INTO major (id, name, department) VALUES (4, 'Mechanical Design, Manufacturing and Automation', 'Mechanical Engineering');
INSERT INTO major (id, name, department) VALUES (5, 'Chemical Engineering and Technology', 'Chemical Engineering');
-- professor
INSERT INTO professor (id, PID, name, department) VALUES (1, '12345678', 'Zhang San', 'Computer Science and Technology');
INSERT INTO professor (id, PID, name, department) VALUES (2, '87654321', 'Li Si', 'Electrical Engineering');
INSERT INTO professor (id, PID, name, department) VALUES (3, '23456789', 'Wang Wu', 'Mechanical Engineering');
INSERT INTO professor (id, PID, name, department) VALUES (4, '98765432', 'Zhao Liu', 'Chemical Engineering');
INSERT INTO professor (id, PID, name, department) VALUES (5, '45678912', 'Sun Qi', 'Mathematics');
-- admin
INSERT INTO admin (id, account, password) VALUES (1, 'admin1', 'password1');
INSERT INTO admin (id, account, password) VALUES (2, 'admin2', 'password2');
INSERT INTO admin (id, account, password) VALUES (3, 'admin3', 'password3');
INSERT INTO admin (id, account, password) VALUES (4, 'admin4', 'password4');
INSERT INTO admin (id, account, password) VALUES (5, 'admin5', 'password5');
-- student
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (1, 'Student 1', '12345678', 'password1', 'Class 1', 'Computer Science and Technology', 90);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (2, 'Student 2', '87654321', 'password2', 'Class 2', 'Software Engineering', 80);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (3, 'Student 3', '23456789', 'password3', 'Class 3', 'Communication Engineering', 85);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (4, 'Student 4', '98765432', 'password4', 'Class 4', 'Mechanical Design, Manufacturing and Automation', 95);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (5, 'Student 5', '45678912', 'password5', 'Class 5', 'Chemical Engineering and Technology', 75);
-- course
INSERT INTO course (id, name, CID, semester, type, department, credit, hours, capacity, selected, location, description, time) VALUES (1, 'Data Structures', 'CS001', 'Spring Semester', '专业必修课', 'Computer Science and Technology', 4, 64, 100, 0, 'Building A101', 'Data Structures course description', '{"days": ["Monday", "Wednesday"], "times": ["08:00", "10:00"]}');
INSERT INTO course (id, name, CID, semester, type, department, credit, hours, capacity, selected, location, description, time) VALUES (2, 'University Physics', 'PH001', 'Fall Semester', '专业必修课', 'Electrical Engineering', 3, 48, 150, 0, 'Building B201', 'University Physics course description', '{"days": ["Tuesday", "Thursday"], "times": ["14:00", "16:00"]}');
INSERT INTO course (id, name, CID, semester, type, department, credit, hours, capacity, selected, location, description, time) VALUES (4, 'Organic Chemistry', 'CH001', 'Fall Semester', '专业必修课', 'Chemical Engineering', 4, 64, 100, 0, 'Building C201', 'Organic Chemistry basic course, covering the basic concepts and reaction mechanisms of organic compounds', '{"days": ["Tuesday", "Thursday"], "times": ["13:00", "15:00"]}');
-- course_student
INSERT INTO course_student (id, course_id, student_id, score, valid) VALUES (1, 'CS001', '12345678', 0, false);
INSERT INTO course_student (id, course_id, student_id, score, valid) VALUES (2, 'PH001', '87654321', 0, false);
INSERT INTO course_student (id, course_id, student_id, score, valid) VALUES (3, 'CH001', '23456789', 0, false);
-- course_professor
INSERT INTO course_professor (id, course_id, professor_id) VALUES (1, 'CS001', '12345678');
INSERT INTO course_professor (id, course_id, professor_id) VALUES (2, 'PH001', '87654321');
INSERT INTO course_professor (id, course_id, professor_id) VALUES (3, 'CH001', '98765432');
-- rate
INSERT INTO rate (id, course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES (1, 'CS001', '12345678', 4, 'normal', 'normal', 'lenient', 'high', 'Great course, learned a lot');
INSERT INTO rate (id, course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES (2, 'PH001', '87654321', 3, 'hard', 'heavy', 'strict', 'low', 'Course is difficult, but the professor is strict');
INSERT INTO rate (id, course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES (3, 'CH001', '23456789', 5, 'easy', 'light', 'lenient', 'high', 'Very easy course, recommended');
-- room
INSERT INTO room (id, name, capacity) VALUES (1, 'Meeting Room 1', 10);
INSERT INTO room (id, name, capacity) VALUES (2, 'Meeting Room 2', 20);
INSERT INTO room (id, name, capacity) VALUES (3, 'Library Study Room', 30);
-- reservation
INSERT INTO reservation (id, student_id, room, time, purpose) VALUES (1, '12345678', 'Meeting Room 1', '{"date": "2023-04-01", "start": "09:00", "end": "12:00"}', 'Group Discussion');
INSERT INTO reservation (id, student_id, room, time, purpose) VALUES (2, '87654321', 'Meeting Room 2', '{"date": "2023-04-02", "start": "14:00", "end": "16:00"}', 'Study Group');
INSERT INTO reservation (id, student_id, room, time, purpose) VALUES (3, '23456789', 'Library Study Room', '{"date": "2023-04-03", "start": "10:00", "end": "13:00"}', 'Project Meeting');
-- file
INSERT INTO file (id, name, filetype, filepath, uploader_id) VALUES (1, 'report.pdf', 'document', '/uploads/12345678/report.pdf', '12345678');
INSERT INTO file (id, name, filetype, filepath, uploader_id) VALUES (2, 'photo.jpg', 'image', '/uploads/87654321/photo.jpg', '87654321');
INSERT INTO file (id, name, filetype, filepath, uploader_id) VALUES (3, 'presentation.mp4', 'video', '/uploads/23456789/presentation.mp4', '23456789');
-- post
INSERT INTO post (id, author_id, title, content, file_id, major_category, course_category) VALUES (1, '12345678', 'Data Structures Question', 'I am not quite understanding the tree part, can someone explain it to me?', null, 'Computer Science and Technology', 'CS001');
INSERT INTO post (id, author_id, title, content, file_id, major_category, course_category) VALUES (2, '87654321', 'Physics Lab Help', 'I am having problems in the lab, need some help', null, 'Electrical Engineering', 'PH001');
INSERT INTO post (id, author_id, title, content, file_id, major_category, course_category) VALUES (3, '23234569', 'Chemistry Course Inquiry', 'I have a few questions about this chemical reaction', null, 'Chemical Engineering', 'CH001');
-- reply
INSERT INTO reply (id, post_id, author_id, content, file_id) VALUES (1, 1, '87654321', 'The tree part is actually quite simple, you can understand it like this...', null);
INSERT INTO reply (id, post_id, author_id, content, file_id) VALUES (2, 2, '12345678', 'I have the same problem, hope someone can answer', null);
-- secondary_reply
INSERT INTO secondary_reply (id, reply_id, author_id, content, file_id) VALUES (1, 1, '23456789', 'Thanks for the explanation, I get it now', null);
INSERT INTO secondary_reply (id, reply_id, author_id, content, file_id) VALUES (2, 2, '45678912', 'I''m a newbie too, hoping for an expert to answer', null);
-- like
INSERT INTO `like` (id, post_id, author_id) VALUES (1, 1, '87654321');
INSERT INTO `like` (id, post_id, author_id) VALUES (2, 2, '12345678');
INSERT INTO `like` (id, post_id, author_id) VALUES (3, 3, '23456789');
-- message
INSERT INTO message (id, receiver_id, content) VALUES (1, '12345678', 'You have a new course notification, please check it promptly');
INSERT INTO message (id, receiver_id, content) VALUES (2, '87654321', 'Your course selection request has been approved, please log in to the system to view');
INSERT INTO message (id, receiver_id, content) VALUES (3, '23456789', 'You have a new internal message, please check it promptly');

