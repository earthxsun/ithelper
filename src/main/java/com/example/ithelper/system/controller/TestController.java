package com.example.ithelper.system.controller;

import com.example.ithelper.common.aop.OperationLog;
import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.dao.AccountDataRepository;
import com.example.ithelper.system.dao.AccountRepository;
import com.example.ithelper.system.entity.Account;
import com.example.ithelper.system.entity.vo.UserVo;
import com.example.ithelper.system.servie.RoleService;
import com.example.ithelper.system.servie.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountDataRepository accountDataRepository;


    @GetMapping("save")
    public String saveRedis() {

        //try {
        //    List<UserVo> users = userService.getUsers();
        //    ObjectMapper objectMapper = new ObjectMapper();
        //    String value = objectMapper.writeValueAsString(users);
        //    stringRedisTemplate.opsForValue().set("userVo", value);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        System.out.println("/test/save");

        return "ok";
    }

    @GetMapping("get")
    public String getRedis() {
        String s = stringRedisTemplate.opsForValue().get("userVo");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserVo userVo = objectMapper.readValue(s, UserVo.class);
            System.out.println(userVo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @OperationLog(value = "测试AOP")
    @RequestMapping("gettest")
    public CommonResponse test() throws CommonException {
        List<String> depts = new ArrayList<>();
        depts.add("关务中心");
        depts.add("关务综合");
        depts.add("东诚报关部");
        List<Account> list = accountRepository.findAllByDeptIn(depts);
        System.out.println(list);
        return new CommonResponse("ok");
    }

    @RequestMapping("printpdf")
    public String printpdf(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //File accountFile = new File("f://aaa.txt");
        File accountFile = new File("f://account.pdf");
        if (accountFile.exists()){
            System.out.println("test/printpdf");
            //response.setHeader("content-type", "text/plain");
            response.setContentType("application/octet-stream");

            //response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account.txt", "UTF-8"));
            //response.setHeader("Content-Length",""+accountFile.length());
            System.out.println(accountFile.length());

            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(accountFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1){
                os.write(buffer,0,i);
                i = bis.read(buffer);
            }
            os.flush();
            os.close();
            bis.close();

            //byte[] data = null;
            //FileInputStream inputStream = new FileInputStream(accountFile);
            //data = new byte[inputStream.available()];
            //inputStream.read(data);
            //response.getOutputStream().write(data);
            //inputStream.close();

        } else {
            System.out.println("文件不存在");
        }

        return null;
    }

    //@RequestMapping("/print")
    //public String jasperReport(HttpServletResponse response) throws Exception {
    //
    //    File file = ResourceUtils.getFile("classpath:static/jasper/logo.png");
    //
    //    System.out.println(file.getAbsolutePath());
    //
    //    String imgPath = StringUtils.substringBeforeLast(file.getAbsolutePath(),"\\") + "\\";
    //
    //    System.out.println(imgPath);
    //
    //    Map<String,Object> parameters = new HashMap<>();
    //    parameters.put("imagedir",imgPath);
    //
    //    //Account account = new Account();
    //    //account.setName("张三");
    //    //account.setDept("关务中心");
    //    //account.setPost("报关员");
    //    //account.setEmail("zhang@szjuhang.com");
    //    //account.setReasonForApplication("新员工入职");
    //    //account.setTel("12345678123");
    //    //account.setSystemName("关贸云");
    //    //account.setOrganization("国际物流 关务综合 东诚报关");
    //    //account.setApplicationType("新增");
    //    //account.setRequiredPermissions("应收单 应付单 付款单 收款单 报销单 全部权限");
    //    //account.setGroup("E组");
    //
    //    List<Account> accountList = new ArrayList<>();
    //    accountList.add(account);
    //
    //    ClassPathResource classPathResource = new ClassPathResource("static/jasper/AccoutRequestForm2019.jasper");
    //    System.out.println(classPathResource.getFile());
    //    FileInputStream fileInputStream = new FileInputStream(classPathResource.getFile());
    //    OutputStream servletOutputStream = response.getOutputStream();
    //    response.setContentType("application/pdf");
    //    JasperRunManager.runReportToPdfStream(fileInputStream,servletOutputStream,parameters,new JRBeanCollectionDataSource(accountList));
    //    servletOutputStream.flush();
    //    servletOutputStream.close();
    //    fileInputStream.close();
    //    return null;
    //}
}
