package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.dao.AccountNameRepository;
import com.example.ithelper.system.entity.AccountName;
import com.example.ithelper.system.servie.AccountNameService;
import com.example.ithelper.system.servie.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class AccountNameServiceImpl implements AccountNameService {

    private AccountNameRepository accountNameRepository;
    private AccountService accountService;

    //构造器注入依赖
    @Autowired
    public AccountNameServiceImpl(AccountNameRepository accountNameRepository, AccountService accountService) {
        this.accountNameRepository = accountNameRepository;
        this.accountService = accountService;
    }


    @Override
    public List getAccountNameByAccountId(String accountId) {
        return accountNameRepository.findByAccountId(accountId);
    }

    @Override
    public CommonResponse save(Map map) {
        System.out.println(map);
        List sysNames = (List) map.get("sysName");
        List accountNames = (List) map.get("accountName");
        String id = map.get("accountId").toString();
        if (id.equals("0")) {
            return new CommonResponse().code(CommonErrorMsg.Application_Not_Exist.getErrCode())
                    .message(CommonErrorMsg.Application_Not_Exist.getErrMsg());
        }
        List<AccountName> list = accountNameRepository.findByAccountId(id);
        System.out.println(list);
        if (list.isEmpty()) {
            System.out.println("list.isEmpty");
            if (sysNames.size() == accountNames.size()) {
                for (int i = 0; i < sysNames.size(); i++) {
                    AccountName accountName = new AccountName();
                    accountName.setAccountId(String.valueOf(id));
                    accountName.setSystem(sysNames.get(i).toString());
                    accountName.setAccountName(accountNames.get(i).toString());
                    accountNameRepository.save(accountName);
                }
            }
        } else {
            System.out.println("list.isNotEmpty");
            for (AccountName accountName : list) {
                for (int i = 0; i < sysNames.size(); i++) {
                    if (sysNames.get(i).toString().equals(accountName.getSystem())) {
                        accountName.setSystem(sysNames.get(i).toString());
                        accountName.setAccountName(accountNames.get(i).toString());
                    } else {
                        System.out.println("系统名不相等");
                    }
                }
            }
        }
        accountService.updateStatus(Long.parseLong(id), "已完成");
        return new CommonResponse("ok");
    }

    @Override
    @Transactional
    public CommonResponse deleteAll(String accountId) {
        List<AccountName> list = accountNameRepository.deleteAllByAccountIdContains(accountId);
        if (list.isEmpty()) {
            return new CommonResponse().message(CommonErrorMsg.AccountName_NOT_EXIST.getErrMsg())
                    .code(CommonErrorMsg.AccountName_NOT_EXIST.getErrCode());
        }
        return new CommonResponse("ok");
    }
}
