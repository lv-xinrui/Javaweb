package com.example.javaweb01.controller;

import com.example.javaweb01.entity.Announcement;
import com.example.javaweb01.entity.Department;
import com.example.javaweb01.entity.MediaReport;
import com.example.javaweb01.entity.News;
import com.example.javaweb01.repository.AnnouncementRepository;
import com.example.javaweb01.repository.DepartmentRepository;
import com.example.javaweb01.repository.MediaReportRepository;
import com.example.javaweb01.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private MediaReportRepository mediaReportRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/")
    public String index(Model model) {
        // 获取文华要闻（置顶新闻）
        List<News> topNews = newsRepository.findByIsTopTrueOrderByCreateTimeDesc();

        // 获取综合新闻（最新10条）
        List<News> generalNews = newsRepository.findTop10ByOrderByCreateTimeDesc();

        // 获取公告通知（最新5条）
        List<Announcement> announcements = announcementRepository.findByIsPublishedTrueOrderByCreateTimeDesc();
        if (announcements.size() > 5) {
            announcements = announcements.subList(0, 5);
        }

        // 获取媒体报道（最新5条）
        List<MediaReport> mediaReports = mediaReportRepository.findTop10ByOrderByCreateTimeDesc();
        if (mediaReports.size() > 5) {
            mediaReports = mediaReports.subList(0, 5);
        }

        // 获取学部设置
        List<Department> departments = departmentRepository.findByIsActiveTrueOrderByOrderIndexAsc();

        model.addAttribute("topNews", topNews);
        model.addAttribute("generalNews", generalNews);
        model.addAttribute("announcements", announcements);
        model.addAttribute("mediaReports", mediaReports);
        model.addAttribute("departments", departments);

        return "index";
    }

    @GetMapping("/news")
    public String news(@RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage;

        if (category.isEmpty()) {
            newsPage = newsRepository.findAll(pageable);
        } else {
            newsPage = newsRepository.findByCategoryOrderByCreateTimeDesc(category, pageable);
        }

        model.addAttribute("newsPage", newsPage);
        model.addAttribute("category", category);

        return "news";
    }

    @GetMapping("/news/detail")
    public String newsDetail(@RequestParam("id") Long id, Model model) {
        News news = newsRepository.findById(id).orElse(null);
        if (news != null) {
            // 增加浏览次数
            news.setViewCount(news.getViewCount() + 1);
            newsRepository.save(news);
        }

        model.addAttribute("news", news);
        return "news-detail";
    }

    @GetMapping("/announcements")
    public String announcements(@RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> announcementPage;

        if (category.isEmpty()) {
            announcementPage = announcementRepository.findByIsPublishedTrueOrderByCreateTimeDesc(pageable);
        } else {
            announcementPage = announcementRepository.findByCategoryOrderByCreateTimeDesc(category, pageable);
        }

        model.addAttribute("announcementPage", announcementPage);
        model.addAttribute("category", category);

        return "announcements";
    }

    @GetMapping("/announcements/detail")
    public String announcementDetail(@RequestParam("id") Long id, Model model) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement != null) {
            // 增加浏览次数
            announcement.setViewCount(announcement.getViewCount() + 1);
            announcementRepository.save(announcement);
        }

        model.addAttribute("announcement", announcement);
        return "announcement-detail";
    }

    @GetMapping("/departments")
    public String departments(Model model) {
        List<Department> departments = departmentRepository.findByIsActiveTrueOrderByOrderIndexAsc();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("/departments/detail")
    public String departmentDetail(@RequestParam("id") Long id, Model model) {
        Department department = departmentRepository.findById(id).orElse(null);
        // 注意：Department实体没有viewCount字段，所以不进行浏览次数统计

        model.addAttribute("department", department);
        return "department-detail";
    }

    @GetMapping("/media")
    public String media(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MediaReport> mediaPage = mediaReportRepository.findAllByOrderByCreateTimeDesc(pageable);

        model.addAttribute("mediaPage", mediaPage);
        return "media";
    }

    @GetMapping("/media/detail")
    public String mediaDetail(@RequestParam("id") Long id, Model model) {
        MediaReport mediaReport = mediaReportRepository.findById(id).orElse(null);
        if (mediaReport != null) {
            // 增加浏览次数
            mediaReport.setViewCount(mediaReport.getViewCount() + 1);
            mediaReportRepository.save(mediaReport);
        }

        model.addAttribute("mediaReport", mediaReport);
        return "media-detail";
    }

    @GetMapping("/student")
    public String student(Model model) {
        // 获取学生相关的新闻和公告
        List<News> studentNews = newsRepository.findByCategoryOrderByCreateTimeDesc("学生活动");
        List<Announcement> studentAnnouncements = announcementRepository.findByCategoryOrderByCreateTimeDesc("学生通知");

        model.addAttribute("studentNews", studentNews);
        model.addAttribute("studentAnnouncements", studentAnnouncements);
        model.addAttribute("pageTitle", "学生专区");

        return "student";
    }

    @GetMapping("/faculty")
    public String faculty(Model model) {
        // 获取教职工相关的新闻和公告
        List<News> facultyNews = newsRepository.findByCategoryOrderByCreateTimeDesc("教职工");
        List<Announcement> facultyAnnouncements = announcementRepository.findByCategoryOrderByCreateTimeDesc("教职工通知");

        model.addAttribute("facultyNews", facultyNews);
        model.addAttribute("facultyAnnouncements", facultyAnnouncements);
        model.addAttribute("pageTitle", "教职工专区");

        return "faculty";
    }

    @GetMapping("/alumni")
    public String alumni(Model model) {
        // 获取校友相关的新闻和公告
        List<News> alumniNews = newsRepository.findByCategoryOrderByCreateTimeDesc("校友活动");
        List<Announcement> alumniAnnouncements = announcementRepository.findByCategoryOrderByCreateTimeDesc("校友通知");

        model.addAttribute("alumniNews", alumniNews);
        model.addAttribute("alumniAnnouncements", alumniAnnouncements);
        model.addAttribute("pageTitle", "校友专区");

        return "alumni";
    }

    @GetMapping("/visitor")
    public String visitor(Model model) {
        // 获取访客相关的信息
        List<News> visitorNews = newsRepository.findByCategoryOrderByCreateTimeDesc("校园开放");
        List<Announcement> visitorAnnouncements = announcementRepository.findByCategoryOrderByCreateTimeDesc("访客信息");
        List<Department> departments = departmentRepository.findByIsActiveTrueOrderByOrderIndexAsc();

        model.addAttribute("visitorNews", visitorNews);
        model.addAttribute("visitorAnnouncements", visitorAnnouncements);
        model.addAttribute("departments", departments);
        model.addAttribute("pageTitle", "访客专区");

        return "visitor";
    }

}
