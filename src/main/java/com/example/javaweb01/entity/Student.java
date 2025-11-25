package com.example.javaweb01.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String studentId; // 学号
    private String name; // 姓名
    private String password; // 密码
    private String gender; // 性别
    private String major; // 专业
    private String className; // 班级
    private String grade; // 年级
    private String phone; // 电话
    private String email; // 邮箱
    private Integer status; // 状态：1-正常，0-停用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
