package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.common.utils.UserTools;
import com.example.ithelper.system.dao.*;
import com.example.ithelper.system.entity.Account;
import com.example.ithelper.system.entity.AccountPermissionInfo;
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

    @Autowired
    SystemsRepository systemsRepository;

    @Autowired
    SystemsPermissionRepository systemsPermissionRepository;

    @Autowired
    AccountPermissionInfoRepository accountPermissionInfoRepository;


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
        String dept = "";
        List<String> depts = new ArrayList<>();
        try {
            User user = userService.getUserByUsername(currentUser);
            dept = user.getDept().getDeptName();
            if(dept.contains("关务")) {
                depts.add("关务中心");
                depts.add("关务综合");
                depts.add("东诚报关部");
            } else {
                depts.add(dept);
            }
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
                //rowsNumber = accountRepository.findAllByNameContains(searchContent).size();
                //accountList = accountRepository.findAllByNameContains(searchContent, pageable);
                rowsNumber = accountRepository.findAllByDeptInAndNameContains(depts,searchContent).size();
                accountList = accountRepository.findAllByDeptInAndNameContains(depts,searchContent, pageable);
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
                    rowsNumber = accountRepository.findAllByCreateTimeBetween(sDate, eDate).size();
                    accountList = accountRepository.findAllByCreateTimeBetween(sDate, eDate, pageable);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                //rowsNumber = accountRepository.findAllByDept(dept).size();
                //accountList = accountRepository.findAllByDept(dept, pageable);
                rowsNumber = accountRepository.findAllByDeptIn(depts).size();
                accountList = accountRepository.findAllByDeptIn(depts, pageable);
        }

        if (Objects.isNull(accountList)) {
            hashMap.put("rowsNumber", 0);
            hashMap.put("page", 1);
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
    public Account saveData(Map map, String method) throws Exception {

        String currentUser = "";
        try {
            currentUser = UserTools.getCurrentUser();
        } catch (CommonException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        List list = (List) map.get("requiredSystemPermissions");
        HashSet<AccountPermissionInfo> infoHashSet = new HashSet<>();
        int i = 1;
        if (method.equals("edit")) {
            account = accountRepository.getOne(Long.valueOf(map.get("id").toString()));
            for (Object obj : list) {
                Map accountPermInfo = (Map) obj;
                AccountPermissionInfo accountPermissionInfo = accountPermissionInfoRepository.findById(Long.valueOf(accountPermInfo.get("id").toString())).get();
                accountPermissionInfo.setSystemName(accountPermInfo.get("name").toString());
                accountPermissionInfo.setOrg(StringUtils.join((List) accountPermInfo.get("value"), ","));
                accountPermissionInfo.setSystemPermissions(StringUtils.join((List) accountPermInfo.get("perm"), ","));
            }
        } else {
            account.setCreatedBy(currentUser);
            for (Object obj : list) {
                Map accountPermInfo = (Map) obj;
                AccountPermissionInfo accountPermissionInfo = new AccountPermissionInfo();
                accountPermissionInfo.setAccount(account);
                accountPermissionInfo.setSystemName(accountPermInfo.get("name").toString());
                accountPermissionInfo.setOrg(StringUtils.join((List) accountPermInfo.get("value"), ","));
                accountPermissionInfo.setSystemPermissions(StringUtils.join((List) accountPermInfo.get("perm"), ","));
                infoHashSet.add(accountPermissionInfo);
            }
            account.setAccountPermissionInfoSet(infoHashSet);
        }
        account.setName(map.get("name").toString());
        account.setDept(map.get("dept").toString());
        account.setEmail(map.get("email").toString());
        account.setReasonForApplication(map.get("reason").toString());
        account.setTel(map.get("tel").toString());
        account.setPost(map.get("post").toString());
        account.setStatus("暂存");
        account.setUpdatedBy(currentUser);
        account.setOtherPerm(map.get("otherPerm").toString());
        account.setGroup(map.get("group").toString());
        account.setApplicationType(map.get("applicationType").toString());

        //System.out.println("account :" + account);

        accountRepository.save(account);

        return null;
    }

    @Override
    public AccountDetailVo editData(long id) throws Exception {

        Account account = accountRepository.getOne(id);
        AccountDetailVo accountDetailVo = new AccountDetailVo();
        BeanUtils.copyProperties(account, accountDetailVo);
        accountDetailVo.setReason(account.getReasonForApplication());
        List<Map> systemInfo = new ArrayList<>();
        Set<AccountPermissionInfo> infoHashSet = account.getAccountPermissionInfoSet();
        System.out.println(infoHashSet);
        for (AccountPermissionInfo accountPermissionInfo : infoHashSet) {
            Map<String, Object> apiMap = new HashMap<>();
            apiMap.put("id", accountPermissionInfo.getId());
            apiMap.put("name", accountPermissionInfo.getSystemName());
            apiMap.put("value", Arrays.asList(accountPermissionInfo.getOrg().split(",")));
            apiMap.put("perm", Arrays.asList(accountPermissionInfo.getSystemPermissions().split(",")));
            apiMap.put("org", toOptions(accountPermissionInfo.getOrg(), accountDetailVo.getDept()));
            systemInfo.add(apiMap);
        }
        accountDetailVo.setSystemInfo(systemInfo);
        System.out.println(accountDetailVo);
        return accountDetailVo;
    }

    @Override
    public CommonResponse updateStatus(long id, String status) {
        System.out.println(id);
        Account account = getAccountById(id);
        System.out.println("updateStatus:" + account);
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

    private List toOptions(String org, String dept) {
        List<String> orgList = new ArrayList<>();
        if (dept.contains("关务") || dept.contains("东诚")) {
            System.out.println("账号包含关务");
            orgList.add("物流");
            orgList.add("综合");
            orgList.add("东诚");
        } else {
            if (org.isEmpty()) {
                orgList.add(dept);
            } else {
                orgList = Arrays.asList(org.split(","));
            }
        }
        List<Map> list = new ArrayList<>();
        for (String s : orgList) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("label", s);
            hashMap.put("value", s);
            list.add(hashMap);
        }
        return list;
    }
}
