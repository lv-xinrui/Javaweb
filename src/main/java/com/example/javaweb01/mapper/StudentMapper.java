package com.example.javaweb01.mapper;

import com.example.javaweb01.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper {

    /**
     * 根据学号查询学生信息
     */
    Student findByStudentId(@Param("studentId") String studentId);

    /**
     * 根据学号和密码验证登录
     */
    Student findByStudentIdAndPassword(@Param("studentId") String studentId, @Param("password") String password);

    /**
     * 插入学生信息
     */
    int insert(Student student);

    /**
     * 更新学生信息
     */
    int update(Student student);
}
