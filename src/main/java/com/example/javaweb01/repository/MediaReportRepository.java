package com.example.javaweb01.repository;

import com.example.javaweb01.entity.MediaReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaReportRepository extends JpaRepository<MediaReport, Long> {

    // 获取最新媒体报道
    List<MediaReport> findTop10ByOrderByCreateTimeDesc();

    // 分页查询媒体报道
    Page<MediaReport> findAllByOrderByCreateTimeDesc(Pageable pageable);

    // 搜索媒体报道
    @Query("SELECT m FROM MediaReport m WHERE m.title LIKE %:keyword% OR m.summary LIKE %:keyword% OR m.mediaSource LIKE %:keyword%")
    Page<MediaReport> searchMediaReports(@Param("keyword") String keyword, Pageable pageable);
}
