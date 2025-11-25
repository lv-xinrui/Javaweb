package com.example.javaweb01.mapper;

import com.example.javaweb01.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成绩Mapper接口
 */
@Mapper
public interface GradeMapper {

    /**
     * 根据学号查询所有成绩
     */
    List<Grade> findByStudentId(@Param("studentId") String studentId);

    /**
     * 根据学号和学期查询成绩
     */
    List<Grade> findByStudentIdAndSemester(@Param("studentId") String studentId, @Param("semester") String semester);

    /**
     * 根据学号查询当前学期成绩
     */
    List<Grade> findCurrentSemesterByStudentId(@Param("studentId") String studentId);

    /**
     * 根据学号统计成绩信息
     */
    Grade findGradeStatisticsByStudentId(@Param("studentId") String studentId);

    /**
     * 插入成绩
     */
    int insert(Grade grade);
}
