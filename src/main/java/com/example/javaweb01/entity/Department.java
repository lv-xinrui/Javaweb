package com.example.javaweb01.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 200)
    private String website; // 学部网站链接

    @Column(length = 500)
    private String imageUrl; // 学部图片

    @Column(length = 100)
    private String dean; // 学部主任

    @Column(length = 500)
    private String address; // 学部地址

    @Column(length = 20)
    private String phone; // 联系电话

    @Column
    private Integer orderIndex = 0; // 显示顺序

    @Column
    private Boolean isActive = true; // 是否启用

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
