package com.example.javaweb01.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "media_reports")
@Data
@EntityListeners(AuditingEntityListener.class)
public class MediaReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 500)
    private String summary;

    @Column(length = 200)
    private String mediaSource; // 媒体来源，如湖北日报

    @Column(length = 500)
    private String originalUrl; // 原文链接

    @Column(length = 500)
    private String imageUrl; // 图片链接

    @Column
    private Integer viewCount = 0;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
