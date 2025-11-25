package com.example.javaweb01.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 课表实体类
 */
@Data
public class Schedule {
    private Long id;
    private String studentId; // 学号
    private Long courseId; // 课程ID
    private String semester; // 学期
    private Integer weekDay; // 星期几：1-7
    private LocalTime startTime; // 开始时间
    private LocalTime endTime; // 结束时间
    private String classroom; // 教室
    private String weekRange; // 周次范围
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联的课程信息
    private Course course;
}
