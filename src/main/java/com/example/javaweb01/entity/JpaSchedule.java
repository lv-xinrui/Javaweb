package com.example.javaweb01.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 课表JPA实体类
 */
@Entity
@Table(name = "schedules")
@Data
public class JpaSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;

    @Column(name = "week_day", nullable = false)
    private Integer weekDay;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "classroom", length = 50)
    private String classroom;

    @Column(name = "week_range", length = 50)
    private String weekRange;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
