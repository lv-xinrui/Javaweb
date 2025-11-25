# 文华学院官网系统 — 详细设计与关键代码报告

> 版本：1.0  | 技术栈：Spring Boot 3.2 + JPA + MyBatis + Thymeleaf + MySQL 8  | JDK 17

## 1. 概述
- 功能：门户资讯展示（新闻/公告/媒体/学部），学生子系统（登录、仪表板、课表、成绩、个人信息、设置）。
- 运行：端口 8081；数据库 `JavaWeb`；Maven 构建。

## 2. 目录结构（关键）
- `src/main/java/com/example/javaweb01/controller/`：控制器
- `src/main/java/com/example/javaweb01/config/`：拦截器、JPA 配置、数据初始化
- `src/main/java/com/example/javaweb01/entity/`：实体（JPA + MyBatis）
- `src/main/java/com/example/javaweb01/repository/`：JPA 仓储
- `src/main/java/com/example/javaweb01/mapper/`：MyBatis 接口
- `src/main/resources/mapper/*.xml`：MyBatis XML
- `src/main/resources/templates/`：Thymeleaf 页面
- `docs/`：文档（本文件）

## 3. 会话与鉴权
- 登录成功后写入 Session：属性名 `student`（脱敏 Student）。
- 受保护路由统一由拦截器 `StudentAuthInterceptor` 校验，无会话跳转登录。
- Cookie 名称为 `JSESSIONID`（见 `application.yml`）。

### 关键代码：拦截器
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/config/StudentAuthInterceptor.java
15:26:public class StudentAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        Student student = (Student) request.getSession().getAttribute("student");
        if (student == null) {
            response.sendRedirect("/student/login");
            return false;
        }
        return true;
    }
}
```

### 关键代码：拦截器注册
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/config/WebConfig.java
17:24:public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new StudentAuthInterceptor())
                .addPathPatterns(
                        "/student/dashboard",
                        "/student/grades",
                        "/student/grades/**",
                        "/student/schedule",
                        "/student/schedule/**",
                        "/student/profile",
                        "/student/settings")
                .excludePathPatterns("/student/login");
}
```

## 四、详细设计与关键代码

### 4.1 登录与会话（Session）

#### 4.1.1 Session 管理机制
- 登录成功后将脱敏的 `Student` 放入 Session，属性名 `student`；
- 客户端通过 Cookie `JSESSIONID` 维持会话；
- 统一拦截器在受保护路由前检查 `student` 是否存在；
- 超时时间 30 分钟（`application.yml` 中配置）。

#### 4.1.2 登录与拦截关键代码

```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/controller/StudentLoginController.java
39:67:@PostMapping("/student/login")
public String login(@RequestParam String studentId,
        @RequestParam String password,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes) {
    Student student = studentMapper.findByStudentIdAndPassword(studentId, password);
    if (student != null) {
        Student sessionStudent = new Student();
        sessionStudent.setId(student.getId());
        sessionStudent.setStudentId(student.getStudentId());
        sessionStudent.setName(student.getName());
        sessionStudent.setGender(student.getGender());
        sessionStudent.setMajor(student.getMajor());
        sessionStudent.setClassName(student.getClassName());
        sessionStudent.setGrade(student.getGrade());
        sessionStudent.setPhone(student.getPhone());
        sessionStudent.setEmail(student.getEmail());
        sessionStudent.setStatus(student.getStatus());
        session.setAttribute("student", sessionStudent);
        redirectAttributes.addFlashAttribute("success", "登录成功！");
        return "redirect:/student/dashboard";
    } else {
        model.addAttribute("error", "学号或密码错误！");
        return "student-login";
    }
}
```

```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/config/StudentAuthInterceptor.java
15:26:public class StudentAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        Student student = (Student) request.getSession().getAttribute("student");
        if (student == null) {
            response.sendRedirect("/student/login");
            return false;
        }
        return true;
    }
}
```

### 4.2 查询功能（示例：成绩与课表）

