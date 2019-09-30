package com.example.ithelper.system.controller;

import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.servie.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public CommonResponse getAllDept(){
        return new CommonResponse(deptService.getAllDepts());
    }
}
