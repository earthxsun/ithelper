package com.example.ithelper.system.controller;

import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.servie.AccountNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accountName")
public class AccountNameController {

    private AccountNameService accountNameService;

    @Autowired
    public AccountNameController(AccountNameService accountNameService){
        this.accountNameService = accountNameService;
    }

    @GetMapping("getOne")
    public CommonResponse getOne(String accountId){
        System.out.println(accountId);
        List list = accountNameService.getAccountNameByAccountId(accountId);
        return new CommonResponse(list);
    }

    @PostMapping("add")
    public CommonResponse add(@RequestBody Map map){
        accountNameService.save(map);
        return accountNameService.save(map);
    }

    @GetMapping("del")
    public CommonResponse deleteAllByAccountId(String accountId) {
        return accountNameService.deleteAll(accountId);
    }
}
