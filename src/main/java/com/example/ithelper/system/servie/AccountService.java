package com.example.ithelper.system.servie;

import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.entity.Account;
import com.example.ithelper.system.entity.vo.AccountDetailVo;

import java.util.HashMap;
import java.util.Map;

public interface AccountService {

    Account getAccountById(long id);

    HashMap getAccounts(Map map);

    Account saveData(Map map,String method) throws Exception;

    CommonResponse updateStatus(long id, String status);

    AccountDetailVo editData(long id) throws NoSuchMethodException, Exception;
}
