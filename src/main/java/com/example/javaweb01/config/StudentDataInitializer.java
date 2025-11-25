package com.example.javaweb01.config;

import com.example.javaweb01.entity.Course;
import com.example.javaweb01.entity.Grade;
import com.example.javaweb01.entity.Schedule;
import com.example.javaweb01.entity.Student;
import com.example.javaweb01.mapper.CourseMapper;
import com.example.javaweb01.mapper.GradeMapper;
import com.example.javaweb01.mapper.ScheduleMapper;
import com.example.javaweb01.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 学生数据初始化器
 */
@Component
public class StudentDataInitializer implements CommandLineRunner {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public void run(String... args) throws Exception {
        // 检查是否已有数据
        if (studentMapper.findByStudentId("2021001") != null) {
            return; // 数据已存在，跳过初始化
        }

        // 初始化学生数据
        initStudents();
        initCourses();
        initSchedules();
        initGrades();
    }

    private void initStudents() {
        Student student1 = new Student();
        student1.setStudentId("2021001");
        student1.setName("张三");
        student1.setPassword("123456");
        student1.setGender("男");
        student1.setMajor("计算机科学与技术");
        student1.setClassName("计科2101");
        student1.setGrade("2021级");
        student1.setPhone("13800138001");
        student1.setEmail("zhangsan@wenhua.edu.cn");
        student1.setStatus(1);
        student1.setCreateTime(LocalDateTime.now());
        studentMapper.insert(student1);

        Student student2 = new Student();
        student2.setStudentId("2021002");
        student2.setName("李四");
        student2.setPassword("123456");
        student2.setGender("女");
        student2.setMajor("软件工程");
        student2.setClassName("软工2101");
        student2.setGrade("2021级");
        student2.setPhone("13800138002");
        student2.setEmail("lisi@wenhua.edu.cn");
        student2.setStatus(1);
        student2.setCreateTime(LocalDateTime.now());
        studentMapper.insert(student2);
    }

    private void initCourses() {
        Course course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setCourseName("计算机导论");
        course1.setCredits(new BigDecimal("3.0"));
        course1.setCourseType("必修");
        course1.setDepartment("计算机学院");
        course1.setTeacherName("张教授");
        course1.setDescription("计算机科学基础课程");
        course1.setStatus(1);
        course1.setCreateTime(LocalDateTime.now());
        courseMapper.insert(course1);

        Course course2 = new Course();
        course2.setCourseCode("CS102");
        course2.setCourseName("C语言程序设计");
        course2.setCredits(new BigDecimal("4.0"));
        course2.setCourseType("必修");
        course2.setDepartment("计算机学院");
        course2.setTeacherName("李教授");
        course2.setDescription("C语言编程基础");
        course2.setStatus(1);
        course2.setCreateTime(LocalDateTime.now());
        courseMapper.insert(course2);

        Course course3 = new Course();
        course3.setCourseCode("CS201");
        course3.setCourseName("数据结构");
        course3.setCredits(new BigDecimal("4.0"));
        course3.setCourseType("必修");
        course3.setDepartment("计算机学院");
        course3.setTeacherName("王教授");
        course3.setDescription("数据结构与算法");
        course3.setStatus(1);
        course3.setCreateTime(LocalDateTime.now());
        courseMapper.insert(course3);

        Course course4 = new Course();
        course4.setCourseCode("CS202");
        course4.setCourseName("数据库原理");
        course4.setCredits(new BigDecimal("3.0"));
        course4.setCourseType("必修");
        course4.setDepartment("计算机学院");
        course4.setTeacherName("赵教授");
        course4.setDescription("数据库系统原理");
        course4.setStatus(1);
        course4.setCreateTime(LocalDateTime.now());
        courseMapper.insert(course4);
    }

