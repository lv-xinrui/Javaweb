# MySQL数据库连接完成报告

## 🎉 连接状态

✅ **MySQL数据库连接成功！**

## 📊 数据库配置信息

### 连接参数
- **数据库名**: JavaWeb
- **端口**: 3306
- **用户名**: root
- **密码**: Xb780914@
- **字符集**: utf8
- **排序规则**: utf8mb3_german2_ci

### 连接URL
```
jdbc:mysql://localhost:3306/JavaWeb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
```

## 🗄️ 数据库表结构

### 1. students 表 (学生表)
```sql
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;
```

### 2. courses 表 (课程表)
```sql
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;
```

### 3. schedules 表 (课表表)
```sql
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
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;
```

### 4. grades 表 (成绩表)
```sql
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
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;
```

## 🔧 技术配置

### Spring Boot 配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/JavaWeb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: Xb780914@
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        '[dialect]': org.hibernate.dialect.MySQLDialect
        '[format_sql]': true

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.javaweb01.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

### Maven 依赖
```xml
<!-- MySQL Driver -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>

<!-- MyBatis Spring Boot Starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

## 🚀 使用方法

### 1. 启动应用
```bash
cd /Users/woaichitang/Desktop/JavaWeb01
mvn spring-boot:run
```

### 2. 访问地址
- **首页**: http://localhost:8081
- **学生页面**: http://localhost:8081/student
- **学生登录**: http://localhost:8081/student/login

### 3. 测试账号
- **学号**: 2021001, **密码**: 123456 (张三)
- **学号**: 2021002, **密码**: 123456 (李四)

## 📊 测试数据

系统已预置完整的测试数据：

### 学生数据
- 张三 (2021001) - 计算机科学与技术
- 李四 (2021002) - 软件工程
- 王五 (2021003) - 网络工程
- 赵六 (2022001) - 计算机科学与技术
- 钱七 (2022002) - 软件工程

### 课程数据
- CS101 - 计算机导论 (3.0学分)
- CS102 - C语言程序设计 (4.0学分)
- CS201 - 数据结构 (4.0学分)
- CS202 - 数据库原理 (3.0学分)
- CS301 - 软件工程 (3.0学分)
- CS302 - 计算机网络 (3.0学分)
- CS401 - 人工智能 (3.0学分)
- CS402 - 机器学习 (3.0学分)

### 课表数据
- 2024春季学期课程安排
- 包含时间、教室、教师信息

### 成绩数据
- 2023秋季学期成绩记录
- 2024春季学期期中成绩
- 包含分数、绩点、等级信息

## ✅ 功能验证

### 已测试功能
- ✅ 应用启动正常
- ✅ MySQL数据库连接成功
- ✅ 学生登录页面正常
- ✅ 成绩查询页面正常
- ✅ 学生仪表板正常

### 待优化功能
- ⚠️ 学生登录重定向问题
- ⚠️ 课表查询页面访问
- ⚠️ 退出登录功能

## 🔍 问题排查

### 字符集问题
- **问题**: 初始配置使用`utf8mb3`字符集导致连接失败
- **解决**: 修改为`utf8`字符集，兼容MySQL 8.0.33驱动

### 驱动版本
- **使用**: MySQL Connector/J 8.0.33
- **兼容**: Spring Boot 3.2.0 + MySQL 8.0+

## 📞 技术支持

如有问题或需要功能扩展，请联系开发团队。

---

**连接状态**: ✅ 成功  
**最后更新**: 2024年10月21日  
**技术栈**: Spring Boot 3.2.0 + MyBatis 3.0.3 + MySQL 8.0 + Bootstrap 5 + Thymeleaf
