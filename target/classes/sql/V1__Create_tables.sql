create database if not exists cs304proj;
-- department
use cs304proj;
create table if not exists department
(
    id int auto_increment primary key,
    name varchar(50) not null unique
);

-- major
create table if not exists major
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    department varchar(50) not null,
    foreign key (department) references department(name)
);

-- professor
create table if not exists professor
(
    id int auto_increment primary key,
    PID varchar(8) not null unique,
    name varchar(50) not null,
    department varchar(50) not null,
    foreign key (department) references department(name),
    constraint check_PID check (PID REGEXP '^[0-9]{8}$')
);

-- admin
create table if not exists admin
(
    id int auto_increment primary key,
    account varchar(50) not null,
    password varchar(50) not null default '000000'
);

-- student class
create table if not exists student
(
    id int auto_increment primary key,
    name varchar(50) not null,
    SID varchar(8) not null unique,
    password varchar(50) not null default '000000',
    class varchar(50) not null,
    major varchar(50) not null,
    foreign key (major) references major(name),
    constraint check_SID check (SID REGEXP '^[0-9]{8}$')
);

-- course
create table if not exists course
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    CID varchar(5) not null unique,
    type enum('通识必修课', '通识选修课', '专业必修课','专业选修课') not null,
    department varchar(50) not null,
    credit int not null,
    hours int not null,
    capacity int not null,
    selected int not null default 0,
    location varchar(50) not null,
    time json not null,
    foreign key (department) references department(name),
    constraint check_CID check (CID REGEXP '[A-Z]{2}[0-9]{3}$')
);

create table if not exists course_student
(
    id int auto_increment primary key,
    course_id varchar(5) not null,
    student_id varchar(8) not null,
    score int not null default 0,
    valid boolean not null default false,
    foreign key (course_id) references course(CID),
    foreign key (student_id) references student(SID),
    unique (course_id, student_id)
);

create table if not exists course_professor
(
    id int auto_increment primary key,
    course_id varchar(5) not null,
    professor_id varchar(8) not null,
    foreign key (course_id) references course(CID),
    foreign key (professor_id) references professor(PID),
    unique (course_id, professor_id)
);

create table if not exists rate
(
    id int auto_increment primary key,
    course_id varchar(5) not null,
    student_id varchar(8) not null,
    rate int not null default 0,
    difficulty enum('easy', 'normal', 'hard') not null default 'normal',
    workload enum('light', 'normal', 'heavy') not null default 'normal',
    grading enum('lenient', 'normal', 'strict') not null default 'normal',
    gain enum('low', 'normal', 'high') not null default 'normal',
    foreign key (course_id) references course(CID),
    foreign key (student_id) references student(SID),
    unique (course_id, student_id)
);

create table if not exists room
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    capacity int not null
);

create table if not exists reservation
(
    id int auto_increment primary key,
    student_id varchar(8) not null,
    room varchar(50) not null,
    time json not null,
    purpose varchar(50) not null,
    foreign key (student_id) references student(SID),
    foreign key (room) references room(name),
    unique (student_id, room)
);

create table if not exists file
(
    id int auto_increment primary key,
    name varchar(50) not null,
    filetype enum('image', 'video', 'audio', 'document') not null,
    filepath varchar(100) not null,
    uploader_id varchar(8) not null,
    foreign key (uploader_id) references student(SID),
    upload_time timestamp not null default now()
);

create table if not exists post
(
    id int auto_increment primary key,
    author_id varchar(8) not null,
    title varchar(50) not null,
    content varchar(1000) not null,
    posting_time timestamp not null default now(),
    file_id int,
    foreign key (author_id) references student(SID),
    foreign key (file_id) references file(id)
);

create table if not exists reply
(
    id int auto_increment primary key,
    post_id int not null,
    author_id varchar(8) not null,
    content varchar(1000) not null,
    time timestamp not null default now(),
    file_id int,
    foreign key (post_id) references post(id),
    foreign key (author_id) references student(SID),
    foreign key (file_id) references file(id),
    unique (author_id, time)
);

create table if not exists secondary_reply
(
    id int auto_increment primary key,
    reply_id int not null,
    author_id varchar(8) not null,
    content varchar(1000) not null,
    time timestamp not null default now(),
    file_id int,
    foreign key (reply_id) references reply(id),
    foreign key (author_id) references student(SID),
    foreign key (file_id) references file(id),
    unique (author_id, time)
);

