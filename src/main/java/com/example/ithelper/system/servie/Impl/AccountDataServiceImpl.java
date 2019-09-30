package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.system.dao.AccountDataRepository;
import com.example.ithelper.system.entity.AccountData;
import com.example.ithelper.system.servie.AccountDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountDataServiceImpl implements AccountDataService {


    @Autowired
    AccountDataRepository accountDataRepository;

    @Override
    public Map getDatas() {

        //List<AccountData> list = accountDataRepository.findAllByName(name);
        List<AccountData> list = accountDataRepository.findAll();
        List<String> nameList = accountDataRepository.getAllName();

        HashMap<String,Map> optionData = new HashMap<>();

        for (String s : nameList) {
            HashMap<String,List> data = new HashMap<>();
            ArrayList<Map> firstOptions = new ArrayList<>();
            ArrayList<Map> secondaryOptions = new ArrayList<>();
            for (AccountData accountData:list){
                if (accountData.getName().equals(s)){
                    if(accountData.getWorkGroup().equals("0")){
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("label",accountData.getContent());
                        hashMap.put("value",accountData.getContent());
                        firstOptions.add(hashMap);
                    }
                    if(accountData.getWorkGroup().equals("1")){
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("label",accountData.getContent());
                        hashMap.put("value",accountData.getContent());
                        secondaryOptions.add(hashMap);
                    }
                }
            }
            data.put("firstOptions",firstOptions);
            data.put("secondaryOptions)",secondaryOptions);
            optionData.put(s,data);
        }
        //System.out.println(optionData);

        return optionData;
    }

    @Override
    public AccountData getAccountDataById(long id) {
        return accountDataRepository.getOne(id);
    }

    @Override
    public AccountData getAccountDataByName(String name) {
        return null;
    }
}
