-- department
INSERT INTO department (id, name) VALUES (1, '计算机科学与技术');
INSERT INTO department (id, name) VALUES (2, '电子工程');
INSERT INTO department (id, name) VALUES (3, '机械工程');
INSERT INTO department (id, name) VALUES (4, '化学工程');
INSERT INTO department (id, name) VALUES (5, '数学');

-- major
INSERT INTO major (id, name, department) VALUES (1, '计算机科学与技术', '计算机科学与技术');
INSERT INTO major (id, name, department) VALUES (2, '软件工程', '计算机科学与技术');
INSERT INTO major (id, name, department) VALUES (3, '通信工程', '电子工程');
INSERT INTO major (id, name, department) VALUES (4, '机械设计制造及其自动化', '机械工程');
INSERT INTO major (id, name, department) VALUES (5, '化学工程与工艺', '化学工程');

-- professor
INSERT INTO professor (id, PID, name, department) VALUES (1, '12345678', '张三', '计算机科学与技术');
INSERT INTO professor (id, PID, name, department) VALUES (2, '87654321', '李四', '电子工程');
INSERT INTO professor (id, PID, name, department) VALUES (3, '23456789', '王五', '机械工程');
INSERT INTO professor (id, PID, name, department) VALUES (4, '98765432', '赵六', '化学工程');
INSERT INTO professor (id, PID, name, department) VALUES (5, '45678912', '孙七', '数学');

-- admin
INSERT INTO admin (id, account, password) VALUES (1, 'admin1', 'password1');
INSERT INTO admin (id, account, password) VALUES (2, 'admin2', 'password2');
INSERT INTO admin (id, account, password) VALUES (3, 'admin3', 'password3');
INSERT INTO admin (id, account, password) VALUES (4, 'admin4', 'password4');
INSERT INTO admin (id, account, password) VALUES (5, 'admin5', 'password5');

-- student
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (1, '学生1', '12345678', 'password1', '1班', '计算机科学与技术', 90);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (2, '学生2', '87654321', 'password2', '2班', '软件工程', 80);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (3, '学生3', '23456789', 'password3', '3班', '通信工程', 85);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (4, '学生4', '98765432', 'password4', '4班', '机械设计制造及其自动化', 95);
INSERT INTO student (id, name, SID, password, class, major, score) VALUES (5, '学生5', '45678912', 'password5', '5班', '化学工程与工艺', 75);

-- course
INSERT INTO course (id, name, CID, semester, type, department, credit, hours, capacity, selected, location, description, time) VALUES (1, '数据结构', 'CS001', '春季学期', '专业必修课', '计算机科学与技术', 4, 64, 100, 0, '教学楼A101', '数据结构课程描述', '{"days": ["周一", "周三"], "times": ["08:00", "10:00"]}');
INSERT INTO course (id, name, CID, semester, type, department, credit, hours, capacity, selected, location, description, time) VALUES (2, '大学物理', 'PH001', '秋季学期', '通识必修课', '电子工程', 3, 48, 150, 0, '实验楼B201', '大学物理课程描述', '{"days": ["周二", "周四"], "times": ["14:00", "16:00"]}');
INSERT INTO course (id, name, CID, semester, type, department, credit, hours, capacity, selected, location, description, time) VALUES (4, '有机化学', 'CH001', '秋季学期', '专业必修课', '化学工程', 4, 64, 100, 0, '化学楼C201', '有机化学基础课程，涵盖有机化合物的基本概念和反应机理', '{"days": ["周二", "周四"], "times": ["13:00", "15:00"]}');

-- course_student
INSERT INTO course_student (id, course_id, student_id, score, valid) VALUES (1, 'CS001', '12345678', 0, false);
INSERT INTO course_student (id, course_id, student_id, score, valid) VALUES (2, 'PH001', '87654321', 0, false);
INSERT INTO course_student (id, course_id, student_id, score, valid) VALUES (3, 'CH001', '23456789', 0, false);

-- course_professor
INSERT INTO course_professor (id, course_id, professor_id) VALUES (1, 'CS001', '12345678');
INSERT INTO course_professor (id, course_id, professor_id) VALUES (2, 'PH001', '87654321');
INSERT INTO course_professor (id, course_id, professor_id) VALUES (3, 'CH001', '98765432');