create table if not exists category
(
    id int auto_increment primary key,
    name varchar(50) not null unique
);

create table if not exists post_category
(
    id int auto_increment primary key,
    post_id int not null,
    category varchar(50) not null,
    foreign key (post_id) references post(id),
    foreign key (category) references category(name),
    unique (post_id, category)
);

create table if not exists `like`
(
    id int auto_increment primary key,
    post_id int not null,
    author_id varchar(8) not null,
    foreign key (post_id) references post(id),
    foreign key (author_id) references student(SID),
    unique (post_id, author_id)
);

create table if not exists message
(
    id int auto_increment primary key,
    receiver_id varchar(8) not null,
    content varchar(1000) not null,
    time timestamp not null default now(),
    foreign key (receiver_id) references student(SID)
);

-- 插入部门数据
INSERT INTO department (name) VALUES ('计算机科学');
INSERT INTO department (name) VALUES ('电子工程');
INSERT INTO department (name) VALUES ('机械工程');
INSERT INTO department (name) VALUES ('化学工程');
INSERT INTO department (name) VALUES ('数学');

-- 插入专业数据
INSERT INTO major (name, department) VALUES ('计算机科学与技术', '计算机科学');
INSERT INTO major (name, department) VALUES ('软件工程', '计算机科学');
INSERT INTO major (name, department) VALUES ('电子工程', '电子工程');
INSERT INTO major (name, department) VALUES ('机械工程', '机械工程');
INSERT INTO major (name, department) VALUES ('化学工程', '化学工程');
INSERT INTO major (name, department) VALUES ('数学与应用数学', '数学');

-- 插入教授数据
INSERT INTO professor (PID, name, department) VALUES ('00000001', '张三', '计算机科学');
INSERT INTO professor (PID, name, department) VALUES ('00000002', '李四', '电子工程');
INSERT INTO professor (PID, name, department) VALUES ('00000003', '王五', '机械工程');
INSERT INTO professor (PID, name, department) VALUES ('00000004', '赵六', '化学工程');
INSERT INTO professor (PID, name, department) VALUES ('00000005', '钱七', '数学');

-- 插入管理员数据
INSERT INTO admin (account, password) VALUES ('20210001', 'admin123');
INSERT INTO admin (account, password) VALUES ('20210221', 'root123');

-- 插入学生数据
INSERT INTO student (name, SID, password, class, major) VALUES ('学生甲', '10210001', '123456', '1班', '计算机科学与技术');
INSERT INTO student (name, SID, password, class, major) VALUES ('学生乙', '10210002', '123456', '1班', '软件工程');
INSERT INTO student (name, SID, password, class, major) VALUES ('学生丙', '10210003', '123456', '2班', '电子工程');
INSERT INTO student (name, SID, password, class, major) VALUES ('学生丁', '10210004', '123456', '2班', '机械工程');
INSERT INTO student (name, SID, password, class, major) VALUES ('学生戊', '10210005', '123456', '3班', '化学工程');
INSERT INTO student (name, SID, password, class, major) VALUES ('学生己', '10210006', '123456', '3班', '数学与应用数学');

-- 插入课程数据
INSERT INTO course (name, CID, type, department, credit, hours, capacity, selected, location, time)
VALUES ('数据结构', 'CS101', '专业必修课', '计算机科学', 4, 64, 100, 0, 'A101', '{"days": ["Monday", "Wednesday", "Friday"], "times": ["09:00", "10:00", "11:00"]}');
INSERT INTO course (name, CID, type, department, credit, hours, capacity, selected, location, time)
VALUES ('电路原理', 'EE200', '专业必修课', '电子工程', 4, 64, 100, 0, 'B202', '{"days": ["Tuesday", "Thursday"], "times": ["13:00", "14:00"]}');
INSERT INTO course (name, CID, type, department, credit, hours, capacity, selected, location, time)
VALUES ('材料力学', 'ME301', '专业必修课', '机械工程', 4, 64, 100, 0, 'C303', '{"days": ["Monday", "Wednesday"], "times": ["15:00", "16:00"]}');
INSERT INTO course (name, CID, type, department, credit, hours, capacity, selected, location, time)
VALUES ('有机化学', 'CH402', '专业必修课', '化学工程', 4, 64, 100, 0, 'D404', '{"days": ["Tuesday", "Friday"], "times": ["09:00", "10:00"]}');

