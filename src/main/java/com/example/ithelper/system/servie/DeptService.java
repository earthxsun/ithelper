package com.example.ithelper.system.servie;

import com.example.ithelper.system.entity.Dept;

import java.util.List;

public interface DeptService {

    List<Dept> getAllDepts();

    Dept getDeptByName(String name);

    Dept getDeptById(long id);

}
