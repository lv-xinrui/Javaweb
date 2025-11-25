-- 文华学院官网数据库初始化脚本
-- 创建数据库

CREATE DATABASE IF NOT EXISTS wenhua_university CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wenhua_university;

-- 创建用户（可选，如果使用默认root用户可跳过）
-- CREATE USER 'wenhua_user'@'localhost' IDENTIFIED BY 'wenhua_password';
-- GRANT ALL PRIVILEGES ON wenhua_university.* TO 'wenhua_user'@'localhost';
-- FLUSH PRIVILEGES;

-- 学生相关表结构
-- 学生表
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    gender VARCHAR(10) COMMENT '性别',
    major VARCHAR(100) COMMENT '专业',
    class_name VARCHAR(50) COMMENT '班级',
    grade VARCHAR(20) COMMENT '年级',
    phone VARCHAR(20) COMMENT '电话',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-停用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_name (name),
    INDEX idx_major (major)
);

-- 课程表
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE COMMENT '课程代码',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credits DECIMAL(3,1) NOT NULL COMMENT '学分',
    course_type VARCHAR(20) COMMENT '课程类型：必修、选修',
    department VARCHAR(100) COMMENT '开课院系',
    teacher_name VARCHAR(50) COMMENT '授课教师',
    description TEXT COMMENT '课程描述',
    status TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-停用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_course_code (course_code),
    INDEX idx_course_name (course_name),
    INDEX idx_teacher (teacher_name)
);

-- 课表表
CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL COMMENT '学号',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    week_day TINYINT NOT NULL COMMENT '星期几：1-7',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    classroom VARCHAR(50) COMMENT '教室',
    week_range VARCHAR(50) COMMENT '周次范围',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    INDEX idx_student_semester (student_id, semester),
    INDEX idx_course_semester (course_id, semester)
);

-- 成绩表
CREATE TABLE grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL COMMENT '学号',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    score DECIMAL(5,2) COMMENT '成绩',
    grade_point DECIMAL(3,2) COMMENT '绩点',
    grade_level VARCHAR(10) COMMENT '等级：A+、A、B+、B、C+、C、D、F',
    exam_type VARCHAR(20) COMMENT '考试类型：期末、期中、平时',
    exam_date DATE COMMENT '考试日期',
    status TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-无效',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    INDEX idx_student_semester (student_id, semester),
    INDEX idx_course_semester (course_id, semester)
);

-- 表结构将由JPA自动创建，这里只是示例
-- 如果需要手动创建表，可以使用以下SQL：

/*
-- 新闻表
CREATE TABLE news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    category VARCHAR(100),
    summary VARCHAR(500),
    image_url VARCHAR(500),
    is_top BOOLEAN DEFAULT FALSE,
    view_count INT DEFAULT 0,
    author VARCHAR(50),
    create_time DATETIME NOT NULL,
    update_time DATETIME,
    INDEX idx_category (category),
    INDEX idx_create_time (create_time),
    INDEX idx_is_top (is_top)
);

-- 公告表
CREATE TABLE announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    category VARCHAR(100),
    summary VARCHAR(500),
    is_important BOOLEAN DEFAULT FALSE,
    is_published BOOLEAN DEFAULT TRUE,
    publisher VARCHAR(50),
    view_count INT DEFAULT 0,
    create_time DATETIME NOT NULL,
    update_time DATETIME,
    INDEX idx_category (category),
    INDEX idx_create_time (create_time),
    INDEX idx_is_published (is_published),
    INDEX idx_is_important (is_important)
);

-- 学部表
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    website VARCHAR(200),
    image_url VARCHAR(500),
    dean VARCHAR(100),
    address VARCHAR(500),
    phone VARCHAR(20),
    order_index INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    create_time DATETIME NOT NULL,
    update_time DATETIME,
    INDEX idx_is_active (is_active),
    INDEX idx_order_index (order_index)
);

-- 媒体报道表
CREATE TABLE media_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    summary VARCHAR(500),
    media_source VARCHAR(200),
    original_url VARCHAR(500),
    image_url VARCHAR(500),
    view_count INT DEFAULT 0,
    create_time DATETIME NOT NULL,
    update_time DATETIME,
    INDEX idx_create_time (create_time),
    INDEX idx_media_source (media_source)
);
*/

