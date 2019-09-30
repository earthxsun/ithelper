package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptRepository extends JpaRepository<Dept,Long> {

    Dept findByDeptName(String name);

    List<Dept> findAllByDeptNameContains(String name);
}
