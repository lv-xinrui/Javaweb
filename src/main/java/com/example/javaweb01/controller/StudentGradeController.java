package com.example.javaweb01.controller;

import com.example.javaweb01.entity.Grade;
import com.example.javaweb01.entity.Student;
import com.example.javaweb01.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * 学生成绩查询控制器
 */
@Controller
public class StudentGradeController {

    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 显示成绩查询页面
     */
    @GetMapping("/student/grades")
    public String gradesPage(HttpSession session,
            @RequestParam(required = false) String semester,
            Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }

        List<Grade> grades;
        if (semester != null && !semester.isEmpty()) {
            // 查询指定学期的成绩
            grades = gradeMapper.findByStudentIdAndSemester(student.getStudentId(), semester);
        } else {
            // 查询当前学期成绩
            grades = gradeMapper.findCurrentSemesterByStudentId(student.getStudentId());
        }

        // 计算统计信息
        double totalCredits = grades.stream()
                .mapToDouble(g -> g.getCourse() != null ? g.getCourse().getCredits().doubleValue() : 0)
                .sum();

        double avgScore = grades.stream()
                .filter(g -> g.getScore() != null)
                .mapToDouble(g -> g.getScore().doubleValue())
                .average()
                .orElse(0.0);

        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        model.addAttribute("currentSemester", semester);
        model.addAttribute("totalCredits", totalCredits);
        model.addAttribute("avgScore", avgScore);

        return "student-grades";
    }

    /**
     * 显示所有学期成绩
     */
    @GetMapping("/student/grades/all")
    public String allGrades(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }

        List<Grade> grades = gradeMapper.findByStudentId(student.getStudentId());

        // 计算统计信息
        double totalCredits = grades.stream()
                .mapToDouble(g -> g.getCourse() != null ? g.getCourse().getCredits().doubleValue() : 0)
                .sum();

        double avgScore = grades.stream()
                .filter(g -> g.getScore() != null)
                .mapToDouble(g -> g.getScore().doubleValue())
                .average()
                .orElse(0.0);

        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        model.addAttribute("totalCredits", totalCredits);
        model.addAttribute("avgScore", avgScore);

        return "student-grades-all";
    }
}