-- 初始化学生相关数据
-- 插入学生数据
INSERT INTO students (student_id, name, password, gender, major, class_name, grade, phone, email) VALUES
('2021001', '张三', '123456', '男', '计算机科学与技术', '计科2101', '2021级', '13800138001', 'zhangsan@wenhua.edu.cn'),
('2021002', '李四', '123456', '女', '软件工程', '软工2101', '2021级', '13800138002', 'lisi@wenhua.edu.cn'),
('2021003', '王五', '123456', '男', '网络工程', '网工2101', '2021级', '13800138003', 'wangwu@wenhua.edu.cn'),
('2022001', '赵六', '123456', '女', '计算机科学与技术', '计科2201', '2022级', '13800138004', 'zhaoliu@wenhua.edu.cn'),
('2022002', '钱七', '123456', '男', '软件工程', '软工2201', '2022级', '13800138005', 'qianqi@wenhua.edu.cn');

-- 插入课程数据
INSERT INTO courses (course_code, course_name, credits, course_type, department, teacher_name, description) VALUES
('CS101', '计算机导论', 3.0, '必修', '计算机学院', '张教授', '计算机科学基础课程'),
('CS102', 'C语言程序设计', 4.0, '必修', '计算机学院', '李教授', 'C语言编程基础'),
('CS201', '数据结构', 4.0, '必修', '计算机学院', '王教授', '数据结构与算法'),
('CS202', '数据库原理', 3.0, '必修', '计算机学院', '赵教授', '数据库系统原理'),
('CS301', '软件工程', 3.0, '必修', '计算机学院', '钱教授', '软件开发生命周期'),
('CS302', '计算机网络', 3.0, '必修', '计算机学院', '孙教授', '计算机网络基础'),
('CS401', '人工智能', 3.0, '选修', '计算机学院', '周教授', '人工智能基础'),
('CS402', '机器学习', 3.0, '选修', '计算机学院', '吴教授', '机器学习算法');

-- 插入课表数据（2024春季学期）
INSERT INTO schedules (student_id, course_id, semester, week_day, start_time, end_time, classroom, week_range) VALUES
-- 张三的课表
('2021001', 1, '2024春季', 1, '08:00:00', '09:40:00', 'A101', '1-16周'),
('2021001', 2, '2024春季', 2, '08:00:00', '09:40:00', 'B201', '1-16周'),
('2021001', 3, '2024春季', 3, '10:00:00', '11:40:00', 'A102', '1-16周'),
('2021001', 4, '2024春季', 4, '14:00:00', '15:40:00', 'B202', '1-16周'),
('2021001', 5, '2024春季', 5, '10:00:00', '11:40:00', 'A103', '1-16周'),
-- 李四的课表
('2021002', 1, '2024春季', 1, '08:00:00', '09:40:00', 'A101', '1-16周'),
('2021002', 2, '2024春季', 2, '08:00:00', '09:40:00', 'B201', '1-16周'),
('2021002', 6, '2024春季', 3, '10:00:00', '11:40:00', 'A104', '1-16周'),
('2021002', 7, '2024春季', 4, '14:00:00', '15:40:00', 'B203', '1-16周'),
('2021002', 8, '2024春季', 5, '10:00:00', '11:40:00', 'A105', '1-16周');

-- 插入成绩数据
INSERT INTO grades (student_id, course_id, semester, score, grade_point, grade_level, exam_type, exam_date) VALUES
-- 张三的成绩
('2021001', 1, '2023秋季', 85.5, 3.3, 'B+', '期末', '2023-12-20'),
('2021001', 2, '2023秋季', 92.0, 4.0, 'A', '期末', '2023-12-22'),
('2021001', 3, '2024春季', 88.0, 3.7, 'A-', '期中', '2024-04-15'),
('2021001', 4, '2024春季', 76.5, 2.7, 'C+', '期中', '2024-04-18'),
-- 李四的成绩
('2021002', 1, '2023秋季', 90.0, 3.7, 'A-', '期末', '2023-12-20'),
('2021002', 2, '2023秋季', 95.0, 4.0, 'A', '期末', '2023-12-22'),
('2021002', 6, '2024春季', 82.0, 3.0, 'B-', '期中', '2024-04-16'),
('2021002', 7, '2024春季', 88.5, 3.7, 'A-', '期中', '2024-04-19');

-- 初始化数据示例（实际数据由DataInitializer自动插入）

-- 查看数据库和表
SHOW DATABASES;
SHOW TABLES;

-- 提示信息
SELECT '数据库初始化完成！请确保application.yml中的数据库配置正确。' AS message;