    private void initSchedules() {
        // 张三的课表
        Schedule schedule1 = new Schedule();
        schedule1.setStudentId("2021001");
        schedule1.setCourseId(1L);
        schedule1.setSemester("2024春季");
        schedule1.setWeekDay(1);
        schedule1.setStartTime(LocalTime.of(8, 0));
        schedule1.setEndTime(LocalTime.of(9, 40));
        schedule1.setClassroom("A101");
        schedule1.setWeekRange("1-16周");
        schedule1.setCreateTime(LocalDateTime.now());
        scheduleMapper.insert(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setStudentId("2021001");
        schedule2.setCourseId(2L);
        schedule2.setSemester("2024春季");
        schedule2.setWeekDay(2);
        schedule2.setStartTime(LocalTime.of(8, 0));
        schedule2.setEndTime(LocalTime.of(9, 40));
        schedule2.setClassroom("B201");
        schedule2.setWeekRange("1-16周");
        schedule2.setCreateTime(LocalDateTime.now());
        scheduleMapper.insert(schedule2);

        // 李四的课表
        Schedule schedule3 = new Schedule();
        schedule3.setStudentId("2021002");
        schedule3.setCourseId(1L);
        schedule3.setSemester("2024春季");
        schedule3.setWeekDay(1);
        schedule3.setStartTime(LocalTime.of(8, 0));
        schedule3.setEndTime(LocalTime.of(9, 40));
        schedule3.setClassroom("A101");
        schedule3.setWeekRange("1-16周");
        schedule3.setCreateTime(LocalDateTime.now());
        scheduleMapper.insert(schedule3);

        Schedule schedule4 = new Schedule();
        schedule4.setStudentId("2021002");
        schedule4.setCourseId(2L);
        schedule4.setSemester("2024春季");
        schedule4.setWeekDay(2);
        schedule4.setStartTime(LocalTime.of(8, 0));
        schedule4.setEndTime(LocalTime.of(9, 40));
        schedule4.setClassroom("B201");
        schedule4.setWeekRange("1-16周");
        schedule4.setCreateTime(LocalDateTime.now());
        scheduleMapper.insert(schedule4);
    }

    private void initGrades() {
        // 张三的成绩
        Grade grade1 = new Grade();
        grade1.setStudentId("2021001");
        grade1.setCourseId(1L);
        grade1.setSemester("2023秋季");
        grade1.setScore(new BigDecimal("85.5"));
        grade1.setGradePoint(new BigDecimal("3.3"));
        grade1.setGradeLevel("B+");
        grade1.setExamType("期末");
        grade1.setExamDate(LocalDate.of(2023, 12, 20));
        grade1.setStatus(1);
        grade1.setCreateTime(LocalDateTime.now());
        gradeMapper.insert(grade1);

        Grade grade2 = new Grade();
        grade2.setStudentId("2021001");
        grade2.setCourseId(2L);
        grade2.setSemester("2023秋季");
        grade2.setScore(new BigDecimal("92.0"));
        grade2.setGradePoint(new BigDecimal("4.0"));
        grade2.setGradeLevel("A");
        grade2.setExamType("期末");
        grade2.setExamDate(LocalDate.of(2023, 12, 22));
        grade2.setStatus(1);
        grade2.setCreateTime(LocalDateTime.now());
        gradeMapper.insert(grade2);

        // 李四的成绩
        Grade grade3 = new Grade();
        grade3.setStudentId("2021002");
        grade3.setCourseId(1L);
        grade3.setSemester("2023秋季");
        grade3.setScore(new BigDecimal("90.0"));
        grade3.setGradePoint(new BigDecimal("3.7"));
        grade3.setGradeLevel("A-");
        grade3.setExamType("期末");
        grade3.setExamDate(LocalDate.of(2023, 12, 20));
        grade3.setStatus(1);
        grade3.setCreateTime(LocalDateTime.now());
        gradeMapper.insert(grade3);

        Grade grade4 = new Grade();
        grade4.setStudentId("2021002");
        grade4.setCourseId(2L);
        grade4.setSemester("2023秋季");
        grade4.setScore(new BigDecimal("95.0"));
        grade4.setGradePoint(new BigDecimal("4.0"));
        grade4.setGradeLevel("A");
        grade4.setExamType("期末");
        grade4.setExamDate(LocalDate.of(2023, 12, 22));
        grade4.setStatus(1);
        grade4.setCreateTime(LocalDateTime.now());
        gradeMapper.insert(grade4);
    }
}
