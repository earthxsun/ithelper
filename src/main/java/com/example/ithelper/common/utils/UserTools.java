package com.example.ithelper.common.utils;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.system.entity.User;
import com.example.ithelper.system.entity.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class UserTools {

    public static List<UserVo> UserToUserVo(List<User> userList) {
        List<UserVo> userVoList = new ArrayList<>();
        int num = 1;
        for (User user : userList) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            userVo.setDeptName(user.getDept().getDeptName());
            userVo.setRoleName(user.getRole().getRoleName());
            userVo.setNum(num);
            num += 1;
            userVoList.add(userVo);
        }
        return userVoList;
    }

    public static String encryptPassword(String password, String username) {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed(username.getBytes());
        String salt = randomNumberGenerator.nextBytes().toHex();
        return new SimpleHash("SHA-256", password, salt, 3).toBase64();
    }

    public static String getCurrentUser() throws CommonException {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            throw new CommonException(CommonErrorMsg.USER_NOT_LOGIN.getErrMsg(),CommonErrorMsg.USER_NOT_LOGIN.getErrCode());
        }

        return  JWTUtil.getUsername(principal.toString());
    }
}
