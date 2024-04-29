-- Department 表
INSERT INTO department (name) VALUES ('计算机科学与技术系');
INSERT INTO department (name) VALUES ('文学与新闻学院');
INSERT INTO department (name) VALUES ('经济管理学院');

-- Major 表
INSERT INTO major (name, department) VALUES ('计算机科学与技术', '计算机科学与技术系');
INSERT INTO major (name, department) VALUES ('汉语言文学', '文学与新闻学院');
INSERT INTO major (name, department) VALUES ('工商管理', '经济管理学院');

-- Professor 表
INSERT INTO professor (PID, name, department) VALUES ('00000001', '张三', '计算机科学与技术系');
INSERT INTO professor (PID, name, department) VALUES ('00000002', '李四', '文学与新闻学院');
INSERT INTO professor (PID, name, department) VALUES ('00000003', '王五', '经济管理学院');

-- Admin 表
INSERT INTO admin (account, password) VALUES ('admin1', '123456');
INSERT INTO admin (account, password) VALUES ('admin2', 'qwerty');
INSERT INTO admin (account, password) VALUES ('admin3', 'adminpwd');

-- Student 表
INSERT INTO student (name, SID, class, major, score) VALUES ('小明', '00000001', '计科1801班', '计算机科学与技术', 100);
INSERT INTO student (name, SID, class, major, score) VALUES ('小红', '00000002', '中文1902班', '汉语言文学', 100);
INSERT INTO student (name, SID, class, major, score) VALUES ('小刚', '00000003', '工商2003班', '工商管理', 100);

-- Course 表
INSERT INTO course (name, CID, semester, type, department, credit, hours, capacity, location, description, time) VALUES
    ('计算机网络', 'CN001', 'semester_value', '专业必修课', '计算机科学与技术系', 3, 48, 50, 'A楼301教室', '计算机网络原理及应用', '{"Monday": ["08:00-10:00"], "Wednesday": ["14:00-16:00"]}');
INSERT INTO course (name, CID, semester, type, department, credit, hours, capacity, location, description, time) VALUES
    ('古代文学', 'WL002', 'semester_value', '专业必修课', '文学与新闻学院', 4, 64, 40, 'B楼202教室', '中国古代文学经典研读', '{"Tuesday": ["10:00-12:00"], "Thursday": ["16:00-18:00"]}');
INSERT INTO course (name, CID, semester, type, department, credit, hours, capacity, location, description, time) VALUES
    ('市场营销', 'MG003', 'semester_value', '专业必修课', '经济管理学院', 3, 36, 60, 'C楼103教室', '现代市场营销策略分析', '{"Monday": ["14:00-16:00"], "Friday": ["08:00-10:00"]}');

-- Course_Student 表
INSERT INTO course_student (course_id, student_id, score, valid) VALUES ('CN001', '00000001', 85, true);
INSERT INTO course_student (course_id, student_id, score, valid) VALUES ('WL002', '00000002', 90, true);
INSERT INTO course_student (course_id, student_id, score, valid) VALUES ('MG003', '00000003', 88, false);

-- Course_Professor 表
INSERT INTO course_professor (course_id, professor_id) VALUES ('CN001', '00000001');
INSERT INTO course_professor (course_id, professor_id) VALUES ('WL002', '00000002');
INSERT INTO course_professor (course_id, professor_id) VALUES ('MG003', '00000003');

-- Rate 表
INSERT INTO rate (course_id, student_id, rate, difficulty, workload, grading, gain, description) VALUES ('CN001', '00000001', 4, 'normal', 'normal', 'normal', 'high', '课程难度适中，作业量合理');
INSERT INTO rate (course_id, student_id, rate, difficulty, workload, grading, gain) VALUES ('WL002', '00000002', 5, 'easy', 'light', 'lenient', 'normal');
INSERT INTO rate (course_id, student_id, rate, difficulty, workload, grading, gain) VALUES ('MG003', '00000003', 3.5, 'hard', 'heavy', 'strict', 'low');

-- Room 表
INSERT INTO room (name, capacity) VALUES ('A楼101教室', 100);
INSERT INTO room (name, capacity) VALUES ('B楼202会议室', 50);
INSERT INTO room (name, capacity) VALUES ('C楼303实验室', 30);

-- Reservation 表
INSERT INTO reservation (student_id, room, time, purpose) VALUES ('00000001', 'A楼101教室', '{"2022-11-01": ["14:00-17:00"]}', '小组讨论');
INSERT INTO reservation (student_id, room, time, purpose) VALUES ('00000002', 'B楼202会议室', '{"2022-11-03": ["10:00-12:00"]}', '社团会议');
INSERT INTO reservation (student_id, room, time, purpose) VALUES ('00000003', 'C楼303实验室', '{"2022-11-05": ["18:00-21:00"]}', '实验操作');

-- 文件表、帖子表、回复表、二级回复表、分类表以及帖子分类关联表的数据插入示例因缺乏上下文和实际内容，这里不提供具体实例。

-- File 表（假设）
INSERT INTO file (name, filetype, filepath, uploader_id) VALUES ('封面图片.jpg', 'image', '/uploads/cover.jpg', '00000001');
INSERT INTO file (name, filetype, filepath, uploader_id) VALUES ('演示视频.mp4', 'video', '/uploads/presentation.mp4', '00000002');
INSERT INTO file (name, filetype, filepath, uploader_id) VALUES ('报告文档.pdf', 'document', '/uploads/report.pdf', '00000003');

-- Post 表（假设）
INSERT INTO post (author_id, title, content, posting_time, file_id) VALUES ('00000001', '学习分享', '...', NOW(), 1);
INSERT INTO post (author_id, title, content, posting_time) VALUES ('00000002', '文学交流贴', '...', NOW());
INSERT INTO post (author_id, title, content, posting_time, file_id) VALUES ('00000003', '案例分析', '...', NOW(), 3);

-- Category 表（假设已有）
INSERT INTO category (name) VALUES ('学习心得'), ('文学交流'), ('商业案例');

-- Post_Category 表（假设已关联）
INSERT INTO post_category (post_id, category) VALUES ((SELECT id FROM post WHERE author_id = '00000001'), '学习心得');
INSERT INTO post_category (post_id, category) VALUES ((SELECT id FROM post WHERE author_id = '00000002'), '文学交流');
INSERT INTO post_category (post_id, category) VALUES ((SELECT id FROM post WHERE author_id = '00000003'), '商业案例');