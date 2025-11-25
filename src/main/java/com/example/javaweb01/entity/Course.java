package com.example.javaweb01.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
public class Course {
    private Long id;
    private String courseCode; // 课程代码
    private String courseName; // 课程名称
    private BigDecimal credits; // 学分
    private String courseType; // 课程类型：必修、选修
    private String department; // 开课院系
    private String teacherName; // 授课教师
    private String description; // 课程描述
    private Integer status; // 状态：1-正常，0-停用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
