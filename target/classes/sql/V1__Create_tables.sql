create database if not exists cs304proj
character set utf8mb4 collate utf8mb4_unicode_ci;
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
    score int not null default 100,
    avatar BLOB,
    foreign key (major) references major(name),
    constraint check_SID check (SID REGEXP '^[0-9]{8}$')
);

-- course
create table if not exists course
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    CID varchar(5) not null unique,
    semester varchar(50) not null,
    type enum('通识必修课', '通识选修课', '专业必修课','专业选修课') not null,
    department varchar(50) not null,
    credit int not null,
    hours int not null,
    capacity int not null,
    selected int not null default 0,
    location varchar(50) not null,
    description varchar(1000),
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
    description varchar(1000),
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
    major_category varchar(50),
    course_category varchar(50),
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