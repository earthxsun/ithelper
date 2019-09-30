package com.example.ithelper.system.servie;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.system.entity.User;
import com.example.ithelper.system.entity.vo.UserVo;

import java.util.Map;

public interface UserService {

    Map getUsers(Map map);

    UserVo getUser (Long id) throws CommonException;

    User saveUser(Map userMap)  throws CommonException;

    User getUserByUsername(String username) throws CommonException;

    User updateUser(Map userInfo) throws CommonException;

    void updateLastLoginTime(User user);

    String updatePassword(String username,String password);
}
