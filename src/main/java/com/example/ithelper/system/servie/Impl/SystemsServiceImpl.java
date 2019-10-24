package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.system.dao.SystemsPermissionRepository;
import com.example.ithelper.system.dao.SystemsRepository;
import com.example.ithelper.system.entity.Systems;
import com.example.ithelper.system.entity.SystemsPermission;
import com.example.ithelper.system.entity.vo.SystemDetail;
import com.example.ithelper.system.servie.SystemsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SystemsServiceImpl implements SystemsService {

    private SystemsRepository systemsRepository;
    private SystemsPermissionRepository systemsPermissionRepository;

    @Autowired
    public SystemsServiceImpl(SystemsRepository systemRepository, SystemsPermissionRepository systemPermissionRepository){
        this.systemsRepository = systemRepository;
        this.systemsPermissionRepository = systemPermissionRepository;
    }

    @Override
    public List getAll() {
        List<SystemDetail> systemDetails = new ArrayList<>();
        List<Systems> systemList = systemsRepository.findAll();
        for (Systems s:systemList) {
            SystemDetail systemDetail = new SystemDetail();
            BeanUtils.copyProperties(s,systemDetail);
            List<Object> permList = new ArrayList<>();
            List<SystemsPermission> permListById = systemsPermissionRepository.findAllBySystems(s);
            for (SystemsPermission permission:permListById) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("label",permission.getPermission());
                hashMap.put("value",permission.getPermission());
                permList.add(hashMap);
            }
            systemDetail.setIndex(String.valueOf((int)(1+Math.random()*(1000-1+1))));
            systemDetail.setPermission(permList);
            systemDetails.add(systemDetail);
        }
        return systemDetails;
    }
}
