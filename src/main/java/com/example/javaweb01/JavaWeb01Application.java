package com.example.javaweb01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.javaweb01.mapper")
public class JavaWeb01Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaWeb01Application.class, args);
    }

}
