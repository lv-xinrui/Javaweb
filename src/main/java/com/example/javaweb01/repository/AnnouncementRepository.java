package com.example.javaweb01.repository;

import com.example.javaweb01.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    // 根据分类查找公告
    List<Announcement> findByCategoryOrderByCreateTimeDesc(String category);

    // 分页查询公告
    Page<Announcement> findByCategoryOrderByCreateTimeDesc(String category, Pageable pageable);

    // 查找重要公告
    List<Announcement> findByIsImportantTrueOrderByCreateTimeDesc();

    // 查找已发布的公告
    List<Announcement> findByIsPublishedTrueOrderByCreateTimeDesc();

    // 分页查询已发布的公告
    Page<Announcement> findByIsPublishedTrueOrderByCreateTimeDesc(Pageable pageable);

    // 搜索公告
    @Query("SELECT a FROM Announcement a WHERE a.title LIKE %:keyword% OR a.summary LIKE %:keyword%")
    Page<Announcement> searchAnnouncements(@Param("keyword") String keyword, Pageable pageable);
}
