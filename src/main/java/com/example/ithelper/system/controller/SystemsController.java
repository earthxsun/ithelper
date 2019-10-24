package com.example.ithelper.system.controller;

import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.servie.SystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemsController {

    private SystemsService systemsService;

    @Autowired
    public SystemsController(SystemsService systemService) {
        this.systemsService = systemService;
    }

    @GetMapping
    public CommonResponse getAllData() {
        return new CommonResponse(systemsService.getAll());
    }
}
