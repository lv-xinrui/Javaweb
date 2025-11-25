package com.example.javaweb01.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 100)
    private String category; // 公告通知、学习园地等

    @Column(length = 500)
    private String summary;

    @Column
    private Boolean isImportant = false; // 是否重要

    @Column
    private Boolean isPublished = true; // 是否发布

    @Column(length = 50)
    private String publisher; // 发布者

    @Column
    private Integer viewCount = 0;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
