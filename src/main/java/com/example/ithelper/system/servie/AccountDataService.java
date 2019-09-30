package com.example.ithelper.system.servie;

import com.example.ithelper.system.entity.AccountData;

import java.util.Map;

public interface AccountDataService {

    Map getDatas();

    AccountData getAccountDataById(long id);

    AccountData getAccountDataByName(String name);
}