-- 插入课程学生关系数据
INSERT INTO course_student (course_id, student_id) VALUES ('CS101', '20210001');
INSERT INTO course_student (course_id, student_id) VALUES ('CS101', '20210002');
INSERT INTO course_student (course_id, student_id) VALUES ('EE200', '20210003');
INSERT INTO course_student (course_id, student_id) VALUES ('EE200', '20210004');
INSERT INTO course_student (course_id, student_id) VALUES ('ME301', '20210005');
INSERT INTO course_student (course_id, student_id) VALUES ('ME301', '20210006');

-- 插入课程教授关系数据
INSERT INTO course_professor (course_id, professor_id) VALUES ('CS101', '00000001');
INSERT INTO course_professor (course_id, professor_id) VALUES ('EE200', '00000002');
INSERT INTO course_professor (course_id, professor_id) VALUES ('ME301', '00000003');
INSERT INTO course_professor (course_id, professor_id) VALUES ('CH402', '00000004');

-- 插入评分数据
INSERT INTO rate (course_id, student_id, rate, difficulty, workload, grading, gain)
VALUES ('CS101', '20210001', 4, 'normal', 'normal', 'normal', 'high');
INSERT INTO rate (course_id, student_id, rate, difficulty, workload, grading, gain)
VALUES ('EE200', '20210003', 3, 'hard', 'heavy', 'strict', 'normal');
INSERT INTO rate (course_id, student_id, rate, difficulty, workload, grading, gain)
VALUES ('ME301', '20210005', 5, 'easy', 'light', 'lenient', 'high');

-- 插入房间数据
INSERT INTO room (name, capacity) VALUES ('会议室1', 50);
INSERT INTO room (name, capacity) VALUES ('会议室2', 30);
INSERT INTO room (name, capacity) VALUES ('实验室1', 20);

-- 插入预约数据
INSERT INTO reservation (student_id, room, time, purpose)
VALUES ('20210001', '会议室1', '{"date": "2023-11-08", "start": "09:00", "end": "12:00"}', '学习小组会议');
INSERT INTO reservation (student_id, room, time, purpose)
VALUES ('20210002', '会议室2', '{"date": "2023-11-09", "start": "14:00", "end": "16:00"}', '项目讨论');

-- 插入文件数据
INSERT INTO file (name, filetype, filepath, uploader_id)
VALUES ('报告.docx', 'document', '/path/to/report.docx', '20210001');
INSERT INTO file (name, filetype, filepath, uploader_id)
VALUES ('照片.jpg', 'image', '/path/to/photo.jpg', '20210002');

-- 插入帖子数据
INSERT INTO post (author_id, title, content, file_id)
VALUES ('20210001', '关于数据结构的讨论', '请大家分享一些数据结构的学习心得。', 1);
INSERT INTO post (author_id, title, content, file_id)
VALUES ('20210003', '电路原理问题', '请问有人可以解释一下电路原理中的某个概念吗？', 2);

-- 插入回复数据
INSERT INTO reply (post_id, author_id, content, file_id)
VALUES (1, '20210002', '我在学习中发现了一个很好的资源网站。', NULL);
INSERT INTO reply (post_id, author_id, content, file_id)
VALUES (2, '20210004', '我也有同样的问题，希望能得到解答。', NULL);

-- 插入二级回复数据
INSERT INTO secondary_reply (reply_id, author_id, content)
VALUES (1, '20210005', '谢谢分享，我也去看看。');
INSERT INTO secondary_reply (reply_id, author_id, content)
VALUES (2, '20210006', '我建议查看课本的相关章节。');

-- 插入分类数据
INSERT INTO category (name) VALUES ('学习资源');
INSERT INTO category (name) VALUES ('课程问题');
INSERT INTO category (name) VALUES ('心得分享');

-- 插入帖子分类关系数据
INSERT INTO post_category (post_id, category) VALUES (1, '学习资源');
INSERT INTO post_category (post_id, category) VALUES (2, '课程问题');