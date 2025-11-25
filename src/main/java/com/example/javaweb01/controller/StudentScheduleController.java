package com.example.javaweb01.controller;

import com.example.javaweb01.entity.Schedule;
import com.example.javaweb01.entity.Student;
import com.example.javaweb01.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * 学生课表查询控制器
 */
@Controller
public class StudentScheduleController {

    @Autowired
    private ScheduleMapper scheduleMapper;

    /**
     * 显示课表查询页面
     */
    @GetMapping("/student/schedule")
    public String schedulePage(HttpSession session,
            @RequestParam(required = false) String semester,
            Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }

        List<Schedule> schedules;
        if (semester != null && !semester.isEmpty()) {
            // 查询指定学期的课表
            schedules = scheduleMapper.findByStudentIdAndSemester(student.getStudentId(), semester);
        } else {
            // 查询当前学期课表
            schedules = scheduleMapper.findCurrentSemesterByStudentId(student.getStudentId());
        }

        model.addAttribute("student", student);
        model.addAttribute("schedules", schedules);
        model.addAttribute("currentSemester", semester);

        return "student-schedule";
    }

    /**
     * 显示所有学期课表
     */
    @GetMapping("/student/schedule/all")
    public String allSchedules(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }

        List<Schedule> schedules = scheduleMapper.findByStudentId(student.getStudentId());

        model.addAttribute("student", student);
        model.addAttribute("schedules", schedules);

        return "student-schedule-all";
    }
}
