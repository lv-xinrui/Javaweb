package com.example.javaweb01.mapper;

import com.example.javaweb01.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课表Mapper接口
 */
@Mapper
public interface ScheduleMapper {

    /**
     * 根据学号和学期查询课表
     */
    List<Schedule> findByStudentIdAndSemester(@Param("studentId") String studentId, @Param("semester") String semester);

    /**
     * 根据学号查询所有课表
     */
    List<Schedule> findByStudentId(@Param("studentId") String studentId);

    /**
     * 根据学号查询当前学期课表
     */
    List<Schedule> findCurrentSemesterByStudentId(@Param("studentId") String studentId);

    /**
     * 插入课表
     */
    int insert(Schedule schedule);
}
