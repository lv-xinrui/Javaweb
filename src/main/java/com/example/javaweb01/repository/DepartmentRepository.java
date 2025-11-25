package com.example.javaweb01.repository;

import com.example.javaweb01.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // 查找所有启用的学部，按顺序排列
    List<Department> findByIsActiveTrueOrderByOrderIndexAsc();

    // 根据名称查找学部
    Department findByName(String name);
}
