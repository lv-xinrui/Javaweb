package com.example.javaweb01.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
@EntityListeners(AuditingEntityListener.class)
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 100)
    private String category; // 文华要闻、综合新闻等

    @Column(length = 500)
    private String summary; // 摘要

    @Column(length = 500)
    private String imageUrl; // 图片链接

    @Column
    private Boolean isTop = false; // 是否置顶

    @Column
    private Integer viewCount = 0; // 浏览次数

    @Column(length = 50)
    private String author; // 作者

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
