package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.system.dao.DeptRepository;
import com.example.ithelper.system.entity.Dept;
import com.example.ithelper.system.servie.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public List<Dept> getAllDepts() {
        return deptRepository.findAll();
    }

    @Override
    public Dept getDeptByName(String name) {
        return deptRepository.findByDeptName(name);
    }

    @Override
    public Dept getDeptById(long id) {
        return deptRepository.getOne(id);
    }
}
