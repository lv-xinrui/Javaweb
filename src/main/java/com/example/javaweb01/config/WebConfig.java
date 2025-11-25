package com.example.javaweb01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
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
}
