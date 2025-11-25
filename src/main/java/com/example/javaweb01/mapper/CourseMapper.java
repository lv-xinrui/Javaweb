package com.example.javaweb01.mapper;

import com.example.javaweb01.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper {

    /**
     * 根据ID查询课程
     */
    Course findById(@Param("id") Long id);

    /**
     * 插入课程
     */
    int insert(Course course);

    /**
     * 更新课程
     */
    int update(Course course);
}
