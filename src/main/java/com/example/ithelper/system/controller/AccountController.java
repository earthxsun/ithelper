package com.example.ithelper.system.controller;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.entity.Account;
import com.example.ithelper.system.servie.AccountDataService;
import com.example.ithelper.system.servie.AccountService;
import com.example.ithelper.system.servie.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDataService accountDataService;

    @Autowired
    UserService userService;

    @RequestMapping("test")
    public String test() {
        return "ok";
    }

    //获取表格数据
    @GetMapping("/data")
    public CommonResponse getAccountData() {
        return new CommonResponse(accountDataService.getDatas());
    }

    //获取AccountData数据
    @PostMapping("getAll")
    public CommonResponse getAccounts(@RequestBody Map map) {
        System.out.println(map);
        return new CommonResponse(accountService.getAccounts(map));
    }

    //根据当前用户的部门返回部门数据
    @GetMapping("getDept")
    public CommonResponse getDept() throws CommonException {
        String currentUser = JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());
        String dept = userService.getUserByUsername(currentUser).getDept().getDeptName();
        ArrayList<String> arrayList = new ArrayList<>();
        if (dept.equals("关务中心") || dept.equals("关务综合") || dept.equals("东诚报关部")) {
            arrayList.add("关务中心");
            arrayList.add("关务综合");
            arrayList.add("东诚报关部");
        } else {
            arrayList.add(dept);
        }
        return new CommonResponse(arrayList);
    }

    //保存申请表数据
    @PostMapping
    public CommonResponse saveData(@RequestBody Map map) {
        System.out.println(map.get("formData").toString());
        String method = map.get("method").toString();
        System.out.println(method);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map accountDataMap = objectMapper.readValue(map.get("formData").toString(), Map.class);
            accountService.saveData(accountDataMap, method);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonResponse("ok");
    }

    //更新申请表状态
    @GetMapping("updateStatus")
    public CommonResponse updateStatus(long id, String status) {

        return accountService.updateStatus(id, status);
    }

    //返回编辑申请表数据
    @GetMapping("edit")
    public CommonResponse editData(long id) {

        return new CommonResponse(accountService.editData(id));
    }

    //打印申请表
    @GetMapping("print")
    public CommonResponse jasperReport(long id, HttpServletResponse response) throws Exception {

        File file = ResourceUtils.getFile("classpath:static/jasper/logo.png");

        String imgPath = StringUtils.substringBeforeLast(file.getAbsolutePath(), "\\") + "\\";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("imagedir", imgPath);

        List<Account> accountList = new ArrayList<>();
        Account account = accountService.getAccountById(id);

        if (account != null) {
            accountList.add(account);
            ClassPathResource classPathResource = new ClassPathResource("static/jasper/AccoutRequestForm2019A.jasper");
            FileInputStream fileInputStream = new FileInputStream(classPathResource.getFile());
            OutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            JasperRunManager.runReportToPdfStream(fileInputStream, servletOutputStream, parameters, new JRBeanCollectionDataSource(accountList));
            servletOutputStream.flush();
            servletOutputStream.close();
            fileInputStream.close();
            return null;
        } else {
            return new CommonResponse().code(CommonErrorMsg.Application_Not_Exist.getErrCode()).message(CommonErrorMsg.Application_Not_Exist.getErrMsg());
        }
    }
}
