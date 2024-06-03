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
    avatar varchar(1000),
    foreign key (major) references major(name),
    constraint check_SID check (SID REGEXP '^[0-9]{8}$')
);

-- course
create table if not exists course
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    CID varchar(5) not null unique,
    semester varchar(50),
    type enum('通识必修课', '通识选修课', '专业必修课','专业选修课') not null,
    department varchar(50) not null,
    credit int not null,
    hours int not null,
    capacity int not null,
    selected int not null default 0,
    location varchar(50) not null,
    description varchar(1000),
    time json,
    rate float,
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
    judged boolean not null default false,
    foreign key (course_id) references course(CID) on delete cascade,
    foreign key (student_id) references student(SID) on delete cascade,
    unique (course_id, student_id)
);


create table if not exists course_professor
(
    id int auto_increment primary key,
    course_id varchar(5) not null,
    professor_id varchar(8) not null,
    foreign key (course_id) references course(CID) on delete cascade ,
    foreign key (professor_id) references professor(PID) on delete cascade,
    unique (course_id, professor_id)
);

create table if not exists rate
(
    id int auto_increment primary key,
    course_id varchar(5) not null,
    student_id varchar(8) not null,
    rate int not null default 3,
    difficulty int not null default 3,
    workload int not null default 3,
    grading int not null default 3,
    gain int not null default 3,
    description varchar(1000),
    foreign key (course_id) references course(CID) on delete cascade,
    foreign key (student_id) references student(SID) on delete cascade,
    unique (course_id, student_id)
);


create table if not exists building
(
    id int auto_increment primary key,
    name varchar(50) not null unique,
    capacity int not null default 0,
    status enum('开放', '关闭') not null default '开放'
);

create table if not exists room
(
    id int auto_increment primary key,
    place varchar(50) not null,
    name varchar(50) not null,
    capacity int not null,
    status enum('开放', '关闭') not null default '开放',
    foreign key (place) references building(name) on delete cascade,
    unique (place, name)
);

create table if not exists reservation
(
    id int auto_increment primary key,
    room_id int not null,
    date date not null,
    start_time time not null,
    end_time time not null,
    create_time timestamp not null default now(),
    status enum('预约','非开放预约时段') not null default '预约',
    foreign key (room_id) references room(id),
    unique (room_id, date, start_time, end_time)
);

create table if not exists student_reservation
(
    id int auto_increment primary key,
    student_id varchar(8) not null,
    reservation_id int not null,
    foreign key (student_id) references student(SID),
    foreign key (reservation_id) references reservation(id),
    unique (student_id, reservation_id)
);

create table if not exists post
(
    id int auto_increment primary key,
    author_id varchar(8) not null,
    title varchar(50) not null,
    content varchar(1000) not null,
    posting_time timestamp not null default now(),
    major_category varchar(50),
    course_category varchar(50),
    constraint fk_post_student foreign key (author_id) references student(SID)
);
create table if not exists file
(
    id int auto_increment primary key,
    name varchar(50) not null,
    filepath varchar(100) not null,
    post_id int,
    constraint fk_file_post foreign key (post_id) references post(id) on delete cascade,
    upload_time timestamp not null default now()
);


create table if not exists reply
(
    id int auto_increment primary key,
    post_id int not null,
    author_id varchar(8) not null,
    content varchar(1000) not null,
    time timestamp not null default now(),
    constraint fk_reply_post foreign key (post_id) references post(id) on delete cascade,
    constraint fk_reply_student foreign key (author_id) references student(SID),
    unique (author_id, time)
);

create table if not exists message
(
    id int auto_increment primary key,
    receiver_id varchar(8) not null,
    content varchar(1000) not null,
    type enum('selection','evaluation','reservation','forum','self') not null,
    creat_time datetime not null default now(),
    end_time datetime not null,
    foreign key (receiver_id) references student(SID)
);