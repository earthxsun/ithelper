package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.dao.AccountDataRepository;
import com.example.ithelper.system.dao.AccountRepository;
import com.example.ithelper.system.entity.Account;
import com.example.ithelper.system.entity.User;
import com.example.ithelper.system.entity.vo.AccountDetailVo;
import com.example.ithelper.system.entity.vo.AccountVo;
import com.example.ithelper.system.servie.AccountService;
import com.example.ithelper.system.servie.DeptService;
import com.example.ithelper.system.servie.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountDataRepository accountDataRepository;

    @Autowired
    DeptService deptService;

    @Autowired
    UserService userService;



    @Override
    public Account getAccountById(long id) {

        if (accountRepository.findById(id).isPresent()) {
            return accountRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public HashMap getAccounts(Map map) {

        String currentUser = JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());
        String dept="";

        try {
            User user = userService.getUserByUsername(currentUser);
            dept = user.getDept().getDeptName();
        } catch (CommonException e) {
            e.printStackTrace();
        }

        String searchContent = map.get("searchContent").toString();

        String keyWord = map.get("keyWord").toString();

        int rowPerPage = (int) map.get("rowsPerPage");

        int page = (int) map.get("page");

        int rowsNumber = 0;

        HashMap<String, Object> hashMap = new HashMap<>();

        List<Account> accountList = null;

        String sortBy = (String) map.get("sortBy");

        Sort sort = new Sort(Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page - 1, rowPerPage, sort);

        if (SecurityUtils.getSubject().hasRole("管理员") && keyWord.isEmpty()) {
            keyWord = "all";
        }

        switch (keyWord) {
            case "all":
                rowsNumber = accountRepository.findAll().size();
                accountList = accountRepository.findAll(pageable).getContent();
                break;
            case "name":
                rowsNumber = accountRepository.findAllByNameContains(searchContent).size();
                accountList = accountRepository.findAllByNameContains(searchContent, pageable);
                break;
            case "type":
                rowsNumber = accountRepository.findAllByApplicationType(searchContent).size();
                accountList = accountRepository.findAllByApplicationType(searchContent, pageable);
                break;
            case "branch":
                rowsNumber = accountRepository.findAllByDept(searchContent).size();
                accountList = accountRepository.findAllByDept(searchContent, pageable);
                break;
            case "status":
                rowsNumber = accountRepository.findAllByStatus(searchContent).size();
                accountList = accountRepository.findAllByStatus(searchContent, pageable);
                break;
            case "date":
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String startDate = map.get("startDate").toString() + " " + "00:00:00";
                String endDate = map.get("endDate").toString() + " " + "23:59:59";

                System.out.println(startDate + "----" + endDate);
                try {
                    Date sDate = simpleDateFormat.parse(startDate);
                    Date eDate = simpleDateFormat.parse(endDate);
                    rowsNumber = accountRepository.findAllByCreateTimeBetween(sDate,eDate).size();
                    accountList = accountRepository.findAllByCreateTimeBetween(sDate,eDate,pageable);
                } catch ( ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                rowsNumber = accountRepository.findAllByDept(dept).size();
                accountList = accountRepository.findAllByDept(dept,pageable);

        }

        if (Objects.isNull(accountList)){
            hashMap.put("rowsNumber", 0);
            hashMap.put("page",1);
            hashMap.put("rowsPerPage", 1);
            hashMap.put("data", new ArrayList<>());
        } else {
            hashMap.put("rowsNumber", rowsNumber);
            hashMap.put("page", page);
            hashMap.put("rowsPerPage", rowPerPage);
            hashMap.put("data", entityToVo(accountList));
        }

        return hashMap;
    }

    private List<AccountVo> entityToVo(List<Account> accountList) {
        List<AccountVo> accountVoList = new ArrayList<>();
        long num = 1;
        for (Account account : accountList) {
            AccountVo accountVo = new AccountVo();
            BeanUtils.copyProperties(account, accountVo);
            accountVo.setNum(num);
            num += 1;
            accountVoList.add(accountVo);
        }
        return accountVoList;
    }

    @Override
    public Account saveData(Map map, String method) {
        String currentUser = JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());
        Account account = new Account();
        if (method.equals("edit")) {
            account = accountRepository.getOne(Long.valueOf(map.get("id").toString()));
        } else {
            account.setCreatedBy(currentUser);
        }

        account.setName(map.get("name").toString());
        account.setDept(map.get("dept").toString());
        account.setEmail(map.get("email").toString());
        account.setReasonForApplication(map.get("reason").toString());
        account.setTel(map.get("tel").toString());
        account.setPost(map.get("post").toString());
        account.setSystem1(map.get("system1").toString());
        account.setSystem2(map.get("system2").toString());
        account.setSystem3(map.get("system3").toString());
        account.setStatus("暂存");

        account.setUpdatedBy(currentUser);
        account.setOtherPerm(map.get("otherPerm").toString());
        account.setGroup(map.get("group").toString());
        account.setApplicationType(map.get("applicationType").toString());

        account.setSysOrg1(StringUtils.join((List) map.get("sysOrg1"), ","));
        account.setSysOrg2(StringUtils.join((List) map.get("sysOrg2"), ","));
        account.setSysOrg3(StringUtils.join((List) map.get("sysOrg3"), ","));
        account.setSysPerm1(StringUtils.join((List) map.get("sysPerm1"), ","));
        account.setSysPerm2(StringUtils.join((List) map.get("sysPerm2"), ","));
        account.setSysPerm3(StringUtils.join((List) map.get("sysPerm3"), ""));


        accountRepository.save(account);

        return null;
    }

    @Override
    public AccountDetailVo editData(long id) {

        Account account = accountRepository.getOne(id);
        AccountDetailVo accountDetailVo = new AccountDetailVo();
        BeanUtils.copyProperties(account, accountDetailVo);
        accountDetailVo.setReason(account.getReasonForApplication());
        accountDetailVo.setSysOrg1(Arrays.asList(account.getSysOrg1().split(",")));
        accountDetailVo.setSysPerm1(Arrays.asList(account.getSysPerm1().split(",")));
        accountDetailVo.setSysPerm1(Arrays.asList(account.getSysPerm1().split(",")));
        accountDetailVo.setSysOrg2(Arrays.asList(account.getSysOrg2().split(",")));
        accountDetailVo.setSysPerm2(Arrays.asList(account.getSysPerm2().split(",")));
        accountDetailVo.setSysOrg3(Arrays.asList(account.getSysOrg3().split(",")));
        accountDetailVo.setSysPerm3(Arrays.asList(account.getSysPerm3().split(",")));
        return accountDetailVo;
    }

    @Override
    public CommonResponse updateStatus(long id, String status) {

        Account account = accountRepository.getOne(id);
        String currentUser = JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());

        if (account == null) {
            return new CommonResponse().code(CommonErrorMsg.Application_Not_Exist.getErrCode())
                    .message(CommonErrorMsg.Application_Not_Exist.getErrMsg());
        }

        switch (status) {
            case "已完成":
                if (SecurityUtils.getSubject().hasRole("管理员")) {
                    if (account.getStatus().equals("已提交")) {
                        account.setStatus(status);
                        account.setUpdatedBy(currentUser);
                    } else {
                        return new CommonResponse().code(CommonErrorMsg.Application_No_Submit.getErrCode())
                                .message(CommonErrorMsg.Application_No_Submit.getErrMsg());
                    }
                } else {
                    return new CommonResponse().code(CommonErrorMsg.USER_NO_PERMISSION.getErrCode())
                            .message(CommonErrorMsg.USER_NO_PERMISSION.getErrMsg());
                }
                break;
            case "作废":
                if (account.getStatus().equals("暂存")) {
                    account.setStatus(status);
                    account.setUpdatedBy(currentUser);
                } else {
                    return new CommonResponse().code(CommonErrorMsg.Applicetion_Cannot_Invalid.getErrCode())
                            .message(CommonErrorMsg.Applicetion_Cannot_Invalid.getErrMsg());
                }
                break;
        }

        account.setStatus(status);
        account.setUpdatedBy(currentUser);
        accountRepository.save(account);
        return new CommonResponse("ok");
    }
}
