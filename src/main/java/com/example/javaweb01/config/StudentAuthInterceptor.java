package com.example.javaweb01.config;

import com.example.javaweb01.entity.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 学生登录拦截器
 * 用于验证学生是否已登录
 */
public class StudentAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        // 获取session中的学生信息
        Student student = (Student) request.getSession().getAttribute("student");

        // 如果学生信息不存在，跳转到登录页面
        if (student == null) {
            response.sendRedirect("/student/login");
            return false;
        }

        return true;
    }
}
