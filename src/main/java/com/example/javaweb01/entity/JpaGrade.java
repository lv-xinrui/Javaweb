package com.example.javaweb01.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成绩JPA实体类
 */
@Entity
@Table(name = "grades")
@Data
public class JpaGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "grade_point", precision = 3, scale = 2)
    private BigDecimal gradePoint;

    @Column(name = "grade_level", length = 10)
    private String gradeLevel;

    @Column(name = "exam_type", length = 20)
    private String examType;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "status")
    private Integer status = 1;

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
