package com.example.ithelper.system.controller;

import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.servie.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public CommonResponse getAllRole(){
        return new CommonResponse(roleService.getAllRoles());
    }
}
