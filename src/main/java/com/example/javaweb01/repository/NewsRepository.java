package com.example.javaweb01.repository;

import com.example.javaweb01.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // 根据分类查找新闻
    List<News> findByCategoryOrderByCreateTimeDesc(String category);

    // 分页查询新闻
    Page<News> findByCategoryOrderByCreateTimeDesc(String category, Pageable pageable);

    // 查找置顶新闻
    List<News> findByIsTopTrueOrderByCreateTimeDesc();

    // 搜索新闻标题
    @Query("SELECT n FROM News n WHERE n.title LIKE %:keyword% OR n.summary LIKE %:keyword%")
    Page<News> searchNews(@Param("keyword") String keyword, Pageable pageable);

    // 获取最新新闻
    List<News> findTop10ByOrderByCreateTimeDesc();
}
