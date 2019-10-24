package com.example.ithelper.system.servie;

import com.example.ithelper.common.response.CommonResponse;

import java.util.List;
import java.util.Map;

public interface AccountNameService {

    List getAccountNameByAccountId(String accountId);

    CommonResponse save(Map map);

    CommonResponse deleteAll(String accountId);
}