-- rate
INSERT INTO rate (id, course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES (1, 'CS001', '12345678', 4, 'normal', 'normal', 'lenient', 'high', '很好的课程，学到了很多');
INSERT INTO rate (id, course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES (2, 'PH001', '87654321', 3, 'hard', 'heavy', 'strict', 'low', '课程很难，但教授很严格');
INSERT INTO rate (id, course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES (3, 'CH001', '23456789', 5, 'easy', 'light', 'lenient', 'high', '非常容易的课程，推荐');

-- room
INSERT INTO room (id, name, capacity) VALUES (1, '会议室1', 10);
INSERT INTO room (id, name, capacity) VALUES (2, '会议室2', 20);
INSERT INTO room (id, name, capacity) VALUES (3, '图书馆研讨室', 30);

-- reservation
INSERT INTO reservation (id, student_id, room, time, purpose) VALUES (1, '12345678', '会议室1', '{"date": "2023-04-01", "start": "09:00", "end": "12:00"}', '小组讨论');
INSERT INTO reservation (id, student_id, room, time, purpose) VALUES (2, '87654321', '会议室2', '{"date": "2023-04-02", "start": "14:00", "end": "16:00"}', '学习小组');
INSERT INTO reservation (id, student_id, room, time, purpose) VALUES (3, '23456789', '图书馆研讨室', '{"date": "2023-04-03", "start": "10:00", "end": "13:00"}', '项目会议');

-- file
INSERT INTO file (id, name, filetype, filepath, uploader_id) VALUES (1, '报告.pdf', 'document', '/uploads/12345678/report.pdf', '12345678');
INSERT INTO file (id, name, filetype, filepath, uploader_id) VALUES (2, '照片.jpg', 'image', '/uploads/87654321/photo.jpg', '87654321');
INSERT INTO file (id, name, filetype, filepath, uploader_id) VALUES (3, '演讲.mp4', 'video', '/uploads/23456789/presentation.mp4', '23456789');

-- post
INSERT INTO post (id, author_id, title, content, file_id, major_category, course_category) VALUES (1, '12345678', '数据结构问题', '关于树的部分我不太懂，谁能帮我解释一下？', null, '计算机科学与技术', 'CS001');
INSERT INTO post (id, author_id, title, content, file_id, major_category, course_category) VALUES (2, '87654321', '物理实验求助', '我在实验室遇到了问题，需要一些帮助', null, '电子工程', 'PH001');
INSERT INTO post (id, author_id, title, content, file_id, major_category, course_category) VALUES (3, '23456789', '化学课程疑问', '对于这个化学反应，我有几个问题', null, '化学工程', 'CH001');

-- reply
INSERT INTO reply (id, post_id, author_id, content, file_id) VALUES (1, 1, '87654321', '树的这部分其实很简单的，你可以这样理解...', null);
INSERT INTO reply (id, post_id, author_id, content, file_id) VALUES (2, 2, '12345678', '我也有同样的问题，希望有人能解答', null);

-- secondary_reply (继续)
INSERT INTO secondary_reply (id, reply_id, author_id, content, file_id) VALUES (1, 1, '23456789', '谢谢你的解释，我现在明白了', null);
INSERT INTO secondary_reply (id, reply_id, author_id, content, file_id) VALUES (2, 2, '45678912', '我也是新手，希望大神解答', null);

-- like
INSERT INTO `like` (id, post_id, author_id) VALUES (1, 1, '87654321');
INSERT INTO `like` (id, post_id, author_id) VALUES (2, 2, '12345678');
INSERT INTO `like` (id, post_id, author_id) VALUES (3, 3, '23456789');

-- message
INSERT INTO message (id, receiver_id, content) VALUES (1, '12345678', '你有一个新的课程通知，请及时查看');
INSERT INTO message (id, receiver_id, content) VALUES (2, '87654321', '你的选课请求已被批准，请登录系统查看');
INSERT INTO message (id, receiver_id, content) VALUES (3, '23456789', '你有一条新的站内消息，请及时查看');