#### 4.2.1 成绩查询（含统计）
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/controller/StudentGradeController.java
36:63:@GetMapping("/student/grades")
public String gradesPage(HttpSession session,
        @RequestParam(required = false) String semester,
        Model model) {
    Student student = (Student) session.getAttribute("student");
    if (student == null) { return "redirect:/student/login"; }
    List<Grade> grades = (semester != null && !semester.isEmpty())
            ? gradeMapper.findByStudentIdAndSemester(student.getStudentId(), semester)
            : gradeMapper.findCurrentSemesterByStudentId(student.getStudentId());
    double totalCredits = grades.stream()
            .mapToDouble(g -> g.getCourse() != null ? g.getCourse().getCredits().doubleValue() : 0)
            .sum();
    double avgScore = grades.stream()
            .filter(g -> g.getScore() != null)
            .mapToDouble(g -> g.getScore().doubleValue())
            .average().orElse(0.0);
    model.addAttribute("student", student);
    model.addAttribute("grades", grades);
    model.addAttribute("currentSemester", semester);
    model.addAttribute("totalCredits", totalCredits);
    model.addAttribute("avgScore", avgScore);
    return "student-grades";
}
```

#### 4.2.2 课表查询（当前/所有学期）
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/controller/StudentScheduleController.java
27:68:@GetMapping("/student/schedule")
public String schedulePage(HttpSession session,
        @RequestParam(required = false) String semester,
        Model model) {
    Student student = (Student) session.getAttribute("student");
    if (student == null) {
        return "redirect:/student/login";
    }
    List<Schedule> schedules;
    if (semester != null && !semester.isEmpty()) {
        schedules = scheduleMapper.findByStudentIdAndSemester(student.getStudentId(), semester);
    } else {
        schedules = scheduleMapper.findCurrentSemesterByStudentId(student.getStudentId());
    }
    model.addAttribute("student", student);
    model.addAttribute("schedules", schedules);
    model.addAttribute("currentSemester", semester);
    return "student-schedule";
}
```

```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/resources/mapper/ScheduleMapper.xml
31:47:    <select id="findByStudentId" resultMap="ScheduleResultMap">
        SELECT s.*, c.course_code, c.course_name, c.credits, c.course_type, c.teacher_name
        FROM schedules s
        LEFT JOIN courses c ON s.course_id = c.id
        WHERE s.student_id = #{studentId}
        ORDER BY s.semester DESC, s.week_day, s.start_time
    </select>
```

### 4.3 添加/修改/删除（以公告为例，JPA）

#### 4.3.1 实体与仓储
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/entity/Announcement.java
11:20:@Entity
@Table(name = "announcements")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ... 省略若干字段 ...
}
```

```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/repository/AnnouncementRepository.java
13:34:@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByCategoryOrderByCreateTimeDesc(String category);
    Page<Announcement> findByIsPublishedTrueOrderByCreateTimeDesc(Pageable pageable);
}
```

#### 4.3.2 控制器示例（可直接复用到后台管理模块）

```java
// 示例：新增公告（Create）
@PostMapping("/admin/announcements/add")
public String addAnnouncement(@ModelAttribute Announcement announcement,
                              RedirectAttributes ra) {
    announcementRepository.save(announcement);
    ra.addFlashAttribute("success", "公告添加成功！");
    return "redirect:/announcements";
}

// 示例：修改公告（Update）
@PostMapping("/admin/announcements/update")
public String updateAnnouncement(@ModelAttribute Announcement announcement,
                                 RedirectAttributes ra) {
    announcementRepository.save(announcement); // 存在即更新
    ra.addFlashAttribute("success", "公告更新成功！");
    return "redirect:/announcements";
}

// 示例：删除公告（Delete）
@PostMapping("/admin/announcements/delete/{id}")
public String deleteAnnouncement(@PathVariable Long id, RedirectAttributes ra) {
    if (announcementRepository.existsById(id)) {
        announcementRepository.deleteById(id);
        ra.addFlashAttribute("success", "公告删除成功！");
    } else {
        ra.addFlashAttribute("error", "公告不存在！");
    }
    return "redirect:/announcements";
}
```


## 4. 学生登录流程
1) `POST /student/login` 提交学号/密码；
2) 通过 `StudentMapper.findByStudentIdAndPassword` 验证；
3) 会话写入脱敏 `Student`，跳转至 `/student/dashboard`。

```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/controller/StudentLoginController.java
39:67:@PostMapping("/student/login")
public String login(@RequestParam String studentId,
        @RequestParam String password,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes) {
    Student student = studentMapper.findByStudentIdAndPassword(studentId, password);
    if (student != null) {
        Student sessionStudent = new Student();
        sessionStudent.setId(student.getId());
        sessionStudent.setStudentId(student.getStudentId());
        sessionStudent.setName(student.getName());
        sessionStudent.setGender(student.getGender());
        sessionStudent.setMajor(student.getMajor());
        sessionStudent.setClassName(student.getClassName());
        sessionStudent.setGrade(student.getGrade());
        sessionStudent.setPhone(student.getPhone());
        sessionStudent.setEmail(student.getEmail());
        sessionStudent.setStatus(student.getStatus());
        session.setAttribute("student", sessionStudent);
        redirectAttributes.addFlashAttribute("success", "登录成功！");
        return "redirect:/student/dashboard";
    } else {
        model.addAttribute("error", "学号或密码错误！");
        return "student-login";
    }
}
```

## 5. 课表与成绩查询
- 数据访问采用 MyBatis；页面使用 Thymeleaf 渲染。
- 课表支持“当前/指定学期”和“所有学期”两个入口。

### 课表控制器
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/controller/StudentScheduleController.java
27:50:@GetMapping("/student/schedule")
public String schedulePage(HttpSession session,
        @RequestParam(required = false) String semester,
        Model model) {
    Student student = (Student) session.getAttribute("student");
    if (student == null) {
        return "redirect:/student/login";
    }
    List<Schedule> schedules;
    if (semester != null && !semester.isEmpty()) {
        schedules = scheduleMapper.findByStudentIdAndSemester(student.getStudentId(), semester);
    } else {
        schedules = scheduleMapper.findCurrentSemesterByStudentId(student.getStudentId());
    }
    model.addAttribute("student", student);
    model.addAttribute("schedules", schedules);
    model.addAttribute("currentSemester", semester);
    return "student-schedule";
}
```

