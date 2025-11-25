package com.example.javaweb01.config;

import com.example.javaweb01.entity.Announcement;
import com.example.javaweb01.entity.Department;
import com.example.javaweb01.entity.MediaReport;
import com.example.javaweb01.entity.News;
import com.example.javaweb01.repository.AnnouncementRepository;
import com.example.javaweb01.repository.DepartmentRepository;
import com.example.javaweb01.repository.MediaReportRepository;
import com.example.javaweb01.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private MediaReportRepository mediaReportRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        // 初始化数据
        initDepartments();
        initNews();
        initAnnouncements();
        initMediaReports();
    }

    private void initDepartments() {
        if (departmentRepository.count() == 0) {
            Department[] departments = {
                    createDepartment("基础科学学部", "负责数学、物理、化学等基础学科的教学与科研工作", 1),
                    createDepartment("外语学部", "培养具有国际视野的外语人才，提供多语种教学服务", 2),
                    createDepartment("经济管理学部", "培养经济管理类应用型人才，服务地方经济发展", 3),
                    createDepartment("信息科学与技术学部", "培养信息技术人才，推动数字化转型", 4),
                    createDepartment("人文社会科学学部", "传承人文精神，培养社会责任感强的人才", 5),
                    createDepartment("机械与电气工程学部", "培养工程技术人才，服务制造业发展", 6),
                    createDepartment("城市建设工程学部", "培养城市建设专业人才，服务城市化进程", 7),
                    createDepartment("马克思主义学部", "开展马克思主义理论教育，培养社会主义建设者", 8),
                    createDepartment("低空经济与大数据产业学院", "面向新兴产业，培养复合型创新人才", 9)
            };

            for (Department dept : departments) {
                departmentRepository.save(dept);
            }
        }
    }

    private Department createDepartment(String name, String description, int orderIndex) {
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        department.setOrderIndex(orderIndex);
        department.setIsActive(true);
        department.setCreateTime(LocalDateTime.now());
        return department;
    }

    private void initNews() {
        if (newsRepository.count() == 0) {
            News[] news = {
                    createNews("文华学院师生参加烈士纪念日活动 缅怀革命英烈 赓续红色血脉",
                            "文华要闻", "在烈士纪念日来临之际，文华学院组织师生代表前往烈士陵园，开展纪念活动，缅怀革命先烈，传承红色精神。",
                            "https://via.placeholder.com/800x400/007bff/ffffff?text=烈士纪念日活动", true),

                    createNews("喜报！我校在湖北省民办高校首届优秀校本教材评选中斩获多项荣誉",
                            "文华要闻", "在湖北省民办高校首届优秀校本教材评选中，我校多部教材脱颖而出，获得专家评委的一致好评。",
                            "https://via.placeholder.com/800x400/28a745/ffffff?text=教材评选获奖", true),

                    createNews("暖心护航，高效协同，为新生享受铁路购票优惠提供坚实保障",
                            "综合新闻", "为帮助新生更好地享受铁路购票优惠政策，学校相关部门积极协调，提供贴心服务。",
                            "https://via.placeholder.com/800x400/ffc107/000000?text=新生服务", false),

                    createNews("擘画蓝图，启航新征程——信息学部2025年发展规划会议召开",
                            "综合新闻", "信息科学与技术学部召开2025年发展规划会议，为学部未来发展制定详细蓝图。",
                            "https://via.placeholder.com/800x400/17a2b8/ffffff?text=发展规划", false),

                    createNews("校企合作开新局 红色教育淬初心——我校与知名企业签署战略合作协议",
                            "综合新闻", "我校与多家知名企业签署战略合作协议，深化产教融合，共同培养高素质应用型人才。",
                            "https://via.placeholder.com/800x400/dc3545/ffffff?text=校企合作", false),

                    createNews("青春舞韵贺双节 文华学子绽芳华",
                            "综合新闻", "在中秋国庆双节期间，文华学子通过各种形式的活动，展现青春活力和文化素养。",
                            "https://via.placeholder.com/800x400/6f42c1/ffffff?text=双节活动", false)
            };

            for (News n : news) {
                newsRepository.save(n);
            }
        }
    }

    private News createNews(String title, String category, String summary, String imageUrl, boolean isTop) {
        News news = new News();
        news.setTitle(title);
        news.setCategory(category);
        news.setSummary(summary);
        news.setImageUrl(imageUrl);
        news.setIsTop(isTop);
        news.setAuthor("文华学院");
        news.setContent(generateNewsContent(title));
        news.setViewCount(0);
        news.setCreateTime(LocalDateTime.now().minusDays((int) (Math.random() * 30)));
        return news;
    }

    private String generateNewsContent(String title) {
        return "<p>这是关于" + title + "的详细报道内容。</p>" +
                "<p>文华学院始终秉承\"立德树人\"的根本任务，致力于培养德智体美劳全面发展的社会主义建设者和接班人。</p>" +
                "<p>学院注重理论与实践相结合，积极开展各类教育活动，为学生提供广阔的发展平台。</p>" +
                "<p>此次活动的成功举办，充分体现了文华学院师生团结协作、积极进取的精神风貌。</p>";
    }

    private void initAnnouncements() {
        if (announcementRepository.count() == 0) {
            Announcement[] announcements = {
                    createAnnouncement("关于举办第十四届中国创新创业大赛的通知",
                            "公告通知", "为激发学生创新创业热情，培养创新创业人才，学校决定举办第十四届中国创新创业大赛。", true),

                    createAnnouncement("关于组织申报2025年度国家社会科学基金项目的通知",
                            "公告通知", "根据上级部门要求，现组织申报2025年度国家社会科学基金项目，请符合条件的教师积极申报。", true),

                    createAnnouncement("关于组织申报2025年度湖北省软科学研究项目的通知",
                            "公告通知", "湖北省软科学研究项目申报工作已启动，请相关教师按照要求准备申报材料。", false),

                    createAnnouncement("关于组织申报2026年度国家艺术基金项目的通知",
                            "公告通知", "国家艺术基金项目申报工作即将开始，请艺术类相关专业教师关注申报信息。", false),

                    createAnnouncement("李强作的政府工作报告（摘登）",
                            "学习园地", "学习领会政府工作报告精神，把握国家发展大局，指导学校各项工作开展。", false),

                    createAnnouncement("中共中央印发《中国共产党纪律处分条例》",
                            "学习园地", "认真学习《中国共产党纪律处分条例》，加强党风廉政建设，营造风清气正的政治生态。", false)
            };

            for (Announcement announcement : announcements) {
                announcementRepository.save(announcement);
            }
        }
    }

    private Announcement createAnnouncement(String title, String category, String summary, boolean isImportant) {
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setCategory(category);
        announcement.setSummary(summary);
        announcement.setIsImportant(isImportant);
        announcement.setIsPublished(true);
        announcement.setPublisher("文华学院");
        announcement.setContent(generateAnnouncementContent(title));
        announcement.setViewCount(0);
        announcement.setCreateTime(LocalDateTime.now().minusDays((int) (Math.random() * 60)));
        return announcement;
    }

    private String generateAnnouncementContent(String title) {
        return "<p>根据学校工作安排，" + title + "。</p>" +
                "<p>请各部门、各学院高度重视，认真组织，确保各项工作顺利开展。</p>" +
                "<p>如有疑问，请联系相关部门。</p>" +
                "<p>特此通知。</p>";
    }

    private void initMediaReports() {
        if (mediaReportRepository.count() == 0) {
            MediaReport[] mediaReports = {
                    createMediaReport("一所民办高校的非遗传承之路", "湖北日报", "文华学院在非遗文化传承方面做出的积极探索和重要贡献。"),
                    createMediaReport("湖北省非遗研究中心倾力打造文化传承新平台", "湖北日报", "我校非遗研究中心成为湖北省重要的文化传承研究平台。"),
                    createMediaReport("铭记历史 吾辈自强", "中国教育在线", "文华学院开展爱国主义教育活动，培养学生的家国情怀。"),
                    createMediaReport("文华学院新生以军训成果展示青春风采", "湖北日报", "新生军训成果汇报表演，展现文华学子的精神风貌。"),
                    createMediaReport("文华学院教师张翼获评省级教学名师", "中国教育在线", "我校教师在教育教学方面取得的突出成就获得认可。")
            };

            for (MediaReport report : mediaReports) {
                mediaReportRepository.save(report);
            }
        }
    }

    private MediaReport createMediaReport(String title, String mediaSource, String summary) {
        MediaReport report = new MediaReport();
        report.setTitle(title);
        report.setMediaSource(mediaSource);
        report.setSummary(summary);
        report.setOriginalUrl("https://example.com/" + title.replaceAll("[^\\w]", "_"));
        report.setImageUrl("https://via.placeholder.com/400x250/6c757d/ffffff?text=" + mediaSource);
        report.setViewCount(0);
        report.setCreateTime(LocalDateTime.now().minusDays((int) (Math.random() * 90)));
        return report;
    }
}
