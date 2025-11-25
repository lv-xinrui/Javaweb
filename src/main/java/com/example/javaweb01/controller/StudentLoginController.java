package com.example.javaweb01.controller;

import com.example.javaweb01.entity.Student;
import com.example.javaweb01.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

/**
 * 学生登录控制器
 * 使用Session管理用户登录状态
 */
@Controller
public class StudentLoginController {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 显示登录页面
     */
    @GetMapping("/student/login")
    public String loginPage(@RequestParam(required = false) String logout, Model model) {
        if ("true".equals(logout)) {
            model.addAttribute("success", "您已成功退出登录！");
        }
        return "student-login";
    }

    /**
     * 处理登录请求
     */
    @PostMapping("/student/login")
    public String login(@RequestParam String studentId,
            @RequestParam String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 验证学号和密码
        Student student = studentMapper.findByStudentIdAndPassword(studentId, password);

        if (student != null) {
            // 登录成功，将学生信息存入session
            // 创建学生对象副本，不包含密码等敏感信息
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
            // 登录失败
            model.addAttribute("error", "学号或密码错误！");
            return "student-login";
        }
    }

    /**
     * 学生仪表板
     */
    @GetMapping("/student/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "会话已过期，请重新登录！");
            return "redirect:/student/login";
        }

        model.addAttribute("student", student);
        return "student-dashboard";
    }

    /**
     * 退出登录
     */
    @GetMapping("/student/logout")
    public String logout(HttpSession session) {
        // 清除所有session属性
        session.removeAttribute("student");
        // 使session失效
        session.invalidate();
        return "redirect:/student/login?logout=true";
    }
}
