create database if not exists course_management;
USE course_management;
-- department
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
    department_id int not null
    references department
    );

-- professor
create table if not exists professor
(
    id int auto_increment primary key,
    PID int not null unique,
    name varchar(50) not null,
    department_id int not null
    references department
    );

-- student class
create table if not exists students
(
    id int auto_increment primary key,
    name varchar(50) not null,
    SID int not null unique ,
    password varchar(50) not null default '000000',
    klass varchar(50) not null,
    major varchar(50) not null references major
    );

-- time slot
create table if not exists time_slot
(
    id int auto_increment primary key,
    start_time time not null,
    end_time time not null,
    weekday int not null
);

-- course
create table if not exists course
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    department_id int not null
    references department,
    credit int not null,
    rate float not null default 0,
    capacity int not null
    );

create table if not exists course_time_slot
(
    id int auto_increment primary key,
    course_id int not null
    references course,
    time_slot_id int not null
    references time_slot,
    unique (course_id, time_slot_id)
    );

create table if not exists course_student
(
    id int auto_increment primary key,
    course_id int not null
    references course,
    student_id int not null
    references students,
    score int not null default 0,
    unique (course_id, student_id)
    );

create table if not exists course_professor
(
    id int auto_increment primary key,
    course_id int not null
    references course,
    professor_id int not null
    references professor,
    unique (course_id, professor_id)
    );

create table if not exists rate
(
    id int auto_increment primary key,
    course_id int not null
    references course,
    student_id int not null
    references students,
    rate int not null default 0,
    difficulty enum('easy', 'normal', 'hard') not null default 'normal',
    workload enum('light', 'normal', 'heavy') not null default 'normal',
    grading enum('lenient', 'normal', 'strict') not null default 'normal',
    gain enum('low', 'normal', 'high') not null default 'normal',
    unique (course_id, student_id)
    );

create table if not exists room
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    capacity int not null
    );

create table if not exists reservations
(
    id int auto_increment primary key,
    student_id int not null
    references students,
    room_id int not null
    references room,
    time json not null,
    purpose varchar(50) not null,
    unique (student_id, room_id, time)
    );

create table if not exists files
(
    id int auto_increment primary key,
    name varchar(50) not null,
    filetype enum('image', 'video', 'audio', 'document') not null,
    filepath varchar(100) not null,
    uploader_id int not null
    references students,
    upload_time timestamp not null default now()
    );

create table if not exists post
(
    id int auto_increment primary key,
    author_id int not null
    references students,
    title varchar(50) not null,
    content varchar(1000) not null,
    posting_time timestamp not null default now(),
    file_id int
    references files
    );

create table if not exists reply
(
    id int auto_increment primary key,
    post_id int not null
    references post,
    author_id int not null
    references students,
    content varchar(1000) not null,
    time timestamp not null default now(),
    file_id int
    references files,
    unique (author_id, time)
    );

create table if not exists secondary_reply
(
    id int auto_increment primary key,
    reply_id int not null
    references reply,
    author_id int not null
    references students,
    content varchar(1000) not null,
    time timestamp not null default now(),
    file_id int
    references files,
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
    post_id int not null
    references post,
    category_id int not null
    references category,
    unique (post_id, category_id)
    );

create table if not exists `like`
(
    id int auto_increment primary key,
    post_id int not null
    references post,
    author_id int not null
    references students,
    unique (post_id, author_id)
    );