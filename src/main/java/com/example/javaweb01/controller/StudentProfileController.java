package com.example.javaweb01.controller;

import com.example.javaweb01.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

/**
 * 学生个人信息与系统设置页面
 */
@Controller
public class StudentProfileController {

    @GetMapping("/student/profile")
    public String profile(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }

        model.addAttribute("student", student);
        return "student-profile";
    }

    @GetMapping("/student/settings")
    public String settings(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }

        model.addAttribute("student", student);
        return "student-settings";
    }
}
