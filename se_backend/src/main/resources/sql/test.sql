USE course_management;
-- Delete data from tables
TRUNCATE TABLE `like`;
TRUNCATE TABLE post_category;
TRUNCATE TABLE category;
TRUNCATE TABLE secondary_reply;
TRUNCATE TABLE reply;
TRUNCATE TABLE post;
TRUNCATE TABLE files;
TRUNCATE TABLE room;
TRUNCATE TABLE rate;
TRUNCATE TABLE course_professor;
TRUNCATE TABLE course_student;
TRUNCATE TABLE course_time_slot;
TRUNCATE TABLE course;
TRUNCATE TABLE time_slot;
TRUNCATE TABLE student;
TRUNCATE TABLE professor;
TRUNCATE TABLE major;
TRUNCATE TABLE department;
-- Insert departments
INSERT INTO department (name) VALUES ('Computer Science'), ('Mathematics'), ('Physics');

-- Insert majors
INSERT INTO major (name, department_id) VALUES ('Software Engineering', 1), ('Applied Mathematics', 2), ('Astrophysics', 3);

-- Insert professors
INSERT INTO professor (PID, name, department_id) VALUES (1001, 'John Doe', 1), (1002, 'Jane Smith', 2), (1003, 'Albert Einstein', 3);

-- Insert students
INSERT INTO student (name, SID, password, klass, major) VALUES ('Alice', 2001, 'password', 'Class 1', 'Software Engineering'), ('Bob', 2002, 'password', 'Class 2', 'Applied Mathematics'), ('Charlie', 2003, 'password', 'Class 3', 'Astrophysics');

-- Insert time slots
INSERT INTO time_slot (start_time, end_time, weekday) VALUES ('08:00:00', '10:00:00', 1), ('10:00:00', '12:00:00', 2), ('14:00:00', '16:00:00', 3);

-- Insert courses
INSERT INTO course (name, department_id, credit, rate, capacity) VALUES ('Data Structures', 1, 4, 4.5, 30), ('Linear Algebra', 2, 3, 4.0, 40), ('Quantum Mechanics', 3, 5, 4.7, 20);