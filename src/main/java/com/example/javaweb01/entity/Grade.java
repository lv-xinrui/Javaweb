package com.example.javaweb01.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成绩实体类
 */
@Data
public class Grade {
    private Long id;
    private String studentId; // 学号
    private Long courseId; // 课程ID
    private String semester; // 学期
    private BigDecimal score; // 成绩
    private BigDecimal gradePoint; // 绩点
    private String gradeLevel; // 等级：A+、A、B+、B、C+、C、D、F
    private String examType; // 考试类型：期末、期中、平时
    private LocalDate examDate; // 考试日期
    private Integer status; // 状态：1-正常，0-无效
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联的课程信息
    private Course course;
}