### 课表查询 SQL（含课程信息联查）
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/resources/mapper/ScheduleMapper.xml
31:47:    <select id="findByStudentId" resultMap="ScheduleResultMap">
        SELECT s.*, c.course_code, c.course_name, c.credits, c.course_type, c.teacher_name
        FROM schedules s
        LEFT JOIN courses c ON s.course_id = c.id
        WHERE s.student_id = #{studentId}
        ORDER BY s.semester DESC, s.week_day, s.start_time
    </select>
```

### 成绩控制器（统计示例：总学分/平均分）
```startLine:endLine:/Users/woaichitang/Desktop/JavaWeb01/src/main/java/com/example/javaweb01/controller/StudentGradeController.java
36:63:@GetMapping("/student/grades")
public String gradesPage(HttpSession session,
        @RequestParam(required = false) String semester,
        Model model) {
    Student student = (Student) session.getAttribute("student");
    if (student == null) { return "redirect:/student/login"; }
    List<Grade> grades = (semester != null && !semester.isEmpty())
            ? gradeMapper.findByStudentIdAndSemester(student.getStudentId(), semester)
            : gradeMapper.findCurrentSemesterByStudentId(student.getStudentId());
    double totalCredits = grades.stream()
            .mapToDouble(g -> g.getCourse() != null ? g.getCourse().getCredits().doubleValue() : 0)
            .sum();
    double avgScore = grades.stream()
            .filter(g -> g.getScore() != null)
            .mapToDouble(g -> g.getScore().doubleValue())
            .average().orElse(0.0);
    model.addAttribute("student", student);
    model.addAttribute("grades", grades);
    model.addAttribute("currentSemester", semester);
    model.addAttribute("totalCredits", totalCredits);
    model.addAttribute("avgScore", avgScore);
    return "student-grades";
}
```

## 6. 门户数据（JPA）
- `HomeController` 负责首页、新闻、公告、媒体、学部等页面，基于 JPA 仓储方法查询；首次启动由 `DataInitializer` 灌入演示数据。

## 7. 数据初始化与脚本
- JPA 初始化：`DataInitializer`（门户数据）。
- MyBatis 初始化：`StudentDataInitializer`（学生/课程/课表/成绩）。
- SQL 脚本：`mysql_init.sql` / `database.sql` 可直接创建库表与样例数据。

## 8. 常见问题与修复记录
- 500（Whitelabel Error）排查：
  - 原因：`ScheduleMapper.xml` 映射 `course.classroom` 字段（`Course` 实体无此属性）导致结果映射异常。
  - 处理：移除无效映射；重建通过，页面可访问。
- 404/重定向：
  - 补充拦截器路径：增加精确路径 `/student/schedule`、`/student/grades` 等，避免仅匹配 `/**` 导致边界问题。

## 9. 安全与优化建议
- 密码加密（BCrypt）、统一异常处理（`@ControllerAdvice`）、CSRF、审计日志、角色权限细化、分页与查询缓存。

## 10. 关键端点清单（学生子系统）
- GET `/student/login`、POST `/student/login`、GET `/student/logout`
- GET `/student/dashboard`
- GET `/student/schedule`、GET `/student/schedule/all`
- GET `/student/grades`、GET `/student/grades/all`
- GET `/student/profile`、GET `/student/settings`

---
本文档用于项目走查与运维交接，后续可补充接口时序图与 ER 图。
